package io.huyvu.notion.inventory.mapper;

import io.huyvu.notion.inventory.model.LDBIngredient;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IngredientMapper {
    @Select("""
            SELECT * 
              FROM INGREDIENT
            """)
    List<LDBIngredient> getIngredients();
}
