[TOC]

## 单例设计模式（Singleton Design Pattern）

### 一、定义：
一个类只允许创建一个对象（或者实例），那这个类就是一个单例类，
这种设计模式就叫作单例设计模式，简称单例模式。

### 二、用途：

从业务概念上，有些数据在系统中只应该保存一份，就比较适合设计为单例类。比如，全局配置信息类。


### 三、实现方式：
以**唯一递增 ID 生成器**为案例
单例模式有两种模式，饿汉式和懒汉式。

#### 1、饿汉式：

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
##### 道听途说：

有人觉得这种实现方式不好，因为不支持延迟加载，如果实例占用资源多（比如占用内存多）或初始化耗时（比如需要加载各种配置文件），提前初始化实例是一种浪费资源的行为。最好的方法应该在用到的时候再去初始化。

不过，我个人并不认同这样的观点，理由如下：

（1）如果初始化耗时长，那我们最好不要等到真正要用它的时候，才去执行这个耗时长的初始化过程，这会影响到系统的性能（比如，在响应客户端接口请求的时候，做这个初始化操作，会导致此请求的响应时间变长，甚至超时）。采用饿汉式实现方式，将耗时的初始化操作，提前到程序启动的时候完成，这样就能避免在程序运行的时候，再去初始化导致的性能问题。

（2）如果实例占用资源多，按照 fail-fast 的设计原则（有问题及早暴露），那我们也希望在程序启动时就将这个实例初始化好。如果资源不够，就会在程序启动的时候触发报错（比如 Java 中的 PermGen Space OOM），我们可以立即去修复。这样也能避免在程序运行一段时间后，突然因为初始化这个实例占用资源过多，导致系统崩溃，影响系统的可用性。

#### 2、懒汉式：

懒汉式相对于饿汉式的优势是支持延迟加载。在第一次调用的时候实例化自己，在对外提供的方法内实例化，需要线程安全的处理。个人觉得懒汉式与饿汉式不分哪个最优，他们有着不同的应用场景。

##### （1）双重检测(double check)

```java
public class LazyIdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private static volatile LazyIdGenerator instance = null;
    private LazyIdGenerator() {}
    public static LazyIdGenerator getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (LazyIdGenerator.class) {
            if (instance != null) {
                return instance;
            }
            instance = new LazyIdGenerator();
            return instance;
        }
    }
}
```
volatile 为了防止指令重排，听说高版本的jdk在语言上高版本的 Java 已经在 JDK 内部实现中解决了这个问题（解决的方法很简单，只要把对象 new 操作和初始化操作设计为原子操作，就自然能禁止重排序）。

##### （2）静态内部类

比双重检测更加简单的实现方法，那就是利用 Java 的静态内部类。它有点类似饿汉式，但又能做到了延迟加载。

```java
public class Lazy2IdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private Lazy2IdGenerator(){}
    private static class SingletonHolder{
        private static final Lazy2IdGenerator instance = new Lazy2IdGenerator();
    }

    public static Lazy2IdGenerator getInstance() {
        return SingletonHolder.instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}
```
`SingletonHolder`是一个静态内部类，当外部类 `Lazy2IdGenerator` 被加载的时候，并不会创建 `SingletonHolder` 实例对象。只有当调用 `getInstance()` 方法时，`SingletonHolder` 才会被加载，这个时候才会创建 `instance`。
`instance` 的唯一性、创建过程的线程安全性，都由 JVM 来保证。所以，这种实现方法既保证了线程安全，又能做到延迟加载。

##### （3）枚举

最简单的实现方式，基于枚举类型的单例实现。这种实现方式通过 Java 枚举类型本身的特性，保证了实例创建的线程安全性和实例的唯一性。
```java
public enum Lazy3IdGenerator {
    INSTANCE;
    private AtomicLong id = new AtomicLong(0);
    public long getId() {
        return id.incrementAndGet();
    }
}
```

### 四、反序列化和反射对单例模式的影响

饿汉式以及懒汉式中的双重检查式、静态内部类式都无法避免被反序列化和反射生成多个实例。而枚举方式实现的单例模式不仅能避免多线程同步的问题，也可以防止反序列化和反射的破坏。

**Joshua Bloch 在《Effective Java》中明确表明，枚举类型实现的单例模式是最佳的方式。**

枚举单例模式具有以下几点优点：

- 写法简洁，代码短小精悍。
- 线程安全。
- 防止反序列化和反射的破坏。

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
public class ThreadIdGenerator2 {
    private ThreadIdGenerator2(){}
    private AtomicLong id = new AtomicLong(0);
    private static ThreadLocal<ThreadIdGenerator2> instances = new ThreadLocal<ThreadIdGenerator2>();

    public static ThreadIdGenerator2 getIntance() {
        ThreadIdGenerator2 instance = instances.get();
        if (instance != null) {
            return instance;
        }
        instance = new ThreadIdGenerator2();
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
                System.out.println("sub1: " + ThreadIdGenerator2.getIntance().toString() + "-->" + ThreadIdGenerator2.getIntance().getId());
                System.out.println("sub2: " + ThreadIdGenerator2.getIntance().toString() + "-->" + ThreadIdGenerator2.getIntance().getId());

            }
        };
        thread.start();
        thread.join();
        System.out.println("main1: " + ThreadIdGenerator2.getIntance().toString() + "-->" + ThreadIdGenerator2.getIntance().getId());
        System.out.println("main2: " + ThreadIdGenerator2.getIntance().toString() + "-->" + ThreadIdGenerator2.getIntance().getId());
    }
}
// 控制台打印
sub1: ThreadIdGenerator2@480a0d08-->1
sub2: ThreadIdGenerator2@480a0d08-->2
main1: ThreadIdGenerator2@4f3f5b24-->1
main2: ThreadIdGenerator2@4f3f5b24-->2    
```

显然主线程和子线程生成的单例不是同一个，且一个线程调用多次生成的是同一个单例对象。

#### 3、实现集群唯一的单例

集群代表着有多个进程，实现集群唯一单例，就是进程间共享一个对象。

集群唯一的单例实现起来相对复杂。

（1）需要考虑共享对象存放在哪里，

（2）进程创建单例对象时需要加分布式锁，防止多个进程创建多个不同的对象。

#### 4、实现一个多例模式

“单例”指的是，一个类只能创建一个对象。对应地，“多例”指的就是，一个类可以创建多个对象，但是个数是有限制的，比如只能创建 3 个对象。

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

多例模式很像工厂模式，它跟工厂模式的不同之处是，**多例模式创建的对象都是同一个类的对象，而工厂模式创建的是不同子类的对象。**