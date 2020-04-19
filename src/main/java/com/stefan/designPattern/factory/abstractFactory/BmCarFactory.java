package com.stefan.designPattern.factory.abstractFactory;

import com.stefan.designPattern.factory.BmCar;
import com.stefan.designPattern.factory.ICar;

public class BmCarFactory implements ICarFactory{
    @Override
    public ICar createBySport() {
        ICar car = new BmSportCar("跑车");
        //1、获取零件
        car.getElements();
        //2、组装零件
        car.assemble();
        return car;
    }

    @Override
    public ICar createByHouseHold() {
        ICar car = new BmHouseHoldCar("家用");
        //1、获取零件
        car.getElements();
        //2、组装零件
        car.assemble();
        return car;
    }
}
