package com.stefan.designPattern.builder2;

/**
 * @author stefan
 * @date 2021/7/14 14:59
 */
public class Test2 {
    public static void main(String[] args) {
        Product2 product = Product2.Builder.create("1")
                .setS2("2")
                .setS3("3")
                .setI1(0)
                .setI2(-1)
                .setI3(3)
                .build();
        System.out.println(product.toString());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1").append("2").toString();
    }
}
