package com.stefan.proxyLearning.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class StarProxy implements InvocationHandler {

    
	public StarProxy(Object target) {
		this.target = target;
	}

	//被代理对象
    private Object target;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("推广，包装");
		method.invoke(target, args);
		return null;
	}

	//创建真正的代理对象
	public  Object create() {
		return  Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),  this);
	}

}
