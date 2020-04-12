package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

public class HungryIdGenerator {
    private static final HungryIdGenerator instance = new HungryIdGenerator();
    private AtomicLong id = new AtomicLong(0);
    private HungryIdGenerator() {}
    public static HungryIdGenerator getInstance() {
        return instance;
    }
    public long getId() {
        return id.incrementAndGet();
    }
}
