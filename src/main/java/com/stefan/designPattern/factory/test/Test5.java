package com.stefan.designPattern.factory.test;

import com.stefan.designPattern.factory.abstractFactory.BmCarFactory;
import com.stefan.designPattern.factory.abstractFactory.ICarFactory;
import com.stefan.designPattern.factory.abstractFactory.MainCarFactory;

public class Test5 {
    public static void main(String[] args) {
        MainCarFactory.create("bmFactory").createByHouseHold();
        MainCarFactory.create("bmFactory").createBySport();
    }
}
