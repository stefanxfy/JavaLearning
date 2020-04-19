package com.stefan.designPattern.factory.abstractFactory;

import com.stefan.designPattern.factory.ICar;

public class MainCarFactory {
    public static ICarFactory create(String name) {
        if ("bmFactory".equals(name)) {
            return new BmCarFactory();
        } else if ("benz".equals(name)) {
            return new BenzFactory();
        } else {
            throw new RuntimeException("没有匹配的工厂");
        }
    }
}
