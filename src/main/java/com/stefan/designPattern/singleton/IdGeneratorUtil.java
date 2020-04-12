package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

public class IdGeneratorUtil {
    private static AtomicLong id = new AtomicLong(0);
    public static long getId() {
        return id.incrementAndGet();
    }

    public static void main(String[] args) {
        System.out.println(IdGeneratorUtil.getId());
    }
}
