[TOC]

## 工厂模式

工厂模式属于创建型设计模式，其设计理念是将创建对象的过程抽象到工厂中，并且统一对外提供一个创建对象的接口，对象的创建过程对使用者透明。

一般情况下，工厂模式分为三种类型：简单工厂、工厂方法和抽象工厂。三种工厂模式的思想都非常简单，就是抽象、解耦。

个人认为，三种工厂模式是根据对象创建的复杂程度，一步步升级的结果，工厂方法是简单工厂的升级，抽象工厂又是工厂方法的升级。

以下通过创建汽车的案例，一步步解析三种工厂模式的递进和联系。

### 一、简单工厂模式

假设有一家生产汽车的公司，在起步阶段因为没有融资，建不起工厂，创建汽车和测试汽车在一起。

```java
public interface ICar {
    void run();
}
public class BmCar implements ICar {
      System.out.println("宝马汽车试跑");
}
public class BenzCar implements ICar {
      System.out.println("奔驰汽车试跑");
}
public class Test {
        /**
     * 假设这是一个包含创建汽车需求的业务代码
     * 1、创建汽车
     * 2、试跑
     * @param name
     * @return
     */
    public static void noFactoryTestCarRun(String name) {
        if ("bm".equals(name)) {
            return new BmCar("家用");
        } else if ("benz".equals(name)) {
            return new BenzCar();
        } else {
            throw new RuntimeException("没有你想要的汽车，如有需要请为工厂增加此汽车类型");
        }
    }
     public static void main(String[] args) {
          noFactoryTestCarRun("bm"); 
          noFactoryTestCarRun("benz");  
     }
}
```

在没有工厂的情况下，汽车的创建过程完全和业务代码耦合在一次，如果需要加一种汽车类型，势必要修改到业务代码。

此时汽车公司日益壮大，也融到了资，建了一个专门创建汽车的工厂，代码如下：

```java
/**
 * 简单工厂模式还叫作静态工厂方法模式
 */
public class CarFactory {

    public static ICar create(String name) {
        if ("bm".equals(name)) {
            return new BmCar();
        } else if ("benz".equals(name)) {
            return new BenzCar();
        }
        return null;
    }
}

public class Test {
    /**
     * 假设这是一个包含创建汽车需求的业务代码
     * 1、创建汽车
     * 2、试跑
     * @param name
     * @return
     */
    public static void testCarRun(String name) {
        ICar ICar = CarFactory.create(name);
        ICar.run();
    }
     public static void main(String[] args) {
        System.out.println("---有了钱，建立了专门的工厂---");
        testCarRun("bm");
        testCarRun("benz");
     }
}
```

以上看似有工厂和没工厂的区别不是很大，只是把业务代码中的`if-else`迁移到了工厂中，如果新增一个汽车类型，也是需要修改到`CarFactory.create`，同样违背了开闭原则。那为什么还要将汽车的创建过程单独抽离到工厂中呢？

为了让代码逻辑更加清晰，可读性和扩展性更好，我们要善于将功能独立的代码块封装成函数或者类。而为了让类的职责更加单一，最好将创建汽车的过程分离到一个类中，让这个类只负责对象的创建。这就是工厂模式的初衷，如果复杂度无法被消除，那就只能被转移。

实际上，如果不是需要频繁地添加新的Car，只是偶尔修改一下 `CarFactory `代码，稍微不符合开闭原则，也是完全可以接受的。

如果创建的对象是可以复用，而不是每次都创建新的，就需要将对象缓存起来，这有点类似单例模式和简单工厂模式的结合。

```java
/**
 * 实际上，测跑人员需要对一辆汽车测试多次，所以不能测试都要创建一辆新车出来。
 * 故需要一个仓库来存汽车。
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
```

### 二、工厂方法模式

创建一辆汽车是一个极其复杂的工艺，此时将创建汽车的复杂过程放在一个工厂里，势必会让代码的可读性和可扩展性降低。为了让代码逻辑更加清晰，易维护，且职责单一，需要将创建对象的过程进一步抽象化，为每种对象分别建立工厂类。

例如，一辆汽车的创建过程，需要生产获取零部件，组装零部件，最后才是一辆完整的汽车。随着汽车公司的壮大，有了更多的资金，于是分别为宝马和奔驰建立了工厂，代码如下：

```java
public class BmFactory {
    public static ICar create() {
        ICar car = new BmCar();
        //1、获取零件
        car.getElements();
        //2、组装零件
        car.assemble();
        return car;
    }
}

public class BenzFactory {
    public static ICar create() {
        ICar car = new BenzCar();
        //1、获取零件
        car.getElements();
        //2、组装零件
        car.assemble();
        return car;
    }
}

/**
 * 工厂方法模式
 */
public class CarFactory {
    public static ICar create(String name) {
        if ("bm".equals(name)) {
            return BmFactory.create();
        } else if ("benz".equals(name)) {
            return BenzFactory.create();
        } else {
            throw new RuntimeException("请新增汽车工厂");
        }
    }
}
```

工厂方法模式相当于”一个简单工厂“ 套 ”一个简单工厂“，工厂方法模式并没有消除`if-else`，而是将`if-else`迁移到了工厂的工厂。

### 三、抽象工厂模式

三五年过去了，汽车公司日益壮大，不再只生产家用汽车，还生产竞技用的车，比如赛车。宝马有家用型和竞技型，奔驰同样也有。而家用型和竞技型汽车的建造工艺不同，无法使用同一种创建流程，且创建流程都较为复杂。

按道理，家用型宝马和竞技型宝马都需要单独的工厂，如果以后的汽车类型越来越多，每增加一种汽车类型就要建两个工厂，这个成本就有些高了。此时抽象工厂模式就应运而生。

抽象工厂模式：用于创建相关或者有依赖对象的家族，而不需要明确指定具体类。

汽车公司为了节流开源，将原来的宝马工厂扩大，既可以生产家用型宝马，又可以生产竞技型宝马。代码如下：

```java
//家用型宝马
public class BmHouseHoldCar extends BmCar {

    public BmHouseHoldCar(String version) {
        super(version);
    }
}

//竞技型宝马
public class BmSportCar extends BmCar {

    public BmSportCar(String version) {
        super(version);
    }
}

//抽象工厂
public interface ICarFactory {
    ICar createBySport();
    ICar createByHouseHold();
}

//宝马工厂，既可以生产家用车，又可以生产跑车
public class BmCarFactory implements ICarFactory{
    @Override
    public ICar createBySport() {
        ICar car = new BmSportCar("跑车");
        //1、获取零件
        car.getElements();
        //2、组装零件
        car.assemble();
        return car;
    }

    @Override
    public ICar createByHouseHold() {
        ICar car = new BmHouseHoldCar("家用");
        //1、获取零件
        car.getElements();
        //2、组装零件
        car.assemble();
        return car;
    }
}

//主工厂，生产工厂的工厂
public class MainCarFactory {
    public static ICarFactory create(String name) {
        if ("bmFactory".equals(name)) {
            return new BmCarFactory();
        } else if ("benz".equals(name)) {
            return new BenzFactory();
        } else {
            throw new RuntimeException("没有匹配的工厂");
        }
    }
}
//部分代码省略，奔驰工厂类似

public class Test {
    public static void main(String[] args) {
        MainCarFactory.create("bmFactory").createByHouseHold();
        MainCarFactory.create("bmFactory").createBySport();
    }
}
```

### 四、总结

#### 1、三种工厂模式的适用场景。

三种工厂是一步步抽象升级的结果，如果一味追究模式，势必会过度封装。

三种工厂模式有着明显不同的适用场景：

- 当对象的创建过程较为简单时，使用简单工厂模式。
- 当对象的创建过程较为复杂时，使用工厂方法模式，将创建的过程进一步抽象化。
- 当对象有多种分类方式时，建议使用抽象工厂，将同种类的工厂抽象化，一个工厂可以创建一系列相关的对象。

#### 2、工厂模式的作用

工厂模式的作用无外乎以下四点，这也是判断要不要使用工厂模式的最本质的参考标准。

- 封装变化：创建逻辑有可能变化，封装成工厂类之后，创建逻辑的变更对调用者透明。
- 代码复用：创建代码抽离到独立的工厂类之后可以复用。
- 隔离复杂性：封装复杂的创建逻辑，调用者无需了解如何创建对象。
- 控制复杂度：将创建代码抽离出来，让原本的函数或类职责更单一，代码更简洁。

#### 3、工厂名和接口命名习惯

大部分工厂类都是以`Factory`单词结尾，但也不是必须的，比如`Java `中的 `DateFormat`、`Calender`。工厂类中创建对象的方法一般都是 `create` 开头，但有的也命名为 `getInstance()`、`newInstance()`，有的甚至命名为` valueOf()`（比如 `Java String` 类的 `valueOf()` 函数）等。

- `valueOf()` 返回与入参相等的对象，例如 `Integer.valueOf()`，这种情况一般是将对象缓存起来复用。
- `getInstance()` 返回单例对象，例如 `Calendar.getInstance()`，获取的是单例，同样需要缓存起来。
- `newInstance() `每次调用时返回新的对象，例如反射。