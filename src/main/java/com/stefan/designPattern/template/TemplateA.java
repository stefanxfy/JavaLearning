package com.stefan.designPattern.template;

public class TemplateA extends AbstractTemplate {
    @Override
    protected void step1() {
        System.out.println("TemplateA...step1");
    }

    @Override
    protected void step2() {
        System.out.println("TemplateA...step2");
    }

    @Override
    protected void step3() {
        System.out.println("TemplateA...step3");
    }
}
