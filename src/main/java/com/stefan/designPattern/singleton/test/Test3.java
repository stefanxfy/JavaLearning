package com.stefan.designPattern.singleton.test;

import com.stefan.designPattern.singleton.MultIdGenerator;

public class Test3 {
    public static void main(String[] args) {
        System.out.println("USER: " + MultIdGenerator.getInstance(MultIdGenerator.Type.USER).toString() + "-->" + MultIdGenerator.getInstance(MultIdGenerator.Type.USER).getId());
        System.out.println("USER: " + MultIdGenerator.getInstance(MultIdGenerator.Type.USER).toString() + "-->" + MultIdGenerator.getInstance(MultIdGenerator.Type.USER).getId());

        System.out.println("PRODUCT: " + MultIdGenerator.getInstance(MultIdGenerator.Type.PRODUCT).toString() + "-->" + MultIdGenerator.getInstance(MultIdGenerator.Type.PRODUCT).getId());
        System.out.println("PRODUCT: " + MultIdGenerator.getInstance(MultIdGenerator.Type.PRODUCT).toString() + "-->" + MultIdGenerator.getInstance(MultIdGenerator.Type.PRODUCT).getId());

        System.out.println("ORDER: " + MultIdGenerator.getInstance(MultIdGenerator.Type.ORDER).toString() + "-->" + MultIdGenerator.getInstance(MultIdGenerator.Type.ORDER).getId());
        System.out.println("ORDER: " + MultIdGenerator.getInstance(MultIdGenerator.Type.ORDER).toString() + "-->" + MultIdGenerator.getInstance(MultIdGenerator.Type.ORDER).getId());

    }
}
