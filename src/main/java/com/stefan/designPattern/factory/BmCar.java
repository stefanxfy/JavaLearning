package com.stefan.designPattern.factory;

@Car
public class BmCar implements ICar {
    private String version;

    public BmCar(String version) {
        this.version = version;
    }

    public BmCar() {
    }

    public String getVersion() {
        return version;
    }

    @Override
    public void run() {
        System.out.println("宝马汽车正在跑");
    }

    @Override
    public void getElements() {
        System.out.println("获取宝马零部件");
    }

    @Override
    public void assemble() {
        System.out.println("组装宝马零部件");
    }
}
