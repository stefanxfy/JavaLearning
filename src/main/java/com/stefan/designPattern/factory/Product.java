package com.stefan.designPattern.factory;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Product {
    public String name() default "";
}
