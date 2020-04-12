package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程唯一单例  ThreadLocal
 */
public class ThreadIdGenerator2 {
    private AtomicLong id = new AtomicLong(0);
    private static ThreadLocal<ThreadIdGenerator2> instances = new ThreadLocal<ThreadIdGenerator2>();
    private ThreadIdGenerator2(){}
    public static ThreadIdGenerator2 getIntance() {
        ThreadIdGenerator2 instance = instances.get();
        if (instance != null) {
            return instance;
        }
        instance = new ThreadIdGenerator2();
        instances.set(instance);
        return instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}
