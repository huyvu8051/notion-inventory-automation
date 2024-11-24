package io.huyvu.notion.inventory.repository;

import io.huyvu.notion.inventory.LimitedRequestHttpClient;
import io.huyvu.notion.inventory.NotionSlf4jLoggerDelegate;
import notion.api.v1.NotionClient;
import notion.api.v1.http.NotionHttpClient;
import notion.api.v1.http.NotionHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotionRepositoryTest {
    private NotionRepository notionRepository;

    @Mock
    private NotionHttpClient httpClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(httpClient.postTextBody(any(), any(), any(), any(), any()))
                .thenReturn(new NotionHttpResponse(200,
                        """
                                 {
                                  "object": "list",
                                  "results": [
                                    {
                                      "object": "page",
                                      "id": "1366a94a-90a6-801f-a13a-f69644e90821",
                                      "created_time": "2024-11-06T12:41:00.000Z",
                                      "last_edited_time": "2024-11-10T06:26:00.000Z",
                                      "created_by": {
                                        "object": "user",
                                        "id": "002cd80d-549e-4164-862a-c0495b4496ca"
                                      },
                                      "last_edited_by": {
                                        "object": "user",
                                        "id": "002cd80d-549e-4164-862a-c0495b4496ca"
                                      },
                                      "cover": null,
                                      "icon": {
                                        "type": "emoji",
                                        "emoji": "🐖"
                                      },
                                      "parent": {
                                        "type": "database_id",
                                        "database_id": "12c6a94a-90a6-81e5-88a8-f0ba8d7a72a9"
                                      },
                                      "archived": false,
                                      "in_trash": false,
                                      "properties": {
                                        "Store": {
                                          "id": "%3ASO%3B",
                                          "type": "multi_select",
                                          "multi_select": []
                                        },
                                        "ID": {
                                          "id": "TrVN",
                                          "type": "unique_id",
                                          "unique_id": {
                                            "prefix": "ING",
                                            "number": 146
                                          }
                                        },
                                        "Status": {
                                          "id": "Z_u%5D",
                                          "type": "select",
                                          "select": null
                                        },
                                        "💈 Recipes": {
                                          "id": "efqS",
                                          "type": "relation",
                                          "relation": [],
                                          "has_more": false
                                        },
                                        "Type": {
                                          "id": "k%5D_%3F",
                                          "type": "select",
                                          "select": null
                                        },
                                        "Ingredient": {
                                          "id": "title",
                                          "type": "title",
                                          "title": [
                                            {
                                              "type": "text",
                                              "text": {
                                                "content": "Thịt Heo",
                                                "link": null
                                              },
                                              "annotations": {
                                                "bold": false,
                                                "italic": false,
                                                "strikethrough": false,
                                                "underline": false,
                                                "code": false,
                                                "color": "default"
                                              },
                                              "plain_text": "Thịt Heo",
                                              "href": null
                                            }
                                          ]
                                        }
                                      },
                                      "url": "https://www.notion.so/Th-t-Heo-1366a94a90a6801fa13af69644e90821",
                                      "public_url": null
                                    },
                                    {
                                      "object": "page",
                                      "id": "1306a94a-90a6-800f-8248-f84a9ef831d0",
                                      "created_time": "2024-10-31T03:19:00.000Z",
                                      "last_edited_time": "2024-11-10T06:26:00.000Z",
                                      "created_by": {
                                        "object": "user",
                                        "id": "002cd80d-549e-4164-862a-c0495b4496ca"
                                      },
                                      "last_edited_by": {
                                        "object": "user",
                                        "id": "002cd80d-549e-4164-862a-c0495b4496ca"
                                      },
                                      "cover": null,
                                      "icon": {
                                        "type": "emoji",
                                        "emoji": "🐓"
                                      },
                                      "parent": {
                                        "type": "database_id",
                                        "database_id": "12c6a94a-90a6-81e5-88a8-f0ba8d7a72a9"
                                      },
                                      "archived": false,
                                      "in_trash": false,
                                      "properties": {
                                        "Store": {
                                          "id": "%3ASO%3B",
                                          "type": "multi_select",
                                          "multi_select": []
                                        },
                                        "ID": {
                                          "id": "TrVN",
                                          "type": "unique_id",
                                          "unique_id": {
                                            "prefix": "ING",
                                            "number": 145
                                          }
                                        },
                                        "Status": {
                                          "id": "Z_u%5D",
                                          "type": "select",
                                          "select": null
                                        },
                                        "💈 Recipes": {
                                          "id": "efqS",
                                          "type": "relation",
                                          "relation": [],
                                          "has_more": false
                                        },
                                        "Type": {
                                          "id": "k%5D_%3F",
                                          "type": "select",
                                          "select": {
                                            "id": "6856ba8f-1979-496a-8d9b-fe73fec41b48",
                                            "name": "Condiment",
                                            "color": "default"
                                          }
                                        },
                                        "Ingredient": {
                                          "id": "title",
                                          "type": "title",
                                          "title": [
                                            {
                                              "type": "text",
                                              "text": {
                                                "content": "Chicken wings",
                                                "link": null
                                              },
                                              "annotations": {
                                                "bold": false,
                                                "italic": false,
                                                "strikethrough": false,
                                                "underline": false,
                                                "code": false,
                                                "color": "default"
                                              },
                                              "plain_text": "Chicken wings",
                                              "href": null
                                            }
                                          ]
                                        }
                                      },
                                      "url": "https://www.notion.so/Chicken-wings-1306a94a90a6800f8248f84a9ef831d0",
                                      "public_url": null
                                    },
                                    {
                                      "object": "page",
                                      "id": "12f6a94a-90a6-80a3-9e62-c2379ee5f34b",
                                      "created_time": "2024-10-30T15:46:00.000Z",
                                      "last_edited_time": "2024-11-10T06:26:00.000Z",
                                      "created_by": {
                                        "object": "user",
                                        "id": "002cd80d-549e-4164-862a-c0495b4496ca"
                                      },
                                      "last_edited_by": {
                                        "object": "user",
                                        "id": "002cd80d-549e-4164-862a-c0495b4496ca"
                                      },
                                      "cover": null,
                                      "icon": {
                                        "type": "emoji",
                                        "emoji": "🐒"
                                      },
                                      "parent": {
                                        "type": "database_id",
                                        "database_id": "12c6a94a-90a6-81e5-88a8-f0ba8d7a72a9"
                                      },
                                      "archived": false,
                                      "in_trash": false,
                                      "properties": {
                                        "Store": {
                                          "id": "%3ASO%3B",
                                          "type": "multi_select",
                                          "multi_select": []
                                        },
                                        "ID": {
                                          "id": "TrVN",
                                          "type": "unique_id",
                                          "unique_id": {
                                            "prefix": "ING",
                                            "number": 144
                                          }
                                        },
                                        "Status": {
                                          "id": "Z_u%5D",
                                          "type": "select",
                                          "select": null
                                        },
                                        "💈 Recipes": {
                                          "id": "efqS",
                                          "type": "relation",
                                          "relation": [],
                                          "has_more": false
                                        },
                                        "Type": {
                                          "id": "k%5D_%3F",
                                          "type": "select",
                                          "select": {
                                            "id": "6856ba8f-1979-496a-8d9b-fe73fec41b48",
                                            "name": "Condiment",
                                            "color": "default"
                                          }
                                        },
                                        "Ingredient": {
                                          "id": "title",
                                          "type": "title",
                                          "title": [
                                            {
                                              "type": "text",
                                              "text": {
                                                "content": "Dragon fruit",
                                                "link": null
                                              },
                                              "annotations": {
                                                "bold": false,
                                                "italic": false,
                                                "strikethrough": false,
                                                "underline": false,
                                                "code": false,
                                                "color": "default"
                                              },
                                              "plain_text": "Dragon fruit",
                                              "href": null
                                            }
                                          ]
                                        }
                                      },
                                      "url": "https://www.notion.so/Dragon-fruit-12f6a94a90a680a39e62c2379ee5f34b",
                                      "public_url": null
                                    },
                                    {
                                      "object": "page",
                                      "id": "12f6a94a-90a6-80b6-95ee-da0f410f7e01",
                                      "created_time": "2024-10-30T15:45:00.000Z",
                                      "last_edited_time": "2024-11-10T06:26:00.000Z",
                                      "created_by": {
                                        "object": "user",
                                        "id": "002cd80d-549e-4164-862a-c0495b4496ca"
                                      },
                                      "last_edited_by": {
                                        "object": "user",
                                        "id": "002cd80d-549e-4164-862a-c0495b4496ca"
                                      },
                                      "cover": null,
                                      "icon": {
                                        "type": "emoji",
                                        "emoji": "🥤"
                                      },
                                      "parent": {
                                        "type": "database_id",
                                        "database_id": "12c6a94a-90a6-81e5-88a8-f0ba8d7a72a9"
                                      },
                                      "archived": false,
                                      "in_trash": false,
                                      "properties": {
                                        "Store": {
                                          "id": "%3ASO%3B",
                                          "type": "multi_select",
                                          "multi_select": []
                                        },
                                        "ID": {
                                          "id": "TrVN",
                                          "type": "unique_id",
                                          "unique_id": {
                                            "prefix": "ING",
                                            "number": 143
                                          }
                                        },
                                        "Status": {
                                          "id": "Z_u%5D",
                                          "type": "select",
                                          "select": null
                                        },
                                        "💈 Recipes": {
                                          "id": "efqS",
                                          "type": "relation",
                                          "relation": [],
                                          "has_more": false
                                        },
                                        "Type": {
                                          "id": "k%5D_%3F",
                                          "type": "select",
                                          "select": {
                                            "id": "6856ba8f-1979-496a-8d9b-fe73fec41b48",
                                            "name": "Condiment",
                                            "color": "default"
                                          }
                                        },
                                        "Ingredient": {
                                          "id": "title",
                                          "type": "title",
                                          "title": [
                                            {
                                              "type": "text",
                                              "text": {
                                                "content": "Soda water ",
                                                "link": null
                                              },
                                              "annotations": {
                                                "bold": false,
                                                "italic": false,
                                                "strikethrough": false,
                                                "underline": false,
                                                "code": false,
                                                "color": "default"
                                              },
                                              "plain_text": "Soda water ",
                                              "href": null
                                            }
                                          ]
                                        }
                                      },
                                      "url": "https://www.notion.so/Soda-water-12f6a94a90a680b695eeda0f410f7e01",
                                      "public_url": null
                                    },
                                    {
                                      "object": "page",
                                      "id": "12f6a94a-90a6-8035-8ec3-f22b240c7684",
                                      "created_time": "2024-10-30T10:22:00.000Z",
                                      "last_edited_time": "2024-11-10T06:26:00.000Z",
                                      "created_by": {
                                        "object": "user",
                                        "id": "002cd80d-549e-4164-862a-c0495b4496ca"
                                      },
                                      "last_edited_by": {
                                        "object": "user",
                                        "id": "002cd80d-549e-4164-862a-c0495b4496ca"
                                      },
                                      "cover": null,
                                      "icon": {
                                        "type": "emoji",
                                        "emoji": "🌿"
                                      },
                                      "parent": {
                                        "type": "database_id",
                                        "database_id": "12c6a94a-90a6-81e5-88a8-f0ba8d7a72a9"
                                      },
                                      "archived": false,
                                      "in_trash": false,
                                      "properties": {
                                        "Store": {
                                          "id": "%3ASO%3B",
                                          "type": "multi_select",
                                          "multi_select": []
                                        },
                                        "ID": {
                                          "id": "TrVN",
                                          "type": "unique_id",
                                          "unique_id": {
                                            "prefix": "ING",
                                            "number": 142
                                          }
                                        },
                                        "Status": {
                                          "id": "Z_u%5D",
                                          "type": "select",
                                          "select": null
                                        },
                                        "💈 Recipes": {
                                          "id": "efqS",
                                          "type": "relation",
                                          "relation": [],
                                          "has_more": false
                                        },
                                        "Type": {
                                          "id": "k%5D_%3F",
                                          "type": "select",
                                          "select": {
                                            "id": "6856ba8f-1979-496a-8d9b-fe73fec41b48",
                                            "name": "Condiment",
                                            "color": "default"
                                          }
                                        },
                                        "Ingredient": {
                                          "id": "title",
                                          "type": "title",
                                          "title": [
                                            {
                                              "type": "text",
                                              "text": {
                                                "content": "Coconut",
                                                "link": null
                                              },
                                              "annotations": {
                                                "bold": false,
                                                "italic": false,
                                                "strikethrough": false,
                                                "underline": false,
                                                "code": false,
                                                "color": "default"
                                              },
                                              "plain_text": "Coconut",
                                              "href": null
                                            }
                                          ]
                                        }
                                      },
                                      "url": "https://www.notion.so/Coconut-12f6a94a90a680358ec3f22b240c7684",
                                      "public_url": null
                                    }
                                  ],
                                  "next_cursor": "12e6a94a-90a6-80c7-81f3-c6c0fdc04c1d",
                                  "has_more": true,
                                  "type": "page_or_database",
                                  "page_or_database": {},
                                  "request_id": "ec29305a-8a95-45b4-b1f7-3f6cadc36310"
                                }""", new HashMap<>()));
        NotionClient notionClient = new NotionClient();
        notionClient.setHttpClient(new LimitedRequestHttpClient(httpClient, new ScheduledThreadPoolExecutor(1)));
        notionClient.setLogger(new NotionSlf4jLoggerDelegate());
        notionClient.setHttpClient(httpClient);
        notionRepository = new NotionRepository(notionClient);

    }

    @Test
    void findAllIngredients() {

        // Submit tasks to the executor
        for (int number : IntStream.range(0, 1000).toArray()) {
            notionRepository.findAllIngredients();
        }
        verify(httpClient, times(1000)).postTextBody(any(), any(), any(), any(), any());


    }


}