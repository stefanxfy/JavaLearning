package com.stefan.designPattern.factory.factoryMethod;

import com.stefan.designPattern.factory.IProduct;

public class Test {
    public static void main(String[] args) {
        IFactory factoryA = new AFactory();
        IProduct productA = factoryA.create();
        productA.doSomeThing();

        IFactory factoryB = new BFactory();
        IProduct productB = factoryB.create();
        productB.doSomeThing();

    }
}
