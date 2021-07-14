package com.stefan.designPattern.factory.factoryAbstract;

public class Test {
    public static void main(String[] args) {
        IFactory factory1 = new Product1Factory();
        IProductA productA1 = factory1.createProductA();
        IProductB productB1 = factory1.createProductB();

    }
}
