package com.stefan.designPattern.factory.factoryAbstract;

public class Product1Factory implements IFactory {
    @Override
    public IProductA createProductA() {
        return new ProductA1();
    }

    @Override
    public IProductB createProductB() {
        return new ProductB1();
    }
}
