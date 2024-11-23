package io.huyvu.notion.inventory;

import notion.api.v1.model.common.Cover;
import notion.api.v1.model.common.Icon;
import notion.api.v1.model.common.ObjectType;
import notion.api.v1.model.common.PropertyType;
import notion.api.v1.model.databases.DatabaseProperty;
import notion.api.v1.model.pages.Page;
import notion.api.v1.model.pages.PageProperty;
import notion.api.v1.model.pages.PagePropertyItem;
import notion.api.v1.model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class NotionPageUtilsTest {
    Page page = null;

    @BeforeEach
    void setUp() {
        var properties = new HashMap<String, PageProperty>();

        PageProperty.UniqueId uniqueId = new PageProperty.UniqueId();
        uniqueId.setPrefix("TEST");
        uniqueId.setNumber(69);

        var uniqueIdProp = new PageProperty("002", PropertyType.UniqueId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, uniqueId);

        var richTexts = new ArrayList<PageProperty.RichText>();
        var richText = new PageProperty.RichText();
        richText.setPlainText("Don't make my heart hurt");
        richTexts.add(richText);

        var titleProp = new PageProperty("003", PropertyType.Title, richTexts, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, uniqueId);

        properties.put("unique_id", uniqueIdProp);
        properties.put("title", titleProp);
        page = new Page(ObjectType.Page, "001", new Icon() {}, new Cover() {
        }, "", new User(""), "", new User(""), "", null, null, null, properties, null, null);
    }

    @Test()
    void getUniqueId() {
        var result = NotionPageUtils.getUniqueId(page);
        assertEquals("TEST-69", result);

    }

    @Test
    void getPageTitle() {
        var result = NotionPageUtils.getPageTitle(page);
        assertEquals("Don't make my heart hurt", result);
    }
}