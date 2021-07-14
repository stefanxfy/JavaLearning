package com.stefan.designPattern.factory;

@Product
public class ProductC implements IProduct {
    @Override
    public void doSomeThing() {
        System.out.println("ProductC...doSomeThing");
    }
}
