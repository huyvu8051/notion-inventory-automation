package io.huyvu.notion.inventory;

import io.huyvu.notion.inventory.listener.NotionEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationRunner {
    private final NotionEventListener eventListener;
    private final NotionConfig config;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    public ApplicationRunner(NotionConfig config, NotionEventListener eventListener) {
        this.config = config;
        this.eventListener = eventListener;
    }


    public void run() {
        while (true) {
            try {
                eventListener.listen();
            }catch (Exception e){
                logger.error(e.getMessage());
                e.printStackTrace();
            }finally {
                try {
                    Thread.sleep(config.getInterval());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
