package com.stefan.designPattern.factory.abstractFactory;

import com.stefan.designPattern.factory.ICar;

public interface ICarFactory {
    ICar createBySport();
    ICar createByHouseHold();
}
