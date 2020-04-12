package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 懒加载：double check
 */
public class LazyIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private static volatile LazyIdGenerator instance = null;
    private LazyIdGenerator() {}
    public static LazyIdGenerator getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (LazyIdGenerator.class) {
            if (instance != null) {
                return instance;
            }
            instance = new LazyIdGenerator();
            return instance;
        }
    }
    public long getId() {
        return id.incrementAndGet();
    }
}
