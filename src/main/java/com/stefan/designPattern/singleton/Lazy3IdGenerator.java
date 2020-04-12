package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 懒加载：枚举，推荐
 */
public enum Lazy3IdGenerator {
    INSTANCE;
    private AtomicLong id = new AtomicLong(0);
    public long getId() {
        return id.incrementAndGet();
    }
}
