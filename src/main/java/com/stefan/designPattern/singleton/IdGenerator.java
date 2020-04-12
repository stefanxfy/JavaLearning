package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 多例模式
 */
public class IdGenerator {
    private AtomicLong id = new AtomicLong(0);

    private IdGenerator() {}

    public long getId() {
        return id.incrementAndGet();
    }
}
