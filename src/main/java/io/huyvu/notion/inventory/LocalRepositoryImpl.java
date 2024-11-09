package io.huyvu.notion.inventory;

import io.huyvu.notion.inventory.mapper.IngredientMapper;
import io.huyvu.notion.inventory.mapper.MealPlanMapper;
import io.huyvu.notion.inventory.model.LDBIngredient;
import io.huyvu.notion.inventory.repository.LocalRepository;

import java.util.List;
import java.util.Optional;

public class LocalRepositoryImpl implements LocalRepository {
    private final IngredientMapper ingredientMapper;
    private final MealPlanMapper mealPlanMapper;

    public LocalRepositoryImpl(IngredientMapper ingredientMapper, MealPlanMapper mealPlanMapper) {
        this.ingredientMapper = ingredientMapper;
        this.mealPlanMapper = mealPlanMapper;
    }


    @Override
    public Optional<LDBIngredient> findIngredientById(String id) {
        return ingredientMapper.findById(id);
    }
}
