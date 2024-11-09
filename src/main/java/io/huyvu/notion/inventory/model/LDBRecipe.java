package io.huyvu.notion.inventory.model;

public class LDBRecipe extends LDBPage implements LDBComparableTitle {
    private String title;
    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
