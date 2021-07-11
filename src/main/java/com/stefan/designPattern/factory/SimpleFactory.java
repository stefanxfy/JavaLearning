package com.stefan.designPattern.factory;

import cn.hutool.core.util.ClassUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleFactory {
    public static IProduct create(String name) {
        if ("A".equals(name)) {
            return new ProductA();
        }
        if ("B".equals(name)) {
            return new ProductB();
        }
        if ("C".equals(name)) {
            return new ProductC();
        }
        throw new UnsupportedOperationException("not match product, name=" + name);
    }

    public static IProduct getInstance(String name) {
        IProduct product = INSTANCE_MAP.get(name);
        if (product == null) {
            throw new UnsupportedOperationException("not match product, name=" + name);
        }
        return product;
    }

    private static final Map<String, IProduct> INSTANCE_MAP = new HashMap<>();

    public static IProduct getInstance2(Class<? extends IProduct> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("class is null");
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IProduct newInstance(Class<? extends IProduct> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("class is null");
        }
        IProduct product = INSTANCE_MAP.get(clazz.getSimpleName());
        if (product != null) {
            return product;
        }
        synchronized (SimpleFactory.INSTANCE_MAP) {
            product = INSTANCE_MAP.get(clazz.getSimpleName());
            if (product != null) {
                return product;
            }
            try {
                product = clazz.newInstance();
                INSTANCE_MAP.put(clazz.getSimpleName(), product);
                return product;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static {
        init();
        for (Map.Entry<String, IProduct> stringIProductEntry : INSTANCE_MAP.entrySet()) {
            System.out.println(stringIProductEntry.getKey() + "::" + stringIProductEntry.getValue());
        }
    }
    private static void init() {
        System.out.println("init....");
        Set<Class<?>> classSet = ClassUtil.scanPackageByAnnotation("com.stefan.designPattern.factory", Product.class);
        for (Class<?> clazz : classSet) {
            boolean isProduct = false;
            in : for (Class<?> i : clazz.getInterfaces()) {
                if (IProduct.class.equals(i)) {
                    isProduct = true;
                    break in;
                }
            }
            if (!isProduct) {
                continue;
            }
            Product product = clazz.getAnnotation(Product.class);
            String name = product.name();
            // 虽然指定name default是“”，但是这里取的话会隐式new String()
            // 所以必须用equals比较，不能用==
            if ("".equals(name)) {
                name = clazz.getSimpleName();
            }
            try {
                INSTANCE_MAP.put(name, (IProduct) clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

}
