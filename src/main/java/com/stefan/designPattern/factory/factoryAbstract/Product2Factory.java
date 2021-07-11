package com.stefan.designPattern.factory.factoryAbstract;

public class Product2Factory implements IFactory {
    @Override
    public IProductA createProductA() {
        return new ProductA2();
    }

    @Override
    public IProductB createProductB() {
        return new ProductB2();
    }
}
