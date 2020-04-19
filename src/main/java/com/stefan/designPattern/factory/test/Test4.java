package com.stefan.designPattern.factory.test;

import com.stefan.designPattern.factory.CarFactory4;
import com.stefan.designPattern.factory.CarFactory5;
import com.stefan.designPattern.factory.ICar;

public class Test4 {
    public static void main(String[] args) {
        System.out.println("简单工厂模式-----");
        ICar bmCar = CarFactory4.create("bm");
        bmCar.run();
        ICar benzCar = CarFactory4.create("benz");
        benzCar.run();

        System.out.println("工厂方法模式-----");
        ICar bmCar2 = CarFactory5.create("bm");
        bmCar2.run();
        ICar benzCar2 = CarFactory5.create("benz");
        benzCar2.run();

        //创建一辆汽车是一个极其复杂的工艺，此时将创建汽车的复杂过程放在一个工厂里，
        // 如果一种汽车的创建过程需要升级或者新增汽车都要修改工厂类。
        //本着让代码逻辑更加清晰，可读性更加好，我们要善于把复杂的过程抽象成一个函数，
        // 又为了职责单一，还需要进一步把复杂的过程抽象成一个类。
        //工厂方法模式并没有消除if-else，而是将if-else迁移到工厂的工厂，同样也可以用注解的方式去掉if-else


        //那什么时候该用工厂方法模式，而非简单工厂模式呢
        //之所以将某个代码块剥离出来，独立为函数或者类，原因是这个代码块的逻辑过于复杂，剥离之后能让代码更加清晰，更加可读、可维护。但是，如果代码块本身并不复杂，就几行代码而已，我们完全没必要将它拆分成单独的函数或者类。
        //基于这个设计思想，当对象的创建逻辑比较复杂，不只是简单的 new 一下就可以，而是要组合其他类对象，做各种初始化操作的时候，我们推荐使用工厂方法模式，将复杂的创建逻辑拆分到多个工厂类中，让每个工厂类都不至于过于复杂。而使用简单工厂模式，将所有的创建逻辑都放到一个工厂类中，会导致这个工厂类变得很复杂。
        //当每个对象的创建逻辑都比较简单的时候，我推荐使用简单工厂模式，将多个对象的创建逻辑放到一个工厂类中。当每个对象的创建逻辑都比较复杂的时候，为了避免设计一个过于庞大的简单工厂类，我推荐使用工厂方法模式，将创建逻辑拆分得更细，每个对象的创建逻辑独立到各自的工厂类中
        //现在，我们上升一个思维层面来看工厂模式，它的作用无外乎下面这四个。这也是判断要不要使用工厂模式的最本质的参考标准。封装变化：创建逻辑有可能变化，封装成工厂类之后，创建逻辑的变更对调用者透明。代码复用：创建代码抽离到独立的工厂类之后可以复用。隔离复杂性：封装复杂的创建逻辑，调用者无需了解如何创建对象。控制复杂度：将创建代码抽离出来，让原本的函数或类职责更单一，代码更简洁。
        //复杂度无法被消除，只能被转移

        //抽象工厂模式：提供一个接口，用于创建相关或依赖对象的家族，而不需要明确指定具体类
        //在JDK中工厂方法的命名有些规范：
        //1. valueOf() 返回与入参相等的对象
        //例如 Integer.valueOf()
        //2. getInstance() 返回单例对象
        //例如 Calendar.getInstance()
        //3. newInstance() 每次调用时返回新的对象

    }
}
