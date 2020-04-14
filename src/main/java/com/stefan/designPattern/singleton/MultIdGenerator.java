package com.stefan.designPattern.singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 多例模式的唯一自增id生成器
 *  用户表一个IdGenerator
 *  商品表一个IdGenerator
 *  订单表一个IdGenerator
 */
public class MultIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private MultIdGenerator() {}
    public long getId() {
        return id.incrementAndGet();
    }
    private  static Map<String, MultIdGenerator> instances = new HashMap<String, MultIdGenerator>();
    static {
        instances.put(Type.USER, new MultIdGenerator());
        instances.put(Type.PRODUCT, new MultIdGenerator());
        instances.put(Type.ORDER, new MultIdGenerator());
    }

    public static MultIdGenerator getInstance(String type) {
        return instances.get(type);
    }

    public static final class Type {
        public static final String USER = "user";
        public static final String PRODUCT = "product";
        public static final String ORDER = "order";
    }
}
