package io.huyvu.notion.inventory.handler;

import notion.api.v1.model.pages.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotionEventHandlerImpl implements NotionEventHandler {
    private final Logger logger = LoggerFactory.getLogger(NotionEventHandlerImpl.class);


    @Override
    public void onAnyIngredientTitleUpdated(Page page) {
        logger.info("onAnyIngredientTitleUpdated");
    }

    @Override
    public void onAfterAnyIngredientTitleUpdated() {
        logger.info("onAfterAnyIngredientTitleUpdated");
    }

    @Override
    public void onNotThingChange() {
        logger.info("onNotThingChange");
    }

    @Override
    public void onRecipeTitleUpdated(Page page) {
        logger.info("onRecipeTitleUpdated");
    }

    @Override
    public void onRecipeIngredientsUpdated(Page ndbRecipe) {
        logger.info("onRecipeIngredientsUpdated");
    }
}
