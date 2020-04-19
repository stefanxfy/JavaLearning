package com.stefan.designPattern.builder;

public class Test {
    public static void main(String[] args) {

        BmCar.Builder sportCarBuilder = new BmCar.BmSportCarBuilder();
        BmCar sportCar = sportCarBuilder
                .setName("宝马v1跑车")
                .setVersion(BmCarDef.Version.V1)
                .setPrice(10000)
                .setEngine(new SuperEngine())
                .setScreen(new FmScreen())
                .setLightSize(BmCarDef.Version.V2_LIGHT_SIZE)
                .build();

        BmCar.Builder houseCarBuilder = new BmCar.BmHouseCarBuilder();
        BmCar houseCar = houseCarBuilder
                .setName("宝马v2家用汽车")
                .setVersion(BmCarDef.Version.V2)
                .setPrice(6000)
                .setEngine(new NormalEngine())
                .setScreen(new IntellectScreen())
                .setLightSize(BmCarDef.Version.V2_LIGHT_SIZE)
                .build();
        System.out.println(sportCar);
        System.out.println("-----------------------------------------------------");
        System.out.println(houseCar);


    }
}
