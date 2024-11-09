package io.huyvu.notion.inventory.mapper;

import io.huyvu.notion.inventory.model.LDBIngredient;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;


public interface IngredientMapper {
    @Select("""
            SELECT id
                 , title
                 , last_edited_time
              FROM ingredients
             WHERE id = ${id}
            """)
    Optional<LDBIngredient> findById(String id);
}
