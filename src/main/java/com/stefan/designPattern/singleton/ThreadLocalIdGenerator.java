package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程唯一单例  ThreadLocal
 */
public class ThreadLocalIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private static ThreadLocal<ThreadLocalIdGenerator> instances = new ThreadLocal<ThreadLocalIdGenerator>();
    private ThreadLocalIdGenerator(){}
    public static ThreadLocalIdGenerator getIntance() {
        ThreadLocalIdGenerator instance = instances.get();
        if (instance != null) {
            return instance;
        }
        instance = new ThreadLocalIdGenerator();
        instances.set(instance);
        return instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}
