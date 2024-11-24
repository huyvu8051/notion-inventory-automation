package io.huyvu.notion.inventory;

import io.huyvu.notion.inventory.listener.NotionEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;


public class ApplicationRunner {
    private final NotionEventListener eventListener;
    private final NotionConfig config;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
    private final Semaphore semaphore;
    private boolean running;

    public ApplicationRunner(NotionConfig config, NotionEventListener eventListener, Semaphore semaphore) {
        this.config = config;
        this.eventListener = eventListener;
        this.semaphore = semaphore;
        this.running = true;
    }


    public void run() {
        while (running) {
            try {
                eventListener.listen();
            } catch (Exception e) {
                logger.error("Unknown error(): {}", e.getMessage(), e);
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
