package io.huyvu.notion.inventory;

import notion.api.v1.http.JavaNetHttpClient;
import notion.api.v1.http.NotionHttpClient;
import notion.api.v1.http.NotionHttpResponse;
import notion.api.v1.logging.NotionLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class LimitedRequestHttpClient implements NotionHttpClient {
    private final NotionHttpClient delegate;
    private final Semaphore semaphore;
    private final ScheduledExecutorService executor;

    public LimitedRequestHttpClient() {
        this.delegate = new JavaNetHttpClient();
        this.executor = new ScheduledThreadPoolExecutor(1);
        this.semaphore = new Semaphore(1);
    }

    public LimitedRequestHttpClient(@NotNull NotionHttpClient delegate, @NotNull ScheduledExecutorService executor, @NotNull Semaphore semaphore) {
        this.delegate = delegate;
        this.executor = executor;
        this.semaphore = semaphore;
    }

    @NotNull
    @Override
    public String buildFullUrl(@NotNull String s, @NotNull String s1) {
        return delegate.buildFullUrl(s, s1);
    }

    @NotNull
    @Override
    public NotionHttpResponse get(@NotNull NotionLogger notionLogger, @NotNull String s, @NotNull Map<String, ? extends List<String>> map, @NotNull Map<String, String> map1) {
        semaphore.acquireUninterruptibly();
        var resp = delegate.get(notionLogger, s, map, map1);
        executor.schedule((Runnable) semaphore::release, 3, TimeUnit.SECONDS);
        return resp;
    }

    @NotNull
    @Override
    public NotionHttpResponse postTextBody(@NotNull NotionLogger notionLogger, @NotNull String s, @NotNull Map<String, ? extends List<String>> map, @NotNull String s1, @NotNull Map<String, String> map1) {
        semaphore.acquireUninterruptibly();
        var notionHttpResponse = delegate.postTextBody(notionLogger, s, map, s1, map1);
        executor.schedule((Runnable) semaphore::release, 3, TimeUnit.SECONDS);
        return notionHttpResponse;
    }

    @NotNull
    @Override
    public NotionHttpResponse patchTextBody(@NotNull NotionLogger notionLogger, @NotNull String s, @NotNull Map<String, ? extends List<String>> map, @NotNull String s1, @NotNull Map<String, String> map1) {
        semaphore.acquireUninterruptibly();
        var notionHttpResponse = delegate.patchTextBody(notionLogger, s, map, s1, map1);
        executor.schedule((Runnable) semaphore::release, 3, TimeUnit.SECONDS);
        return notionHttpResponse;
    }

    @NotNull
    @Override
    public NotionHttpResponse delete(@NotNull NotionLogger notionLogger, @NotNull String s, @NotNull Map<String, ? extends List<String>> map, @NotNull Map<String, String> map1) {
        semaphore.acquireUninterruptibly();
        var delete = delegate.delete(notionLogger, s, map, map1);
        executor.schedule((Runnable) semaphore::release, 3, TimeUnit.SECONDS);
        return delete;
    }

    @Override
    public void close() {
        delegate.close();
    }

    @NotNull
    @Override
    public String urlEncode(@NotNull String s) {
        return delegate.urlEncode(s);
    }

    @NotNull
    @Override
    public String buildQueryString(@NotNull Map<String, ? extends List<String>> map) {
        return delegate.buildQueryString(map);
    }

    @Override
    public void debugLogStart(@NotNull NotionLogger notionLogger, @NotNull String s, @NotNull String s1, @Nullable String s2) {
        delegate.debugLogStart(notionLogger, s, s1, s2);
    }

    @Override
    public void debugLogSuccess(@NotNull NotionLogger notionLogger, long l, @NotNull NotionHttpResponse notionHttpResponse) {
        delegate.debugLogSuccess(notionLogger, l, notionHttpResponse);
    }

    @Override
    public void warnLogFailure(@NotNull NotionLogger notionLogger, @NotNull Exception e) {
        delegate.warnLogFailure(notionLogger, e);
    }
}
