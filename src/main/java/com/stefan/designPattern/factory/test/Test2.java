package com.stefan.designPattern.factory.test;

import com.stefan.designPattern.factory.CarFactory2;
import com.stefan.designPattern.factory.ICar;

public class Test2 {
    public static void main(String[] args) {
        ICar bmICar1 = CarFactory2.valueOf("bm");
        ICar bmICar2 = CarFactory2.valueOf("bm");
        bmICar1.run();
        bmICar2.run();
        System.out.println("这是同一辆车：" + (bmICar1 == bmICar2));

        //大部分工厂类都是以“Factory”这个单词结尾的，但也不是必须的，
        // 比如 Java 中的 DateFormat、Calender。
        // 除此之外，工厂类中创建对象的方法一般都是 create 开头，
        // 比如代码中的 createParser()，但有的也命名为 getInstance()、createInstance()、newInstance()，
        // 有的甚至命名为 valueOf()（比如 Java String 类的 valueOf() 函数）等等，

        //对于每次新增汽车类型都要修改CarFactory，那这是不是违反了开闭原则呢？
        // 实际上，如果不是需要频繁地添加新的Car，只是偶尔修改一下 CarFactory 代码，
        // 稍微不符合开闭原则，也是完全可以接受的。
        //如何优雅做到不违反开闭原则呢？只需要做到新增汽车类型，可以自动注解到工厂中。
        //1、如果在Sptring容器的话，可以利用Spring装载类的机制。
        //2、自我实现注解
    }
}
