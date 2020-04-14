package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

public class LazyIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private static volatile LazyIdGenerator instance = null;
    private LazyIdGenerator() {}

    public static synchronized LazyIdGenerator getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new LazyIdGenerator();
        return instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}
