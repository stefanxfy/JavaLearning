package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 静态方法-实现唯一自增id生成器
 */
public class IdGeneratorUtil {
    private static AtomicLong id = new AtomicLong(0);
    public static long getId() {
        return id.incrementAndGet();
    }

    public static void main(String[] args) {
        System.out.println(IdGeneratorUtil.getId());
    }
}
