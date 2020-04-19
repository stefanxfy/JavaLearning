package com.stefan.designPattern.builder;

public class BmCarDef {
    public static class Type {
        public static final int HOUSE = 0;
        public static final int SPORT = 1;
    }

    public static class ScreenType {
        public static final int FM = 0;
        public static final int INTELLECT = 1;
    }

    public static class EngineType {
        public static final int NORMAL = 0;
        public static final int SUPER = 1;
    }

    public static class Version {
        public static final String V1 = "v1";
        public static final String V2 = "v2";
        public static final int V1_LIGHT_SIZE = 4;
        public static final int V2_LIGHT_SIZE = 6;
    }
}
