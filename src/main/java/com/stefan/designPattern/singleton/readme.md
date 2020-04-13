[TOC]

## 单例设计模式（Singleton Design Pattern）

### 一、定义
一个类只允许创建一个对象（或者实例），那这个类就是一个单例类，这种设计模式就叫作单例设计模式，简称单例模式。

### 二、用途

从业务概念上，有些数据在系统中只应该保存一份，就比较适合设计为单例类。比如，无状态工具类、全局配置信息类。


### 三、实现方式
以**唯一递增 ID 生成器**为案例
单例模式有两种模式，饿汉式和懒汉式。

#### 1、饿汉式

饿汉式在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，所以天生是线程安全的。
```java
public class HungryIdGenerator {
    private static final HungryIdGenerator instance = new HungryIdGenerator();
    private AtomicLong id = new AtomicLong(0);
    private HungryIdGenerator() {}
    public static HungryIdGenerator getInstance() {
        return instance;
    }
    public long getId() {
        return id.incrementAndGet();
    }
}
```
**道听途说：**

有人觉得这种实现方式不好，因为不支持延迟加载，如果实例占用资源多（比如占用内存多）或初始化耗时（比如需要加载各种配置文件），提前初始化实例是一种浪费资源的行为。最好的方法应该在用到的时候再去初始化。

不过，我个人并不认同这样的观点，理由如下：

- 如果实例占用资源多，按照 fail-fast 的设计原则（有问题及早暴露），在程序启动时初始化单例，尽早报`OOM`，以免程序在正常运行中，第一次加载单例突然`OOM`导致系统崩溃。
- 如果加载比较耗时，更应该在程序启动时加初始化好单例了，而不是在程序运行中占用正常请求的时长，导致请求缓慢甚至超时报错。

#### 2、懒汉式

懒汉式相对于饿汉式的优势是支持延迟加载。在对外提供的方法内实例化，需要线程安全的处理。个人觉得懒汉式与饿汉式不分哪个最优，他们有着不同的应用场景。

```java
public class LazyIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private static volatile LazyIdGenerator instance = null;
    private LazyIdGenerator() {}

    public static synchronized LazyIdGenerator getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new LazyIdGenerator();
        return instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}
```

把`synchronized`加在方法上，使得每次获得实例都要操作锁，开销很大，性能很低。强烈不推荐这种方式实现单例。

#### 3、双重检测(double check)

双重检测是懒汉式的升级版，只有在第一次初始化时加锁，以后不会再加锁。个人觉得双重检测的懒汉式与饿汉式不分哪个最优，他们有着不同的应用场景。

```java
/**
 * 懒加载：double check
 */
public class DoubleCheckIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private static volatile DoubleCheckIdGenerator instance = null;
    private DoubleCheckIdGenerator() {}
    public static DoubleCheckIdGenerator getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (DoubleCheckIdGenerator.class) {
            if (instance != null) {
                return instance;
            }
            instance = new DoubleCheckIdGenerator();
            return instance;
        }
    }
    public long getId() {
        return id.incrementAndGet();
    }
}
```
`instance` 成员变量加了`volatile` 关键字，是为了防止指令重排，因为`instance = new DoubleCheckIdGenerator();` 并不是一个原子操作，其在jvn中至少做了三件事：

1. 给instance在堆上分配内存空间。(分配内存)
2. 调用`DoubleCheckIdGenerator`的构造函数等来初始化instance。（初始化）
3. 将instance对象指向分配的内存空间。（执行完这一步instance就不是null了）

在没有`volatile`修饰时，执行顺序可以是1,2,3，也可以是1,3,2。假设有两个线程，当一个线程执行了3，还没执行2，此时第二个线程来到第一个check，发现instance不为null，就直接返回了，这就出现问题，这时的instance并没有完全完成初始化。

听说在语言上高版本的 Java 已经在 JDK 内部实现中解决了这个问题（解决的方法很简单，只要把对象 new 操作和初始化操作设计为原子操作，就自然能禁止重排序）。但是并没有在官方资料中看到，所以以防万一，还是加上`volatile` 这个关键字。

#### 4、静态内部类

比双重检测更加简单的实现方法，那就是利用 Java 的静态内部类。它有点类似饿汉式，但又能做到了延迟加载。

```java
/**
 * 懒加载：静态内部类
 */
public class StaticInnerClassIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private StaticInnerClassIdGenerator(){}
    private static class SingletonHolder{
        private static final StaticInnerClassIdGenerator instance = new StaticInnerClassIdGenerator();
    }

    public static StaticInnerClassIdGenerator getInstance() {
        return SingletonHolder.instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}
```
`SingletonHolder`是一个静态内部类，当外部类 `StaticInnerClassIdGenerator`被加载的时候，并不会创建 `SingletonHolder` 实例对象。只有当调用 `getInstance()` 方法时，`SingletonHolder` 才会被加载，这个时候才会创建 `instance`。
`instance` 的唯一性、创建过程的线程安全性，都由 JVM 来保证。所以，这种实现方法既保证了线程安全，又能做到延迟加载。

#### 5、枚举

最简单的实现方式，基于枚举类型的单例实现。这种实现方式通过 Java 枚举类型本身的特性，保证了实例创建的线程安全性和实例的唯一性。
```java
/**
 * 枚举单例-饿汉，推荐
 */
public enum IdGeneratorEnum {
    INSTANCE;
    private AtomicLong id = new AtomicLong(0);
    public long getId() {
        return id.incrementAndGet();
    }
}
```

### 四、枚举单例模式是世界上最好的单例模式

饿汉式以及懒汉式中的双重检查式、静态内部类式都无法避免被反序列化和反射生成多个实例。而枚举方式实现的单例模式不仅能避免多线程同步的问题，也可以防止反序列化和反射的破坏。

**Joshua Bloch 在《Effective Java》中明确表明，枚举类型实现的单例模式是最佳的方式。**

枚举单例模式具有以下三个优点：

- 写法简洁，代码短小精悍。
- 线程安全。
- 防止反序列化和反射的破坏。

通过jad反编译工具将枚举单例反编译：

```java
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IdGeneratorEnum.java

package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

public final class IdGeneratorEnum extends Enum
{

    public static IdGeneratorEnum[] values()
    {
        return (IdGeneratorEnum[])$VALUES.clone();
    }

    public static IdGeneratorEnum valueOf(String name)
    {
        return (IdGeneratorEnum)Enum.valueOf(com/stefan/designPattern/singleton/IdGeneratorEnum, name);
    }

    private IdGeneratorEnum(String s, int i)
    {
        super(s, i);
        id = new AtomicLong(0L);
    }

    public long getId()
    {
        return id.incrementAndGet();
    }

    public static final IdGeneratorEnum INSTANCE;
    private AtomicLong id;
    private static final IdGeneratorEnum $VALUES[];

    static 
    {
        INSTANCE = new IdGeneratorEnum("INSTANCE", 0);
        $VALUES = (new IdGeneratorEnum[] {
            INSTANCE
        });
    }
}
```

#### 1、JVM级别的线程安全

反编译的代码中可以发现枚举中的各个枚举项都是通过static代码块来定义和初始化的，他们会在类被加载时完成初始化，而java类的加载由`JVM`保证线程安全。

#### 2、防止反序列化的破坏

Java的序列化专门对枚举的序列化做了规定，在序列化时，仅仅是将枚举对象的name属性输出到结果中，在反序列化时通过`java.lang.Enum`的`valueOf`方法来根据名字查找对象，而不是新建一个新的对象，所以就防止了反序列化导致单例破坏的现象。可以查看`java.io.ObjectInputStream#readObject`验证。`readObject`判断到枚举类时，调用的了这个方法`java.io.ObjectInputStream#readEnum`

```java
private Enum<?> readEnum(boolean unshared) throws IOException {
    if (this.bin.readByte() != 126) {
        throw new InternalError();
    } else {
        ObjectStreamClass desc = this.readClassDesc(false);
        if (!desc.isEnum()) {
            throw new InvalidClassException("non-enum class: " + desc);
        } else {
            int enumHandle = this.handles.assign(unshared ? unsharedMarker : null);
            ClassNotFoundException resolveEx = desc.getResolveException();
            if (resolveEx != null) {
                this.handles.markException(enumHandle, resolveEx);
            }

            String name = this.readString(false);
            Enum<?> result = null;
            Class<?> cl = desc.forClass();
            if (cl != null) {
                try {
                    Enum<?> en = Enum.valueOf(cl, name);
                    result = en;
                } catch (IllegalArgumentException var9) {
                    throw (IOException)(new InvalidObjectException("enum constant " + name + " does not exist in " + cl)).initCause(var9);
                }

                if (!unshared) {
                    this.handles.setObject(enumHandle, result);
                }
            }

            this.handles.finish(enumHandle);
            this.passHandle = enumHandle;
            return result;
        }
    }
}
```

#### 3、防止反射的破坏

对于反射，枚举类同样有防御措施，反射在通过`newInstance`创建对象时会检查这个类是否是枚举类，如果是枚举类就会`throw new IllegalArgumentException("Cannot reflectively create enum objects");`,如下是源码`java.lang.reflect.Constructor#newInstance`：

```java
public T newInstance(Object... initargs) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!this.override) {
            Class<?> caller = Reflection.getCallerClass();
            this.checkAccess(caller, this.clazz, this.clazz, this.modifiers);
        }

        if ((this.clazz.getModifiers() & 16384) != 0) {
            throw new IllegalArgumentException("Cannot reflectively create enum objects");
        } else {
            ConstructorAccessor ca = this.constructorAccessor;
            if (ca == null) {
                ca = this.acquireConstructorAccessor();
            }

            T inst = ca.newInstance(initargs);
            return inst;
        }
    }
```

### 五、单例模式存在哪些问题?

单例模式有节省资源、保证结果正确、方便管理等优点，同时也存在一些问题。

#### 1、单例对 OOP 特性的支持不友好

OOP 的四大特性是封装、抽象、继承、多态。单例模式对于其中的抽象、继承、多态都支持得不好。

就拿唯一递增id生成器来说，上面实现的单例IdGenerator 的使用方式违背了基于接口而非实现的设计原则，也就违背了广义上理解的 OOP 的抽象特性。如果未来某一天，我们希望针对不同的业务采用不同的 ID 生成算法。比如，订单 ID 和用户 ID 采用不同的 ID 生成器来生成。为了应对这个需求变化，我们需要修改所有用到 IdGenerator 类的地方，这样代码的改动就会比较大。

除此之外，单例对继承、多态特性的支持也不友好。这里我之所以会用“不友好”这个词，而非“完全不支持”，是因为从理论上来讲，单例类也可以被继承、也可以实现多态，只是实现起来会非常奇怪，会导致代码的可读性变差。所以，一旦你选择将某个类设计成到单例类，也就意味着放弃了继承和多态这两个强有力的面向对象特性，也就相当于损失了可以应对未来需求变化的扩展性。

#### 2、单例会隐藏类之间的依赖关系

通过构造函数、参数传递等方式声明的类之间的依赖关系，我们通过查看函数的定义，就能很容易识别出来。但是，单例类不需要显示创建、不需要依赖参数传递，在函数中直接调用就可以了。如果代码比较复杂，这种调用关系就会非常隐蔽。

#### 3、单例对代码的扩展性不友好

单例类只能有一个对象实例。如果未来某一天，我们需要在代码中创建两个实例或多个实例，那就要对代码有比较大的改动。

拿数据库连接池来举例说明，在系统设计初期，我们觉得系统中只应该有一个数据库连接池，这样能方便我们控制对数据库连接资源的消耗。所以，我们把数据库连接池类设计成了单例类。后期我们想把慢sql和普通sql进行隔离，这样就需要建立两个数据库连接池实例。但是初始数据库连接池设计成单例类，显然就无法适应这样的需求变更，也就是说，单例类在某些情况下会影响代码的扩展性、灵活性。

所以，数据库连接池、线程池这类的资源池，最好还是不要设计成单例类。实际上，一些开源的数据库连接池、线程池也确实没有设计成单例类。

#### 4、单例不支持有参数的构造函数

单例不支持有参数的构造函数，比如我们创建一个连接池的单例对象，我们没法通过参数来指定连接池的大小。解决方式是，将参数配置化。在单例实例化时，从外部读取参数。

### 六、单例模式的替代方案

为了保证全局唯一，除了使用单例，还可以用静态方法来实现。这其实就是平时常用的工具类了，但是它比单例更加不灵活，比如，它无法支持延迟加载，扩展性也很差。

```java
public class IdGeneratorUtil {
    private static AtomicLong id = new AtomicLong(0);
    public static long getId() {
        return id.incrementAndGet();
    }

    public static void main(String[] args) {
        System.out.println(IdGeneratorUtil.getId());
    }
}
```

实际上，类对象的全局唯一性可以通过多种不同的方式来保证。单例模式、工厂模式、IOC 容器（比如 Spring IOC 容器），还可以通过自我约束，自己在编写代码的时候自己保证不要创建两个类对象。

### 七、重新理解单例模式的唯一性

#### 1、单例模式的唯一性

经典的单例模式，创建的全局唯一对象，属于进程唯一性，如果一个系统部署了多个实例，就有多个进程，每个进程都存在唯一一个对象，且进程间的实例对象不是同一个对象。

#### 2、实现线程唯一的单例

实现线程唯一的单例，就是一个对象在一个线程只能存在一个，不同的线程有不同的对象。

```Java
/**
 * 线程唯一单例
 */
public class ThreadIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private static final ConcurrentHashMap<Long, ThreadIdGenerator> instances = new ConcurrentHashMap<Long, ThreadIdGenerator>();

    private ThreadIdGenerator() {}

    public ThreadIdGenerator getInstance() {
        long threadId = Thread.currentThread().getId();
        instances.putIfAbsent(threadId, new ThreadIdGenerator());
        return instances.get(threadId);
    }

    public long getId() {
        return id.incrementAndGet();
    }
}
```

在代码中，通过一个 `HashMap` 来存储对象，其中 key 是线程 ID，value 是对象。这样就可以做到不同的线程对应不同的对象，同一个线程只能对应一个对象。实际上，Java 语言本身提供了 `ThreadLocal` 工具类，可以更加轻松地实现线程唯一单例。

```java
/**
 * 线程唯一单例  ThreadLocal
 */
public class ThreadLocalIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private static ThreadLocal<ThreadLocalIdGenerator> instances = new ThreadLocal<ThreadLocalIdGenerator>();
    private ThreadLocalIdGenerator(){}
    public static ThreadLocalIdGenerator getIntance() {
        ThreadLocalIdGenerator instance = instances.get();
        if (instance != null) {
            return instance;
        }
        instance = new ThreadLocalIdGenerator();
        instances.set(instance);
        return instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}
// 代码测试：
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("sub1: " + ThreadLocalIdGenerator.getIntance().toString() + "-->" + ThreadLocalIdGenerator.getIntance().getId());
                System.out.println("sub2: " + ThreadLocalIdGenerator.getIntance().toString() + "-->" + ThreadLocalIdGenerator.getIntance().getId());

            }
        };
        thread.start();
        thread.join();
        
        System.out.println("main1: " + ThreadLocalIdGenerator.getIntance().toString() + "-->" + ThreadLocalIdGenerator.getIntance().getId());
        System.out.println("main2: " + ThreadLocalIdGenerator.getIntance().toString() + "-->" + ThreadLocalIdGenerator.getIntance().getId());
    }
}
// 控制台打印
sub1: com.stefan.designPattern.singleton.ThreadLocalIdGenerator@480a0d08-->1
sub2: com.stefan.designPattern.singleton.ThreadLocalIdGenerator@480a0d08-->2
main1: com.stefan.designPattern.singleton.ThreadLocalIdGenerator@4f3f5b24-->1
main2: com.stefan.designPattern.singleton.ThreadLocalIdGenerator@4f3f5b24-->2  
```

显然主线程和子线程生成的单例不是同一个，且一个线程调用多次生成的是同一个单例对象。

#### 3、实现集群唯一的单例

集群代表着有多个进程，实现集群唯一单例，就是进程间共享一个对象。

集群唯一的单例实现起来相对复杂。

（1）需要考虑共享对象存放在哪里，

（2）进程创建单例对象时需要加分布式锁，防止多个进程创建多个不同的对象。

#### 4、实现一个多例模式

“单例”指的是，一个类只能创建一个对象。对应地，“多例”指的就是，一个类可以创建多个对象，但是个数是有限制的，比如只能创建 3 个对象。

##### （1）以map存储多例的方式

```java
/**
 * 多例模式的唯一自增id生成器
 *  用户表一个IdGenerator
 *  商品表一个IdGenerator
 *  订单表一个IdGenerator
 */
public class MultIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private MultIdGenerator() {}
    public long getId() {
        return id.incrementAndGet();
    }
    private  static Map<String, MultIdGenerator> instances = new HashMap<String, MultIdGenerator>();
    static {
        instances.put(Type.USER, new MultIdGenerator());
        instances.put(Type.PRODUCT, new MultIdGenerator());
        instances.put(Type.ORDER, new MultIdGenerator());
    }

    public static MultIdGenerator getInstance(String type) {
        return instances.get(type);
    }

    public static final class Type {
        public static final String USER = "user";
        public static final String PRODUCT = "product";
        public static final String ORDER = "order";
    }
}
// 测试
public class Test3 {
    public static void main(String[] args) {
        System.out.println("USER: " + MultIdGenerator.getInstance(MultIdGenerator.Type.USER).toString() + "-->" + MultIdGenerator.getInstance(MultIdGenerator.Type.USER).getId());
        System.out.println("USER: " + MultIdGenerator.getInstance(MultIdGenerator.Type.USER).toString() + "-->" + MultIdGenerator.getInstance(MultIdGenerator.Type.USER).getId());

        System.out.println("PRODUCT: " + MultIdGenerator.getInstance(MultIdGenerator.Type.PRODUCT).toString() + "-->" + MultIdGenerator.getInstance(MultIdGenerator.Type.PRODUCT).getId());
        System.out.println("PRODUCT: " + MultIdGenerator.getInstance(MultIdGenerator.Type.PRODUCT).toString() + "-->" + MultIdGenerator.getInstance(MultIdGenerator.Type.PRODUCT).getId());

        System.out.println("ORDER: " + MultIdGenerator.getInstance(MultIdGenerator.Type.ORDER).toString() + "-->" + MultIdGenerator.getInstance(MultIdGenerator.Type.ORDER).getId());
        System.out.println("ORDER: " + MultIdGenerator.getInstance(MultIdGenerator.Type.ORDER).toString() + "-->" + MultIdGenerator.getInstance(MultIdGenerator.Type.ORDER).getId());

    }
}

// 控制台
USER: com.stefan.designPattern.singleton.MultIdGenerator@4f3f5b24-->1
USER: com.stefan.designPattern.singleton.MultIdGenerator@4f3f5b24-->2
PRODUCT: com.stefan.designPattern.singleton.MultIdGenerator@15aeb7ab-->1
PRODUCT: com.stefan.designPattern.singleton.MultIdGenerator@15aeb7ab-->2
ORDER: com.stefan.designPattern.singleton.MultIdGenerator@7b23ec81-->1
ORDER: com.stefan.designPattern.singleton.MultIdGenerator@7b23ec81-->2
```

##### （2）使用枚举实现多例模式

```java
/**
 * 多例模式的唯一自增id生成器
 *  用户表一个IdGenerator
 *  商品表一个IdGenerator
 *  订单表一个IdGenerator
 *
 *  枚举无法实现多例模式
 */
public enum MultIdGeneratorEnum {
    USER,
    PRODUCT,
    ORDER;
    private AtomicLong id = new AtomicLong(0);

    public long getId() {
        return id.incrementAndGet();
    }
}
测试
public class Test2 {
    public static void main(String[] args) {
        System.out.println("USER: " + MultIdGeneratorEnum.USER.toString() + "-->" + MultIdGeneratorEnum.USER.getId());
        System.out.println("USER: " + MultIdGeneratorEnum.USER.toString() + "-->" + MultIdGeneratorEnum.USER.getId());

        System.out.println("PRODUCT: " + MultIdGeneratorEnum.PRODUCT.toString() + "-->" + MultIdGeneratorEnum.PRODUCT.getId());
        System.out.println("PRODUCT: " + MultIdGeneratorEnum.PRODUCT.toString() + "-->" + MultIdGeneratorEnum.PRODUCT.getId());

        System.out.println("ORDER: " + MultIdGeneratorEnum.ORDER.toString() + "-->" + MultIdGeneratorEnum.ORDER.getId());
        System.out.println("ORDER: " + MultIdGeneratorEnum.ORDER.toString() + "-->" + MultIdGeneratorEnum.ORDER.getId());
        //USER: USER-->1
        //USER: USER-->2
        //PRODUCT: PRODUCT-->1
        //PRODUCT: PRODUCT-->2
        //ORDER: ORDER-->1
        //ORDER: ORDER-->2
    }
}
```

枚举的方式明显要简洁很多，**强烈推荐用枚举方式实现单例和多例模式。** 通过反编译查看源码枚举方式实现的多例其实和前面以map存储多例的方式差不多的原理，都是用`HashMap`把实例化对象存储起来。

使用`jad`反编译`MultIdGeneratorEnum.class` :

```java
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultIdGeneratorEnum.java

package com.stefan.designPattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

public final class MultIdGeneratorEnum extends Enum
{

    public static MultIdGeneratorEnum[] values()
    {
        return (MultIdGeneratorEnum[])$VALUES.clone();
    }

    public static MultIdGeneratorEnum valueOf(String name)
    {
        return (MultIdGeneratorEnum)Enum.valueOf(com/stefan/designPattern/singleton/MultIdGeneratorEnum, name);
    }

    private MultIdGeneratorEnum(String s, int i)
    {
        super(s, i);
        id = new AtomicLong(0L);
    }

    public long getId()
    {
        return id.incrementAndGet();
    }

    public static final MultIdGeneratorEnum USER;
    public static final MultIdGeneratorEnum PRODUCT;
    public static final MultIdGeneratorEnum ORDER;
    private AtomicLong id;
    private static final MultIdGeneratorEnum $VALUES[];

    static 
    {
        USER = new MultIdGeneratorEnum("USER", 0);
        PRODUCT = new MultIdGeneratorEnum("PRODUCT", 1);
        ORDER = new MultIdGeneratorEnum("ORDER", 2);
        $VALUES = (new MultIdGeneratorEnum[] {
            USER, PRODUCT, ORDER
        });
    }
}
```

多例模式很像工厂模式，它跟工厂模式的不同之处是，**多例模式创建的对象都是同一个类的对象，而工厂模式创建的是不同子类的对象。**

### 八、枚举源码补充

一个枚举类反编译之后，可以看到其继承自`java.lang.Enum`，其中有一个valueof的方法是直接调用`java.lang.Enum#valueOf`的。其底层是一个key为name，value为Enum类型的实例化对象。通过源码可以验证：

```java
package java.lang;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

public abstract class Enum<E extends Enum<E>> implements Comparable<E>, Serializable {
    private final String name;
    private final int ordinal;

    public final String name() {
        return this.name;
    }

    public final int ordinal() {
        return this.ordinal;
    }

    protected Enum(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }

    public String toString() {
        return this.name;
    }

    public final boolean equals(Object other) {
        return this == other;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public final int compareTo(E o) {
        if (this.getClass() != o.getClass() && this.getDeclaringClass() != o.getDeclaringClass()) {
            throw new ClassCastException();
        } else {
            return this.ordinal - o.ordinal;
        }
    }

    public final Class<E> getDeclaringClass() {
        Class<?> clazz = this.getClass();
        Class<?> zuper = clazz.getSuperclass();
        return zuper == Enum.class ? clazz : zuper;
    }

    public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
        //关键性代码，好比是hashmap缓存
        T result = (Enum)enumType.enumConstantDirectory().get(name);
        if (result != null) {
            return result;
        } else if (name == null) {
            throw new NullPointerException("Name is null");
        } else {
            throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." + name);
        }
    }

    protected final void finalize() {
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new InvalidObjectException("can't deserialize enum");
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("can't deserialize enum");
    }
}
```

`java.lang.Enum#valueOf`中第一行代码调用的是`java.lang.Class#enumConstantDirectory`，部分源码如下，Class类中有一个成员变量是`HashMap`，原理一目了然。

```java
private transient volatile Map<String, T> enumConstantDirectory;
Map<String, T> enumConstantDirectory() {
        Map<String, T> directory = this.enumConstantDirectory;
        if (directory == null) {
            T[] universe = this.getEnumConstantsShared();
            if (universe == null) {
                throw new IllegalArgumentException(this.getName() + " is not an enum type");
            }

            directory = new HashMap((int)((float)universe.length / 0.75F) + 1);
            Object[] var3 = universe;
            int var4 = universe.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                T constant = var3[var5];
                ((Map)directory).put(((Enum)constant).name(), constant);
            }

            this.enumConstantDirectory = (Map)directory;
        }
        return (Map)directory;
}
```

