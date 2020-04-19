package com.stefan.designPattern.factory;

@Car("Benz")
public class BenzCar implements ICar {
    @Override
    public void run() {
        System.out.println("奔驰汽车正在跑");
    }

    @Override
    public void getElements() {
        System.out.println("获取奔驰零部件");
    }

    @Override
    public void assemble() {
        System.out.println("组装奔驰零部件");
    }
}
