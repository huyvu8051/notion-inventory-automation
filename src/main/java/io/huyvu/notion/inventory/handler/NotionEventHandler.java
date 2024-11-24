package io.huyvu.notion.inventory.handler;

import notion.api.v1.model.pages.Page;

public interface NotionEventHandler {
    void onAnyIngredientTitleUpdated(Page page);

    void onAfterAnyIngredientTitleUpdated();

    void onNotThingChange();

    void onRecipeTitleUpdated(Page page);

    void onRecipeIngredientsUpdated(Page ndbRecipe);

    void onAnyRecipeTitleUpdated();

    void onAnyRecipeIngredientUpdated();
}
