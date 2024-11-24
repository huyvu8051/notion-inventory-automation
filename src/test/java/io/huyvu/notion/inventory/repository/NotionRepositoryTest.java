package io.huyvu.notion.inventory.repository;

import io.huyvu.notion.inventory.*;
import notion.api.v1.NotionClient;
import notion.api.v1.http.JavaNetHttpClient;
import notion.api.v1.http.NotionHttpClient;
import notion.api.v1.http.NotionHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

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

        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {

            var futures = IntStream.range(0, 5)
                    .mapToObj(number -> CompletableFuture.runAsync(() -> {
                        var allIngredients = notionRepository.findAllIngredients();
                        log.info("ID {} size {}", number, allIngredients.size());
                    }))
                    .toArray(CompletableFuture[]::new);

            CompletableFuture.allOf(futures).get();

            // Shut down the executor
            executorService.shutdown();
        }

        // Verify that the method was called 3 times
        verify(httpClient, times(5)).postTextBody(any(), any(), any(), any(), any());

    }


}