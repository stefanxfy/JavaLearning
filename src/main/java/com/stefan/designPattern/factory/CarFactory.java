package com.stefan.designPattern.factory;

/**
 * 简单工厂模式还叫作静态工厂方法模式
 */
public class CarFactory {

    public static ICar create(String name) {
        if ("bm".equals(name)) {
            return new BmCar("家用");
        } else if ("benz".equals(name)) {
            return new BenzCar();
        }
        return null;
    }
}
