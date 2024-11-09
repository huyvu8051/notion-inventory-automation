package io.huyvu.notion.inventory.repository;

import io.huyvu.notion.inventory.model.LDBIngredient;
import io.huyvu.notion.inventory.model.LDBRecipe;
import notion.api.v1.model.pages.Page;

import java.util.Optional;

public interface LocalRepository {

    Optional<LDBIngredient> findIngredientById(String id);

    void saveIngredient(Page page);

    Optional<LDBRecipe> findRecipeById(String id);
}
