package io.huyvu.notion.inventory.repository;

import io.huyvu.notion.inventory.NotionConfig;
import io.huyvu.notion.inventory.NotionInventoryApplication;
import notion.api.v1.http.NotionHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotionRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(NotionRepositoryTest.class);
    private NotionRepository notionRepository;
    private NotionHttpClient httpClient;

    @BeforeEach
    void setUp() {

        var notionConfig = NotionConfig.useDefault();
        var notionClient = NotionInventoryApplication.getNotionClient(notionConfig);
        httpClient = notionClient.getHttpClient();
        httpClient = spy(httpClient);
        notionClient.setHttpClient(httpClient);


        notionRepository = new NotionRepository(notionClient);

    }

    @Test
    void findAllIngredients() {

        // Submit tasks to the executor
        for (int number : IntStream.range(0, 3).toArray()) {
            var allIngredients = notionRepository.findAllIngredients();
            log.info("Time {} size {}", number, allIngredients.size());
        }
        verify(httpClient, times(3)).postTextBody(any(), any(), any(), any(), any());


    }


}