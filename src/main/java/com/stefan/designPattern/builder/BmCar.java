package com.stefan.designPattern.builder;

import java.util.List;

/**
 * version=v1，驾驶位前面只有一个FM屏幕。车灯是4个
 * version=v2，驾驶位前面是一个可以看电视听歌的智能屏幕。车灯是6个
 * type=0 家用型，engine发动机是普通发动机
 * type=1 竞技型，engine发动机是高级发动机, version不管是v1还是v2，车灯都是6个
 */
public class BmCar {
    private String name;
    private int price;
    private String version;
    private int type; //适用类型，0家用型、1竞技型
    private Engine engine;
    private Screen screen;
    private int lightSize;

    private BmCar(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.version = builder.version;
        this.type = builder.type;
        this.engine = builder.engine;
        this.screen = builder.screen;
        this.lightSize = builder.lightSize;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getVersion() {
        return version;
    }

    public int getType() {
        return type;
    }

    public Engine getEngine() {
        return engine;
    }

    public Screen getScreen() {
        return screen;
    }

    public int getLightSize() {
        return lightSize;
    }

    @Override
    public String toString() {
        return "BmCar{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", version='" + version + '\'' +
                ", type=" + type +
                ", engine=" + engine +
                ", screen=" + screen +
                ", lightSize=" + lightSize +
                '}';
    }

    public abstract static class Builder {
        protected String name;
        protected int price;
        protected String version;
        protected int type; //适用类型，0家用型、1竞技型
        protected Engine engine;
        protected Screen screen;
        protected int lightSize;
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }
        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }
        public Builder setEngine(Engine engine) {
            this.engine = engine;
            return this;
        }

        public Builder setScreen(Screen screen) {
            this.screen = screen;
            return this;
        }

        public Builder setLightSize(int lightSize) {
            this.lightSize = lightSize;
            return this;
        }

        public abstract BmCar build();
    }

    public static class BmSportCarBuilder extends Builder {
        public BmSportCarBuilder() {
            this.type = BmCarDef.Type.SPORT;
        }

        @Override
        public BmCar build() {
            if ("v1".equals(this.version)) {

                if (BmCarDef.ScreenType.FM != this.screen.getType()) {
                    throw new RuntimeException("竞技型宝马的显示屏不匹配");
                }
            } else if ("v2".equals(this.version)) {
                if (BmCarDef.ScreenType.INTELLECT != this.screen.getType()) {
                    throw new RuntimeException("竞技型宝马的显示屏不匹配");
                }
            }
            if (this.lightSize != BmCarDef.Version.V2_LIGHT_SIZE) {
                throw new RuntimeException("竞技型宝马的灯的数量不匹配");
            }
            if (BmCarDef.EngineType.SUPER != this.engine.getType()) {
                throw new RuntimeException("竞技型宝马的发动机不匹配");
            }
            BmCar bmCar = new BmCar(this);
            return bmCar;
        }
    }

    public static class BmHouseCarBuilder extends Builder {
        public BmHouseCarBuilder() {
            this.type = BmCarDef.Type.HOUSE;
        }

        @Override
        public BmCar build() {
            if (BmCarDef.Version.V1.equals(this.version)) {
                if (this.lightSize != BmCarDef.Version.V1_LIGHT_SIZE) {
                    throw new RuntimeException("家用型宝马的灯的数量不匹配");
                }
                if (BmCarDef.ScreenType.FM != this.screen.getType()) {
                    throw new RuntimeException("家用型宝马的显示屏不匹配");
                }
            } else if (BmCarDef.Version.V2.equals(this.version)) {
                if (this.lightSize != BmCarDef.Version.V2_LIGHT_SIZE) {
                    throw new RuntimeException("家用型宝马的灯的数量不匹配");
                }
                if (BmCarDef.ScreenType.INTELLECT != this.screen.getType()) {
                    throw new RuntimeException("家用型宝马的显示屏不匹配");
                }
            }
            if (BmCarDef.EngineType.NORMAL != this.engine.getType()) {
                throw new RuntimeException("家用型宝马的发动机不匹配");
            }
            BmCar bmCar = new BmCar(this);
            return bmCar;
        }
    }


}
