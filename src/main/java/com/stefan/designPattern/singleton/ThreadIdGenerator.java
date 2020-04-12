package com.stefan.designPattern.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程唯一单例
 */
public class ThreadIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private static final ConcurrentHashMap<Long, ThreadIdGenerator> instances = new ConcurrentHashMap<Long, ThreadIdGenerator>();

    private ThreadIdGenerator() {}

    public ThreadIdGenerator getInstance() {
        long threadId = Thread.currentThread().getId();
        instances.putIfAbsent(threadId, new ThreadIdGenerator());
        return instances.get(threadId);
    }

    public long getId() {
        return id.incrementAndGet();
    }
}
