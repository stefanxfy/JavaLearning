package com.stefan.designPattern.builder;

public class Engine {
    private int type;

    protected Engine(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
