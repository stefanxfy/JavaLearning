package com.stefan.designPattern.factory;

@Product(name = "A")
public class ProductA implements IProduct {
    @Override
    public void doSomeThing() {
        System.out.println("ProductA...doSomeThing");
    }
}
