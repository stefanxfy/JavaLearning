package com.stefan.designPattern.factory;

/**
 * 工厂方法模式
 */
public class CarFactory5 {
    public static ICar create(String name) {
        if ("bm".equals(name)) {
            return BmFactory.create();
        } else if ("benz".equals(name)) {
            return BenzFactory.create();
        } else {
            throw new RuntimeException("请新增汽车工厂");
        }
    }
}
