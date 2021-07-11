package com.stefan.designPattern.factory.factoryMethod;

import com.stefan.designPattern.factory.IProduct;
import com.stefan.designPattern.factory.ProductA;

public class AFactory implements IFactory {
    @Override
    public IProduct create() {
        return new ProductA();
    }
}
