package com.stefan.designPattern.factory;

public class CarFactory4 {
    public static ICar create(String name) {
        if ("bm".equals(name)) {
            return createBm();
        } else if ("benz".equals(name)) {
            return createBenz();
        } else {
            throw new RuntimeException("没有你想要的汽车，如有需要请为工厂增加此汽车类型");
        }
    }

    /**
     * 只是简单模拟创建汽车的流程复杂度
     * @return
     */
    private static ICar createBm() {
        ICar car = new BmCar();
        //1、获取零件
        car.getElements();
        //2、组装零件
        car.assemble();
        return car;
    }

    private static ICar createBenz() {
        ICar car = new BenzCar();
        //1、获取零件
        car.getElements();
        //2、组装零件
        car.assemble();
        return car;
    }
}
