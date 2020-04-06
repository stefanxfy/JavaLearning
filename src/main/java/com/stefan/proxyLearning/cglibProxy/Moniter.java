package com.stefan.proxyLearning.cglibProxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Moniter implements MethodInterceptor {

    /**
     *
     * @param o   目标类的实例
     * @param method  目标方法的反射对象
     * @param objects  方法的参数
     * @param methodProxy  代理类的实例
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("该同学平时表现良好");
        methodProxy.invokeSuper(o, objects);
        return null;
    }
}
