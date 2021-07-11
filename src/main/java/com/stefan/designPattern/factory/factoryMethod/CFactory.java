package com.stefan.designPattern.factory.factoryMethod;

import com.stefan.designPattern.factory.IProduct;
import com.stefan.designPattern.factory.ProductB;
import com.stefan.designPattern.factory.ProductC;

public class CFactory implements IFactory {
    @Override
    public IProduct create() {
        return new ProductC();
    }
}
