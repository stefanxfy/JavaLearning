package com.stefan.designPattern.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 实际上，测跑人员需要对一辆汽车测试多次，所以不能测试都要创建一辆新车出来。
 * 故需要一个仓库来存汽车。
 * 这有点类似单例模式和简单工厂模式的结合
 */
public class CarFactory2 {
    private static Map<String, ICar> carPool = new HashMap<String, ICar>();
    static {
        carPool.put("bm", new BmCar());
        carPool.put("benz", new BenzCar());
    }

    public static ICar valueOf(String name) {
        ICar ICar = carPool.get(name);
        if (ICar == null) {
            throw new RuntimeException("没有你想要的汽车，如有需要请为工厂增加此汽车类型");
        }
        return ICar;
    }
}
