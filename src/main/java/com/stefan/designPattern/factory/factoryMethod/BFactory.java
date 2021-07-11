package com.stefan.designPattern.factory.factoryMethod;

import com.stefan.designPattern.factory.IProduct;
import com.stefan.designPattern.factory.ProductA;
import com.stefan.designPattern.factory.ProductB;

public class BFactory implements IFactory {
    @Override
    public IProduct create() {
        return new ProductB();
    }
}
