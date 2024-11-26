package io.huyvu.notion.inventory.repository;

import io.huyvu.notion.inventory.LimitedRequestHttpClient;
import io.huyvu.notion.inventory.TestUtils;
import notion.api.v1.NotionClient;
import notion.api.v1.http.NotionHttpClient;
import notion.api.v1.http.NotionHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NotionRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(NotionRepositoryTest.class);
    private NotionRepository notionRepository;
    private NotionHttpClient httpClient;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        var notionClient = new NotionClient();
        httpClient = mock(NotionHttpClient.class);
        when(httpClient.postTextBody(any(), any(), any(), any(), any()))
                .thenReturn(new NotionHttpResponse(200, TestUtils.readFileAsString("ingredients.json"), new HashMap<>()));
        notionClient.setHttpClient(new LimitedRequestHttpClient(httpClient));
        notionRepository = new NotionRepository(notionClient);

    }


    @Test
    void findAllIngredientsWithDelay() {
        long start = System.currentTimeMillis();
        var futures = IntStream.range(0, 5)
                .mapToObj(number -> CompletableFuture.supplyAsync(() -> {
                    var allIngredients = notionRepository.findAllIngredients();
                    log.info("ID {} size {}", number, allIngredients.size());
                    return System.currentTimeMillis();
                }))
                .toList();


        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        var list = futures.stream().map(CompletableFuture::join).sorted().toList();
        start -= 3000;
        for (Long l : list) {
            log.info("start {} took {}", start, l - start);
            assertTrue(l - start > 2500);
            start = l;
        }

    }

}