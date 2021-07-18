package com.stefan.designPattern.template;

public abstract class AbstractTemplate implements Template {
    @Override
    public void templateMethod() {
        System.out.println("templateMethod...start");
        step1();
        step2();
        step3();
        System.out.println("templateMethod...end");
    }

    protected abstract void step1();
    protected abstract void step2();
    protected abstract void step3();
}
