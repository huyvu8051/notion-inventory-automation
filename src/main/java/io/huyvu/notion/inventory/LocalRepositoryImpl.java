package io.huyvu.notion.inventory;

import io.huyvu.notion.inventory.mapper.IngredientMapper;
import io.huyvu.notion.inventory.mapper.MealPlanMapper;
import io.huyvu.notion.inventory.mapper.RecipeMapper;
import io.huyvu.notion.inventory.model.LDBIngredient;
import io.huyvu.notion.inventory.model.LDBRecipe;
import io.huyvu.notion.inventory.repository.LocalRepository;
import notion.api.v1.model.pages.Page;
import org.apache.ibatis.session.SqlSession;

import java.util.Optional;

public class LocalRepositoryImpl implements LocalRepository {
    private final SqlSession sqlSession;
    private final IngredientMapper ingredientMapper;
    private final MealPlanMapper mealPlanMapper;
    private final RecipeMapper recipeMapper;

    public LocalRepositoryImpl(SqlSession sqlSession, IngredientMapper ingredientMapper, MealPlanMapper mealPlanMapper, RecipeMapper recipeMapper) {
        this.sqlSession = sqlSession;
        this.ingredientMapper = ingredientMapper;
        this.mealPlanMapper = mealPlanMapper;
        this.recipeMapper = recipeMapper;
    }


    @Override
    public Optional<LDBIngredient> findIngredientById(String id) {
        return ingredientMapper.findById(id);
    }

    @Override
    public void saveIngredient(Page page) {
        var ldbIngredient = new LDBIngredient();
        ldbIngredient.setId(page.getId());
        ldbIngredient.setTitle(NotionPageUtils.getPageTitle(page));
        ldbIngredient.setLastEditedTime(page.getLastEditedTime());
        ldbIngredient.setUniqueId(NotionPageUtils.getUniqueId(page));
        ingredientMapper.save(ldbIngredient);
        sqlSession.commit();
    }

    @Override
    public Optional<LDBRecipe> findRecipeById(String id) {
        return recipeMapper.findById(id);
    }
}
