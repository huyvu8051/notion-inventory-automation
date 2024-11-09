package io.huyvu.notion.inventory.handler;

import io.huyvu.notion.inventory.repository.LocalRepository;
import notion.api.v1.model.pages.Page;

public class CustomNotionEventHandlerImpl extends NotionEventHandlerImpl{
    private final LocalRepository localRepository;

    public CustomNotionEventHandlerImpl(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    @Override
    public void onAnyIngredientTitleUpdated(Page page) {
        super.onAnyIngredientTitleUpdated(page);
        localRepository.saveIngredient(page);
    }
}
