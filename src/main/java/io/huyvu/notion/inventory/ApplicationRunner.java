package io.huyvu.notion.inventory;

import io.huyvu.notion.inventory.listener.NotionEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ApplicationRunner {
    private final NotionEventListener eventListener;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
    private boolean running;

    public ApplicationRunner(NotionEventListener eventListener) {
        this.eventListener = eventListener;
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
