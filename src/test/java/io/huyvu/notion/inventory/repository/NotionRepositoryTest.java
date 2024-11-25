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
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void findAllIngredientsWithDelay() throws ExecutionException, InterruptedException {
        var futures = IntStream.range(0, 5)
                .mapToObj(number -> CompletableFuture.supplyAsync(() -> {
                    var allIngredients = notionRepository.findAllIngredients();
                    log.info("ID {} size {}", number, allIngredients.size());
                    return System.currentTimeMillis();
                }))
                .toList();


        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        var list = futures.stream().map(e -> {
            try {
                return e.get();
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        }).sorted().toList();

        var iterator = list.iterator();
        long start = iterator.next();

        while(iterator.hasNext()){
            long next = iterator.next();
            assertTrue(next - start >= 2500);
            start = next;
        }

        verify(httpClient, times(5)).postTextBody(any(), any(), any(), any(), any());


    }

}