package io.huyvu.notion.inventory;

import notion.api.v1.logging.NotionLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotionSlf4jLoggerDelegate implements NotionLogger {
    private final Logger logger = LoggerFactory.getLogger(NotionSlf4jLoggerDelegate.class);

    @Override
    public void debug(@NotNull String s) {
        logger.debug(s);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(@NotNull String s, @Nullable Throwable throwable) {
        logger.debug(s, throwable);
    }

    @Override
    public void info(@NotNull String s, @Nullable Throwable throwable) {
        logger.info(s, throwable);
    }

    @Override
    public void warn(@NotNull String s, @Nullable Throwable throwable) {
        logger.warn(s, throwable);
    }

    @Override
    public void error(@NotNull String s, @Nullable Throwable throwable) {
        logger.error(s, throwable);
    }

    @Override
    public void info(@NotNull String s) {
        logger.info(s);
    }

    @Override
    public void warn(@NotNull String s) {
        logger.warn(s);
    }

    @Override
    public void error(@NotNull String s) {
        logger.error(s);
    }
}
