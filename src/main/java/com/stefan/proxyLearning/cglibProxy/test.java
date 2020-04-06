package com.stefan.proxyLearning.cglibProxy;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class test {
    public static void main(String[] args) {
        //生成代理类的class路径，必须放在enhancer创建之前
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "src/com/stefan/proxyLearning/cglibProxy");
        //1新建一个字节增强器
        Enhancer enhancer = new Enhancer();
        //2将被代理类设置成父类
        enhancer.setSuperclass(Student.class);
        //3设置拦截器
        enhancer.setCallback(new Moniter());
        //4生成真正的动态代理对象
        Student studentProxy = (Student) enhancer.create();
        studentProxy.giveMoney();

    }
}
