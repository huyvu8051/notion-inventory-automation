package io.huyvu.notion.inventory;

import io.huyvu.notion.inventory.model.LDBIngredient;
import io.huyvu.notion.inventory.repository.LocalRepository;
import notion.api.v1.model.pages.Page;

import java.util.List;
import java.util.function.Consumer;

public class NotionEventListener {
    private final NotionRepository notionRepository;
    private final LocalRepository localRepository;
    private Consumer<Page> onAnyIngredientTitleUpdated = page -> {
    };

    public NotionEventListener(NotionRepository notionRepo, LocalRepository localRepository) {
        this.notionRepository = notionRepo;
        this.localRepository = localRepository;
    }


    public void setOnAnyIngredientTitleUpdated(Consumer<Page> onAnyIngredientTitleUpdated) {
        this.onAnyIngredientTitleUpdated = onAnyIngredientTitleUpdated;
    }

    private boolean isAnyIngredientTitleUpdated() {
        List<Page> ingredients = notionRepository.findAllIngredients();
        List<LDBIngredient> ldbIngredients = localRepository.findAllIngredients();
        return false;
    }

    public void listen() {
        boolean isAnyIngredientTitleUpdated = isAnyIngredientTitleUpdated();
    }
}
