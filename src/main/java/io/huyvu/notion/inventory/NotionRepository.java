package io.huyvu.notion.inventory;

import notion.api.v1.NotionClient;
import notion.api.v1.model.pages.Page;

import java.util.List;

public class NotionRepository {
    private final NotionClient notionClient;

    public NotionRepository(NotionClient notionClient) {
        this.notionClient = notionClient;
    }

    public List<Page> findAllIngredients() {
        return null;
    }
}
