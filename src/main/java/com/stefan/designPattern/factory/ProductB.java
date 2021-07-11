package com.stefan.designPattern.factory;

import java.util.Calendar;

@Product(name = "B")
public class ProductB implements IProduct {
    @Override
    public void doSomeThing() {
        System.out.println("ProductB...doSomeThing");
    }
}
