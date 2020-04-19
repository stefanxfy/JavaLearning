package com.stefan.designPattern.factory.test;

import com.stefan.designPattern.factory.BenzCar;
import com.stefan.designPattern.factory.BmCar;
import com.stefan.designPattern.factory.CarFactory;
import com.stefan.designPattern.factory.ICar;

public class Test1 {
    /**
     * 假设这是一个包含创建汽车需求的业务代码
     * 1、创建汽车
     * 2、试跑
     * @param name
     * @return
     */
    public static void noFactoryTestCarRun(String name) {
        ICar ICar = null;
        if ("bm".equals(name)) {
            ICar = new BmCar();
        } else if ("benz".equals(name)) {
            ICar = new BenzCar();
        }
        ICar.run();
    }

    public static void testCarRun(String name) {
        ICar ICar = CarFactory.create(name);
        ICar.run();
    }
    public static void main(String[] args) {
        //比如这里就是业务代码区，这里包含建造汽车的代码，在没有工厂的情况下
        System.out.println("---前期没钱，无工厂生产，生产和测试一起做---");
        noFactoryTestCarRun("bm");
        noFactoryTestCarRun("benz");

        System.out.println("---有了钱，建立了专门的工厂---");
        testCarRun("bm");
        testCarRun("benz");

        //看着似乎，有工厂和没工厂区别不大，只是把if-else换了一个地方，但是有一天市场扩大，需要生产新类型汽车保时捷（Porsche)，
        //1、没有工厂的前提下 (1)需要新建一个实现了Car的模具PorscheCar （2）修改业务代码noFactoryTestCarRun
        //2、有工厂的前提下 （1）需要新建一个实现了Car的模具PorscheCar （2）修改CarFactory.create
        //这也没什么区别，但是如果创建一个汽车的过程很复杂（本身很复杂），完全和业务代码耦合，对以后的代码维护和扩展成了极大的阻碍。
        //所以工厂模式的一大特点就是创建过程与业务代码解耦。
        // 为了让代码逻辑更加清晰，可读性更好，我们要善于将功能独立的代码块封装成函数
        // 可以将创建汽车的代码抽象成一个函数create，还是和测试汽车放在一个类里，
        // 但是为了让类的职责更加单一、代码更加清晰，我们还可以进一步将 create() 函数剥离到一个独立的类中，让这个类只负责对象的创建。



    }
}
