package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 多例模式的唯一自增id生成器
 *  用户表一个IdGenerator
 *  商品表一个IdGenerator
 *  订单表一个IdGenerator
 *
 *  枚举无法实现多例模式
 */
public enum MultIdGenerator1 {
    USER,
    PRODUCT,
    ORDER;
    private AtomicLong id = new AtomicLong(0);

    public long getId() {
        return id.incrementAndGet();
    }
}
