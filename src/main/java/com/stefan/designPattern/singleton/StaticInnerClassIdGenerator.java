package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 懒加载：静态内部类
 */
public class StaticInnerClassIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private StaticInnerClassIdGenerator(){}
    private static class SingletonHolder{
        private static final StaticInnerClassIdGenerator instance = new StaticInnerClassIdGenerator();
    }

    public static StaticInnerClassIdGenerator getInstance() {
        return SingletonHolder.instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}
