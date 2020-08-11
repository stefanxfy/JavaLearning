package com.stefan.designPattern.builder;

/**
 * version=v1，驾驶位前面只有一个FM屏幕，车灯是4个。
 * version=v2，驾驶位前面是一个可以看电视听歌的智能屏幕，车灯是6个。
 * type=0 家用型，engine发动机是普通发动机。
 * type=1 竞技型，engine发动机是高级发动机, version不管是v1还是v2，车灯都是6个。
 */
public class Test2 {
    public static void main(String[] args) {
        //创建一辆家用v1汽车,FM屏幕、4个车灯、普通发动机
        BmCar bmCarHouseV1 = new BmCar("AE86", 1000, "v1", 0);
        if ("v1".equals(bmCarHouseV1.getVersion())) {
            bmCarHouseV1.setScreen(new FmScreen());
            bmCarHouseV1.setLightSize(4);
        }
        if (0 == bmCarHouseV1.getType()) {
            bmCarHouseV1.setEngine(new NormalEngine());
        }

        //创建一辆竞技v1汽车，FM屏幕、6个车灯、高级发动机
        BmCar bmCarSportV1 = new BmCar("AE86", 1000, "v1", 0);
        if ("v1".equals(bmCarSportV1.getVersion())) {
            bmCarSportV1.setScreen(new FmScreen());
        }
        if (1 == bmCarSportV1.getType()) {
            bmCarSportV1.setEngine(new SuperEngine());
            bmCarSportV1.setLightSize(6);
        }
    }
}
