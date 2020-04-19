package com.stefan.designPattern.factory.abstractFactory;

import com.stefan.designPattern.factory.ICar;

public class BenzFactory implements ICarFactory{
    @Override
    public ICar createBySport() {
        return null;
    }

    @Override
    public ICar createByHouseHold() {
        return null;
    }
}
