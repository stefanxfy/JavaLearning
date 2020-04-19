package com.stefan.designPattern.factory;

public class BmFactory {
    public static ICar create() {
        ICar car = new BmCar();
        //1、获取零件
        car.getElements();
        //2、组装零件
        car.assemble();
        return car;
    }
}
