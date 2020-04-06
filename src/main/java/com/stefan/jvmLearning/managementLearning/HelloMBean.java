package com.stefan.jvmLearning.managementLearning;

public interface HelloMBean {
    void sayHello();
    void sayHello(String name);
    String getName();
    void setName(String name);
    String getObjectName();
}
