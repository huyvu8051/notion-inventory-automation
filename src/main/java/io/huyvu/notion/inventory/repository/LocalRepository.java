package io.huyvu.notion.inventory.repository;

import io.huyvu.notion.inventory.model.LDBIngredient;

import java.util.List;
import java.util.Optional;

public interface LocalRepository {

    Optional<LDBIngredient> findIngredientById(String id);
}
