package io.huyvu.notion.inventory;

import io.huyvu.notion.inventory.mapper.IngredientMapper;
import io.huyvu.notion.inventory.mapper.InitSchema;
import io.huyvu.notion.inventory.mapper.MealPlanMapper;
import notion.api.v1.NotionClient;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteDataSource;

public class NotionInventoryApplication {
    private static final Logger logger = LoggerFactory.getLogger(NotionInventoryApplication.class);

    public static void main(String[] args) {
        logger.info("=== Notion Inventory ===");

        var config = NotionConfig.useDefault();

        var notionClient = new NotionClient(config.getNotionApiKey());

        var notionRepo = new NotionRepository(notionClient);

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(config.getSqlitePath());
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);

        configuration.addMappers(config.getMappersPath());
        var sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        initDatabaseSchema(sqlSessionFactory);


        var sqlSession = sqlSessionFactory.openSession();
        var ingredientMapper = sqlSession.getMapper(IngredientMapper.class);
        var mealPlanMapper = sqlSession.getMapper(MealPlanMapper.class);

        var localRepository = new LocalRepositoryImpl(ingredientMapper, mealPlanMapper);

        var eventListener = new NotionEventListener(notionRepo, localRepository);

        eventListener.setOnAnyIngredientTitleUpdated(page -> {
            logger.info("On any ingredient title updated");
        });


        ApplicationRunner applicationRunner = new ApplicationRunner(config, eventListener);
        applicationRunner.run();


    }

    private static void initDatabaseSchema(SqlSessionFactory sqlSessionFactory) {
        try (var sqlSession = sqlSessionFactory.openSession()) {
            var mapper = sqlSession.getMapper(InitSchema.class);
            mapper.createIngredients();
            mapper.createMealPlans();
            mapper.createRecipes();
            mapper.createRecipeIngredients();
            mapper.createMealPlanRecipes();
            sqlSession.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
