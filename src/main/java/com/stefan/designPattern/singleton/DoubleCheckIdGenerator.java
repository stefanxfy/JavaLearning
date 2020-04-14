package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 懒加载：double check
 */
public class DoubleCheckIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private static volatile DoubleCheckIdGenerator instance = null;
    private DoubleCheckIdGenerator() {}
    public static DoubleCheckIdGenerator getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (DoubleCheckIdGenerator.class) {
            if (instance != null) {
                return instance;
            }
            instance = new DoubleCheckIdGenerator();
            return instance;
        }
    }
    public long getId() {
        return id.incrementAndGet();
    }
}
