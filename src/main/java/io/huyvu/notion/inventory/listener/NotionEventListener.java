package io.huyvu.notion.inventory.listener;

import io.huyvu.notion.inventory.NotionPageUtils;
import io.huyvu.notion.inventory.handler.NotionEventHandler;
import io.huyvu.notion.inventory.model.LDBComparableTitle;
import io.huyvu.notion.inventory.model.LDBRecipe;
import io.huyvu.notion.inventory.repository.LocalRepository;
import io.huyvu.notion.inventory.repository.NotionRepository;
import notion.api.v1.model.pages.Page;

import java.util.List;
import java.util.Optional;

public class NotionEventListener {
    private final NotionRepository notionRepository;
    private final LocalRepository localRepository;
    private final NotionEventHandler notionEventHandler;

    public NotionEventListener(NotionRepository notionRepo, LocalRepository localRepository, NotionEventHandler notionEventHandler) {
        this.notionRepository = notionRepo;
        this.localRepository = localRepository;
        this.notionEventHandler = notionEventHandler;
    }

    private boolean isAnyIngredientUpdated() {
        List<Page> ingredients = notionRepository.findAllIngredients();
        var result = false;
        for (Page ndbIngredient : ingredients) {
            var id = ndbIngredient.getId();
            var optionalLDBIngredient = localRepository.findIngredientById(id);
            if (optionalLDBIngredient.isEmpty() || isTitleDifference(ndbIngredient, optionalLDBIngredient.get())) {
                notionEventHandler.onAnyIngredientTitleUpdated(ndbIngredient);
                result = true;
            }
        }
        return result;
    }

    private boolean isTitleDifference(Page page, LDBComparableTitle comparableTitle) {
        var ndbTitle = NotionPageUtils.getPageTitle(page);
        var ldbTitle = comparableTitle.getTitle();
        return !ndbTitle.equals(ldbTitle);
    }


    public void listen() {
        boolean isAnyIngredientTitleUpdated = isAnyIngredientUpdated();
        if(isAnyIngredientTitleUpdated) {
            notionEventHandler.onAfterAnyIngredientTitleUpdated();
        }


        var anyRecipeUpdatedResult = isAnyRecipeUpdated();
        if(anyRecipeUpdatedResult.isAnyTitleUpdated) {
            notionEventHandler.onAnyRecipeTitleUpdated();
        }
        if(anyRecipeUpdatedResult.isIngredientsUpdated){
            notionEventHandler.onAnyRecipeIngredientUpdated();
        }

        if(!isAnyIngredientTitleUpdated) {
            notionEventHandler.onNotThingChange();
        }


    }

    private AnyRecipeUpdatedResult isAnyRecipeUpdated() {
        boolean isTitleUpdated = false;
        boolean isIngredientsUpdated = false;
        var ndbRecipes = notionRepository.findAllRecipes();
        for (Page ndbRecipe : ndbRecipes) {
            var optionalLDBRecipe = localRepository.findRecipeById(ndbRecipe.getId());
            if (optionalLDBRecipe.isEmpty() || isTitleDifference(ndbRecipe, optionalLDBRecipe.get())) {
                notionEventHandler.onRecipeTitleUpdated(ndbRecipe);
            }

            if(optionalLDBRecipe.isEmpty() || isIngredientsUpdated(ndbRecipe)) {
                notionEventHandler.onRecipeIngredientsUpdated(ndbRecipe);
            }
        }

        return new AnyRecipeUpdatedResult(isTitleUpdated, isIngredientsUpdated);
    }

    private boolean isIngredientsUpdated(Page ndbRecipe) {
        return false;
    }

    private record AnyRecipeUpdatedResult(boolean isAnyTitleUpdated, boolean isIngredientsUpdated) {
    }
}
