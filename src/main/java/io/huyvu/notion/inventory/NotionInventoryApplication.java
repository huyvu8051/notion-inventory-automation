package io.huyvu.notion.inventory;

import notion.api.v1.NotionClient;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        var localRepository = new LocalRepository(sqlSessionFactory);

        var eventListener = new NotionEventListener(notionRepo, localRepository);

        eventListener.setOnAnyIngredientTitleUpdated(page -> {
            logger.info("On any ingredient title updated");
        });


        ApplicationRunner applicationRunner = new ApplicationRunner(config, eventListener);
        applicationRunner.run();


    }
}
