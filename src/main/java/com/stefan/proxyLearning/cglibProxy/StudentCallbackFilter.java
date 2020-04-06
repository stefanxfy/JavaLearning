package com.stefan.proxyLearning.cglibProxy;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class StudentCallbackFilter implements CallbackFilter {
    /**
     * 返回的值为数字，代表了Callback数组中的索引位置，要到用的Callback
     */
    @Override
    public int accept(Method method) {
        if ("findTeacher".equals(method.getName())) {
            return 1;
        } else if ("giveMoney".equals(method.getName())) {
            return 0;
        } else if ("giveHomeWork".equals(method.getName())) {
            return 2;
        }
        return 0;
    }
}
