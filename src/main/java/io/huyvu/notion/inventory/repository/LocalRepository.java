package io.huyvu.notion.inventory.repository;

import io.huyvu.notion.inventory.model.LDBIngredient;

import java.util.List;

public interface LocalRepository {
    List<LDBIngredient> findAllIngredients();
}
