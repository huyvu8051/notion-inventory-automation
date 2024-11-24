package io.huyvu.notion.inventory.repository;

import notion.api.v1.NotionClient;
import notion.api.v1.model.databases.query.sort.QuerySort;
import notion.api.v1.model.databases.query.sort.QuerySortDirection;
import notion.api.v1.model.databases.query.sort.QuerySortTimestamp;
import notion.api.v1.model.pages.Page;
import notion.api.v1.request.databases.QueryDatabaseRequest;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.huyvu.notion.inventory.NotionConfig.NOTION_INGREDIENT_DB_ID;
import static io.huyvu.notion.inventory.NotionConfig.NOTION_RECIPE_DB_ID;

public class NotionRepository {
    private static final Logger log = LoggerFactory.getLogger(NotionRepository.class);
    private final NotionClient notionClient;

    public NotionRepository(NotionClient notionClient) {
        this.notionClient = notionClient;
    }

    public List<Page> findAllIngredients() {
        return getLastUpdated5Pages(NOTION_INGREDIENT_DB_ID);
    }

    public List<Page> findAllRecipes() {
        return getLastUpdated5Pages(NOTION_RECIPE_DB_ID);
    }

    @NotNull
    private List<Page> getLastUpdated5Pages(String notionIngredientDbId) {
        var request = new QueryDatabaseRequest(notionIngredientDbId);

        var querySort = new QuerySort();
        querySort.setTimestamp(QuerySortTimestamp.LastEditedTime);
        querySort.setDirection(QuerySortDirection.Descending);

        request.setSorts(List.of(querySort));
        request.setPageSize(5);
        var queryResults = notionClient.queryDatabase(request);
        return queryResults.getResults();
    }


}
