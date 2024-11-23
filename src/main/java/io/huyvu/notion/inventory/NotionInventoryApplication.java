package io.huyvu.notion.inventory;

import io.huyvu.notion.inventory.handler.CustomNotionEventHandlerImpl;
import io.huyvu.notion.inventory.handler.NotionEventHandlerImpl;
import io.huyvu.notion.inventory.listener.NotionEventListener;
import io.huyvu.notion.inventory.mapper.IngredientMapper;
import io.huyvu.notion.inventory.mapper.InitSchemaMapper;
import io.huyvu.notion.inventory.mapper.MealPlanMapper;
import io.huyvu.notion.inventory.mapper.RecipeMapper;
import io.huyvu.notion.inventory.repository.NotionRepository;
import notion.api.v1.NotionClient;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
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
        notionClient.setLogger(new NotionSlf4jLoggerDelegate());
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
        var recipeMapper = sqlSession.getMapper(RecipeMapper.class);

        var localRepository = new LocalRepositoryImpl(sqlSession, ingredientMapper, mealPlanMapper, recipeMapper);

        NotionEventHandlerImpl notionEventHandler = new CustomNotionEventHandlerImpl(localRepository);
        var eventListener = new NotionEventListener(notionRepo, localRepository, notionEventHandler);

        ApplicationRunner applicationRunner = new ApplicationRunner(config, eventListener);
        applicationRunner.run();


    }

    private static void initDatabaseSchema(SqlSessionFactory sqlSessionFactory) {
        logger.info("Initializing database schema...");
        try (var sqlSession = sqlSessionFactory.openSession()) {
            var mapper = sqlSession.getMapper(InitSchemaMapper.class);
            mapper.createIngredients();
            mapper.createMealPlans();
            mapper.createRecipes();
            mapper.createRecipeIngredients();
            mapper.createMealPlanRecipes();
            sqlSession.commit();
            logger.info("Database schema initialized successfully.");
        } catch (Exception e) {
            logger.error("Error during database schema initialization", e);
            throw new IllegalStateException("Database initialization failed", e);
        }

    }
}
