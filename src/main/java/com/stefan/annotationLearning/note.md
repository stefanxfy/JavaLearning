# 注解

[TOC]



### 注解定义

从JDK5开始,Java增加对元数据的支持，也就是注解，可以把注解理解为代码里的特殊标记，这些标记可以在编译，类加载，运行时被读取，并执行相应的处理。

### java中常见注解

- `@Override`，表示当前的方法定义将覆盖超类中的方法。

- `@Deprecated`，使用了注解为它的元素编译器将发出警告，因为注解@Deprecated是不赞成使用的代码，被弃用的代码。

- `@SuppressWarnings`，关闭编译器警告信息。

  

### 注解的分类

按照运行机制分为：源码注解、编译时注解、运行时注解

按照来源分为：jdk的注解、第三方注解、自己自定义注解

还有一类注解是: 元注解

### 元注解

元注解是可以注解到注解上的注解。它的作用和目的就是给自定义注解进行解释说明的。

元注解有`@Retention`、`@Documented`、`@Target`、`@Inherited`、`@Repeatable` 5 种。

- `@Retention`

Retention 的英文意为保留期的意思。当 @Retention 应用到一个注解上的时候，它解释说明了这个注解的的存活时间。

```java
public enum RetentionPolicy {
    /**
     * Annotations are to be discarded by the compiler.
     * 注解只在源码中存在，编译成.class文件就不存在了
     */
    SOURCE,

    /**
     * Annotations are to be recorded in the class file by the compiler
     * but need not be retained by the VM at run time.  This is the default
     * behavior.
     * 注解在源码和.clss文件中都存在
     * @Override、@Deprecated、@SuppressWarnings属于编译时注解
     */
    CLASS,

    /**
     * Annotations are to be recorded in the class file by the compiler and
     * retained by the VM at run time, so they may be read reflectively.
     * 在运行阶段还起作用，甚至会影响运行逻辑
     * @Autowired 属于运行时注解
     * @see java.lang.reflect.AnnotatedElement
     */
    RUNTIME
}
```

- `@Documented`

顾名思义，这个元注解肯定是和文档有关。它的作用是能够将注解中的元素包含到 Javadoc 中去。

- `@Target`

Target 是目标的意思，`@Target` 指定了注解运用的位置。

```java
public enum ElementType {
    /** Class, interface (including annotation type), or enum declaration 
    *标明该注解可以用于类、接口（包括注解类型）或enum声明
    */
    TYPE,

    /** Field declaration (includes enum constants) 
    *标明该注解可以用于字段(域)声明，包括enum实例 
    */
    FIELD,

    /** Method declaration 
    *标明该注解可以用于方法声明
    */
    METHOD,

    /** Formal parameter declaration 
    *标明该注解可以用于参数声明 
    */
    PARAMETER,

    /** Constructor declaration 
    *标明注解可以用于构造函数声明
    */
    CONSTRUCTOR,

    /** Local variable declaration 
    *标明注解可以用于局部变量声明
    */
    LOCAL_VARIABLE,

    /** Annotation type declaration 
    *标明注解可以用于注解声明(应用于另一个注解上)
    */
    ANNOTATION_TYPE,

    /** Package declaration 
    *标明注解可以用于包声明
    */
    PACKAGE,

    /**
     * Type parameter declaration
     *标明注解可以用于类型参数声明（1.8新加入）
     *
     * @since 1.8
     */
    TYPE_PARAMETER,

    /**
     * Use of a type
     *用于标注任意类型(不包括class)
     * @since 1.8
     */
    TYPE_USE
}
```



- `@Inherited`

Inherited 是继承的意思，但是它并不是说注解本身可以继承，而是说如果一个超类被 @Inherited 注解过的注解进行注解的话，那么如果它的子类没有被任何注解应用的话，那么这个子类就继承了超类的注解。

- `@Repeatable`

Repeatable 是可重复的意思，@Repeatable 是 Java 1.8 才加进来的。通常是注解的值可以同时取多个。