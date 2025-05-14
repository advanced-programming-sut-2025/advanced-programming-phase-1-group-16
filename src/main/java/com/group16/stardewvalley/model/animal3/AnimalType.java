package com.group16.stardewvalley.model.animal3;

import java.util.List;


public enum AnimalType {
    //coop animals:
    HEN("hen",800, List.of(AnimalProductTypes.HEN_EGG, AnimalProductTypes.HEN_BIG_EGG), 1, true),
    DUCK("duck",1_200, List.of(AnimalProductTypes.DUCK_EGG, AnimalProductTypes.DUCK_FEATHER), 2, true),
    RABBIT("rabbit",8_000, List.of(AnimalProductTypes.RABBIT_LEG, AnimalProductTypes.RABBIT_WOOL), 4, true),
    DINOSAUR("dinosaur",1_400, List.of(AnimalProductTypes.DINOSAUR_EGG), 7, true),

    COW("cow",1_500, List.of(AnimalProductTypes.BIG_MILK, AnimalProductTypes.MILK), 1, false),
    GOAT("goat",4_000, List.of(AnimalProductTypes.BIG_GOAT_MILK, AnimalProductTypes.GOAT_MILK), 2, false),
    SHEEP("sheep",8_000, List.of(AnimalProductTypes.SHEEP_WOOL), 3, false),
    PIG("pig",16_000, List.of(AnimalProductTypes.TRUFFLE), 0, false);


    private final String name;
    private boolean hasCoop;
    private final Integer price;
    private final List<AnimalProductTypes> productList;
    private final Integer productPeriod; //in day



    AnimalType(String name,int price, List<AnimalProductTypes> productList, int productPeriod, boolean hasCoop) {
        this.name = name;
        this.price = price;
        this.productList = productList;
        this.productPeriod = productPeriod;
        this.hasCoop = hasCoop;
    }

}
