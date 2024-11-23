package io.huyvu.notion.inventory;

import io.huyvu.notion.inventory.exception.PagePropertyAccessException;
import notion.api.v1.model.common.PropertyType;
import notion.api.v1.model.pages.Page;
import notion.api.v1.model.pages.PageProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class NotionPageUtils {
    private NotionPageUtils(){}

    @NotNull
    public static String getPageTitle(Page page) {
        Map<String, PageProperty> properties = page.getProperties();
        PageProperty pageTitleProperty = null;
        for (var entry : properties.values()) {
            var type = entry.getType();
            if (PropertyType.Title.equals(type)) {
                pageTitleProperty = entry;
            }
        }

        if (pageTitleProperty == null) {
            throw new PagePropertyAccessException("Page title property not found.");
        }
        var titles = pageTitleProperty.getTitle();
        if (titles == null) {
            throw new PagePropertyAccessException("Page title property is null");
        }

        if (titles.isEmpty()) {
            return "";
        }
        var plainText = titles.getFirst().getPlainText();

        if (plainText == null) {
            return "";
        }
        return plainText;
    }

    public static String getUniqueId(Page page) {
        Map<String, PageProperty> properties = page.getProperties();
        PageProperty pageUniqueProperty = null;
        for (var entry : properties.values()) {
            var type = entry.getType();
            if (PropertyType.UniqueId.equals(type)) {
                pageUniqueProperty = entry;
            }
        }

        if (pageUniqueProperty == null) {
            throw new PagePropertyAccessException("Page unique id property not found.");
        }
        var uniqueId = pageUniqueProperty.getUniqueId();
        if (uniqueId == null) {
            throw new PagePropertyAccessException("Page unique id property is null");
        }

        return uniqueId.getPrefix() + "-" + uniqueId.getNumber();

    }
}
