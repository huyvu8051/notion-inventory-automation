package io.huyvu.notion.inventory.repository;

import notion.api.v1.NotionClient;
import notion.api.v1.model.databases.query.sort.QuerySort;
import notion.api.v1.model.databases.query.sort.QuerySortDirection;
import notion.api.v1.model.databases.query.sort.QuerySortTimestamp;
import notion.api.v1.model.pages.Page;
import notion.api.v1.request.databases.QueryDatabaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static io.huyvu.notion.inventory.NotionConfig.NOTION_INGREDIENT_DB_ID;
import static io.huyvu.notion.inventory.NotionConfig.NOTION_RECIPE_DB_ID;

public class NotionRepository {
    private static final Logger log = LoggerFactory.getLogger(NotionRepository.class);
    private final NotionClient notionClient;
    private final Semaphore semaphore;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public NotionRepository(NotionClient notionClient, Semaphore semaphore) {
        this.notionClient = notionClient;
         this.semaphore = semaphore;
    }

    public List<Page> findAllIngredients() {
        var request = new QueryDatabaseRequest(NOTION_INGREDIENT_DB_ID);

        var querySort = new QuerySort();
        querySort.setTimestamp(QuerySortTimestamp.LastEditedTime);
        querySort.setDirection(QuerySortDirection.Descending);

        request.setSorts(List.of(querySort));
        request.setPageSize(5);
        semaphore.acquireUninterruptibly();
        var queryResults = notionClient.queryDatabase(request);

        scheduler.schedule(() -> {
            semaphore.release();
            log.info("findAllIngredients lock được mở sau 3 giây.");
        }, 3, TimeUnit.SECONDS);


        return queryResults.getResults();
    }

    public List<Page> findAllRecipes() {
        var request = new QueryDatabaseRequest(NOTION_RECIPE_DB_ID);

        var querySort = new QuerySort();
        querySort.setTimestamp(QuerySortTimestamp.LastEditedTime);
        querySort.setDirection(QuerySortDirection.Descending);

        request.setSorts(List.of(querySort));
        request.setPageSize(5);
        semaphore.acquireUninterruptibly();
        var queryResults = notionClient.queryDatabase(request);
        scheduler.schedule(() -> {
            semaphore.release();
            log.info("findAllRecipes lock được mở sau 3 giây.");
        }, 3, TimeUnit.SECONDS);
        return queryResults.getResults();
    }
}
