package com.stefan.designPattern.factory;

public class BenzFactory {
    public static ICar create() {
        ICar car = new BenzCar();
        //1、获取零件
        car.getElements();
        //2、组装零件
        car.assemble();
        return car;
    }
}
