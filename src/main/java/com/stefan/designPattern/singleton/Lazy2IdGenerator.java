package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 懒加载：静态内部类
 */
public class Lazy2IdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private Lazy2IdGenerator(){}
    private static class SingletonHolder{
        private static final Lazy2IdGenerator instance = new Lazy2IdGenerator();
    }

    public static Lazy2IdGenerator getInstance() {
        return SingletonHolder.instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}
