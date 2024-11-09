package io.huyvu.notion.inventory;

import io.huyvu.notion.inventory.model.LDBComparableTitle;
import io.huyvu.notion.inventory.repository.LocalRepository;
import io.huyvu.notion.inventory.repository.NotionRepository;
import notion.api.v1.model.common.PropertyType;
import notion.api.v1.model.pages.Page;
import notion.api.v1.model.pages.PageProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class NotionEventListener {
    private final NotionRepository notionRepository;
    private final LocalRepository localRepository;
    private Consumer<Page> onAnyIngredientTitleUpdated = page -> {};

    public NotionEventListener(NotionRepository notionRepo, LocalRepository localRepository) {
        this.notionRepository = notionRepo;
        this.localRepository = localRepository;
    }


    public void setOnAnyIngredientTitleUpdated(Consumer<Page> onAnyIngredientTitleUpdated) {
        this.onAnyIngredientTitleUpdated = onAnyIngredientTitleUpdated;
    }

    private boolean isAnyIngredientTitleUpdated() {
        List<Page> ingredients = notionRepository.findAllIngredients();
        for (Page ndbIngredient : ingredients) {
            var id = ndbIngredient.getId();
            var optionalLDBIngredient = localRepository.findIngredientById(id);
            if (optionalLDBIngredient.isEmpty()) {
                onAnyIngredientTitleUpdated.accept(ndbIngredient);
                continue;
            }

            var ldbIngredient = optionalLDBIngredient.get();

            if(isTitleDifference(ndbIngredient, ldbIngredient)){
                onAnyIngredientTitleUpdated.accept(ndbIngredient);
            }

        }
        return false;
    }

    private boolean isTitleDifference(Page page, LDBComparableTitle comparableTitle) {
        var properties = page.getProperties();
        var ndbTitle = getPageTitle(properties);
        var ldbTitle = comparableTitle.getTitle();
        return ndbTitle.equals(ldbTitle);
    }

    @NotNull
    private String getPageTitle(Map<String, PageProperty> properties) {
        PageProperty pageTitleProperty = null;
        for (var entry : properties.values()) {
            var type = entry.getType();
            if (PropertyType.Title.equals(type)) {
                pageTitleProperty = entry;
            }
        }

        if (pageTitleProperty == null) {
            throw new RuntimeException("Page title property not found.");
        }
        var titles = pageTitleProperty.getTitle();
        if (titles == null) {
            throw new RuntimeException("Page title property is null");
        }

        if (titles.isEmpty()) {
            return "";
        }
        var plainText = titles.getFirst().getPlainText();

        if(plainText == null){
            return "";
        }
        return plainText;
    }

    public void listen() {
        boolean isAnyIngredientTitleUpdated = isAnyIngredientTitleUpdated();
    }
}
