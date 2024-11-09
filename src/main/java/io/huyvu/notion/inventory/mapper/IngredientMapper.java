package io.huyvu.notion.inventory.mapper;

import io.huyvu.notion.inventory.model.LDBIngredient;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;


public interface IngredientMapper {
    @Select("""
            SELECT id
                 , title
                 , last_edited_time
              FROM ingredients
             WHERE id = #{id}
            """)
    Optional<LDBIngredient> findById(String id);

    @Insert("""
            INSERT INTO ingredients (id, unique_id, title, last_edited_time)
            VALUES (#{id}, #{uniqueId}, #{title}, #{lastEditedTime})
                ON CONFLICT(id) DO UPDATE SET unique_id = excluded.unique_id
                 , title                                = excluded.title
                 , last_edited_time                     = excluded.last_edited_time
            """)
    void save(LDBIngredient ldbIngredient);
}
