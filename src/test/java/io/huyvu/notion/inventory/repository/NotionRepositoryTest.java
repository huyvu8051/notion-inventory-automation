package io.huyvu.notion.inventory.repository;

import io.huyvu.notion.inventory.*;
import notion.api.v1.NotionClient;
import notion.api.v1.http.JavaNetHttpClient;
import notion.api.v1.http.NotionHttpClient;
import notion.api.v1.http.NotionHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotionRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(NotionRepositoryTest.class);
    private NotionRepository notionRepository;
    private NotionHttpClient httpClient;

    @BeforeEach
    void setUp() throws IOException {
        var notionClient = new NotionClient();
        httpClient = mock(NotionHttpClient.class);
        when(httpClient.postTextBody(any(), any(), any(), any(), any()))
                .thenReturn(new NotionHttpResponse(200, TestUtils.readFileAsString("ingredients.json"), new HashMap<>()));
        notionClient.setHttpClient(new LimitedRequestHttpClient(httpClient));
        notionRepository = new NotionRepository(notionClient);

    }

    @Test
    void findAllIngredients() throws ExecutionException, InterruptedException {

        var futures = IntStream.range(0, 5)
                .mapToObj(number -> CompletableFuture.runAsync(() -> {
                    var allIngredients = notionRepository.findAllIngredients();
                    log.info("ID {} size {}", number, allIngredients.size());
                }))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).get();

        // Verify that the method was called 3 times
        verify(httpClient, times(5)).postTextBody(any(), any(), any(), any(), any());

    }


    @Test
    void findAllIngredientsWithDelay() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        var futures = IntStream.range(0, 5)
                .mapToObj(number -> {
                    var longCompletableFuture = CompletableFuture.supplyAsync(() -> {
                        var allIngredients = notionRepository.findAllIngredients();
                        log.info("ID {} size {}", number, allIngredients.size());
                        return System.currentTimeMillis();
                    });
                    return longCompletableFuture;
                })
                .toArray(CompletableFuture[]::new);



        CompletableFuture.allOf(futures).join();


        for (CompletableFuture<Long> future : futures) {
            var next = future.get();
            assertTrue(next - start >= 3000);
            start = next;
        }

    }

}