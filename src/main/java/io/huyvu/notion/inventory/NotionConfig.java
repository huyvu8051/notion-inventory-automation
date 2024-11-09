package io.huyvu.notion.inventory;

public class NotionConfig {


    private static final String SQLITE_PATH = "jdbc:sqlite:notion-inventory-sqlite.db";
    private static final String MAPPERS_PATH = "io.huyvu.notion.inventory.mapper";
    private String NOTION_API_KEY = System.getenv("NOTION_API_KEY");
    public static String NOTION_MEAL_PLAN_DB_ID = System.getenv("NOTION_MEAL_PLAN_DB_ID");
    public static String NOTION_INGREDIENT_DB_ID = System.getenv("NOTION_INGREDIENT_DB_ID");
    public static String NOTION_RECIPE_DB_ID = System.getenv("NOTION_RECIPE_DB_ID");
    public static String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");
    public static String LOG_LEVEL = System.getenv("LOG_LEVEL");
    private int interval = 3000;


    public static NotionConfig useDefault() {
        return new NotionConfig();
    }

    public int getInterval() {
        return this.interval;
    }

    public String getNotionApiKey() {
        return NOTION_API_KEY;
    }

    public String getNotionMealPlanDbId() {
        return NOTION_MEAL_PLAN_DB_ID;
    }

    public String getNotionIngredientDbId() {
        return NOTION_INGREDIENT_DB_ID;
    }

    public String getNotionRecipeDbId() {
        return NOTION_RECIPE_DB_ID;
    }

    public String getOpenaiApiKey() {
        return OPENAI_API_KEY;
    }

    public String getLogLevel() {
        return LOG_LEVEL;
    }

    public String getSqlitePath() {
        return SQLITE_PATH;
    }

    public String getMappersPath() {
        return MAPPERS_PATH;
    }
}
