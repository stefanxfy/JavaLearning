package com.stefan.designPattern.template;

public class TemplateB extends AbstractTemplate {
    @Override
    protected void step1() {
        System.out.println("TemplateB...step1");
    }

    @Override
    protected void step2() {
        System.out.println("TemplateB...step2");
    }

    @Override
    protected void step3() {
        System.out.println("TemplateB...step3");
    }
}
