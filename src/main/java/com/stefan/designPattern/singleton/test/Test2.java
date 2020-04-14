package com.stefan.designPattern.singleton.test;

import com.stefan.designPattern.singleton.MultIdGeneratorEnum;

/**
 * 枚举实现 多例模式
 */
public class Test2 {
    public static void main(String[] args) {
        System.out.println("USER: " + MultIdGeneratorEnum.USER.toString() + "-->" + MultIdGeneratorEnum.USER.getId());
        System.out.println("USER: " + MultIdGeneratorEnum.USER.toString() + "-->" + MultIdGeneratorEnum.USER.getId());

        System.out.println("PRODUCT: " + MultIdGeneratorEnum.PRODUCT.toString() + "-->" + MultIdGeneratorEnum.PRODUCT.getId());
        System.out.println("PRODUCT: " + MultIdGeneratorEnum.PRODUCT.toString() + "-->" + MultIdGeneratorEnum.PRODUCT.getId());

        System.out.println("ORDER: " + MultIdGeneratorEnum.ORDER.toString() + "-->" + MultIdGeneratorEnum.ORDER.getId());
        System.out.println("ORDER: " + MultIdGeneratorEnum.ORDER.toString() + "-->" + MultIdGeneratorEnum.ORDER.getId());
        //USER: USER-->1
        //USER: USER-->2
        //PRODUCT: PRODUCT-->1
        //PRODUCT: PRODUCT-->2
        //ORDER: ORDER-->1
        //ORDER: ORDER-->2
    }
}
