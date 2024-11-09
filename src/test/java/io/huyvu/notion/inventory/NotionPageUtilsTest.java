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

        var value = new PageProperty("002", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, uniqueId);
        value.setType(PropertyType.UniqueId);

        properties.put("title", value);
        page = new Page(ObjectType.Page, "001", new Icon() {}, new Cover() {
        }, "", new User(""), "", new User(""), "", null, null, null, properties, null, null);
    }

    @Test
    void getUniqueId() {
        var result = NotionPageUtils.getUniqueId(page);
        assertEquals(result, "TEST-69");

    }
}