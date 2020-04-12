package com.stefan.designPattern.singleton;

/**
 * 枚举无法实现 多例模式
 */
public class Test2 {
    public static void main(String[] args) {
        System.out.println("USER: " + MultIdGenerator1.USER.toString() + "-->" + MultIdGenerator1.USER.getId());
        System.out.println("USER: " + MultIdGenerator1.USER.toString() + "-->" + MultIdGenerator1.USER.getId());

        System.out.println("PRODUCT: " + MultIdGenerator1.PRODUCT.toString() + "-->" + MultIdGenerator1.USER.getId());
        System.out.println("PRODUCT: " + MultIdGenerator1.PRODUCT.toString() + "-->" + MultIdGenerator1.USER.getId());

        System.out.println("ORDER: " + MultIdGenerator1.ORDER.toString() + "-->" + MultIdGenerator1.USER.getId());
        System.out.println("ORDER: " + MultIdGenerator1.ORDER.toString() + "-->" + MultIdGenerator1.USER.getId());

        //USER: USER-->1
        //USER: USER-->2
        //PRODUCT: PRODUCT-->3
        //PRODUCT: PRODUCT-->4
        //ORDER: ORDER-->5
        //ORDER: ORDER-->6
    }
}
