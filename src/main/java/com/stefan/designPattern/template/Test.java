package com.stefan.designPattern.template;

public class Test {
    public static void main(String[] args) {
        Template templateA = new TemplateA();
        templateA.templateMethod();
        System.out.println("------------------------------");
        Template templateB = new TemplateB();
        templateB.templateMethod();
    }
}
