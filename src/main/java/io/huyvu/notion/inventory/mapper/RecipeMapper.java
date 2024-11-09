package io.huyvu.notion.inventory.mapper;

import io.huyvu.notion.inventory.model.LDBRecipe;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

public interface RecipeMapper {
    @Select("""
            SELECT id, title, last_edited_time
              FROM recipes
             WHERE id = #{id}
            """)
    Optional<LDBRecipe> findById(String id);
}
