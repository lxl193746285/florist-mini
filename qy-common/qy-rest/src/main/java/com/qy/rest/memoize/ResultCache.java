package com.qy.rest.memoize;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 结果缓存
 *
 * @author legendjw
 */
@Component
@RequestScope
public class ResultCache {
    /*
     * Since 'null' can be a valid result, we need a special object to indicate that
     * a method has never been executed before.
     *
     */
    public static final Object NONE = new Object();

    /*
     * Since ConcurrentHashMap doesn't accept 'null' as a valid value, we need a
     * special object to represent 'null'.
     *
     */
    public static final Object NULL = new Object();

    private final Map<ExecutionContext, Object> cache = new ConcurrentHashMap<>();

    public Object findCachedResult(ExecutionContext executionContext) {
        return cache.getOrDefault(executionContext, ResultCache.NONE);
    }

    public Object save(ExecutionContext executionContext, Object result) {
        return cache.computeIfAbsent(executionContext, key -> result == null ? ResultCache.NULL : result);
    }
}
