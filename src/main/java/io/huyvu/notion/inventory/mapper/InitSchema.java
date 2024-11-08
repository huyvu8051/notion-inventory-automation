package io.huyvu.notion.inventory.mapper;

import org.apache.ibatis.annotations.Insert;

public interface InitSchema {

    @Insert("""
            CREATE TABLE IF NOT EXISTS ingredients
            (
                id               TEXT PRIMARY KEY,
                unique_id        TEXT UNIQUE,
                title            TEXT,
                last_edited_time TEXT
            )
            """)
    void createIngredients();


    @Insert("""
            CREATE TABLE IF NOT EXISTS recipes
            (
                id               TEXT PRIMARY KEY,
                unique_id        TEXT UNIQUE,
                title            TEXT,
                last_edited_time TEXT
            )""")
    void createRecipes();


    @Insert("""
            CREATE TABLE IF NOT EXISTS meal_plans
            (
                id        TEXT PRIMARY KEY,
                unique_id TEXT UNIQUE,
                meal_type TEXT,
                date      TEXT
            )""")
    void createMealPlans();


    @Insert("""
            CREATE TABLE IF NOT EXISTS recipe_ingredients
            (
                recipe_id     TEXT,
                ingredient_id TEXT,
                FOREIGN KEY (recipe_id) REFERENCES recipes (id),
                FOREIGN KEY (ingredient_id) REFERENCES ingredients (id),
                PRIMARY KEY (recipe_id, ingredient_id)
            )""")
    void createRecipeIngredients();


    @Insert("""
            CREATE TABLE IF NOT EXISTS meal_plan_recipes
            (
                meal_plan_id TEXT,
                recipe_id    TEXT,
                FOREIGN KEY (meal_plan_id) REFERENCES meal_plans (id),
                FOREIGN KEY (recipe_id) REFERENCES recipes (id),
                PRIMARY KEY (meal_plan_id, recipe_id)
            )""")
    void createMealPlanRecipes();

}
