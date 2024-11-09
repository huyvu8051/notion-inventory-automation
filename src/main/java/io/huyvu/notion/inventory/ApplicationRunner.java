package io.huyvu.notion.inventory;

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
                Thread.sleep(config.getInterval());
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }
}
