package com.stefan.designPattern.factory;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Car {
    String value() default "";
}
