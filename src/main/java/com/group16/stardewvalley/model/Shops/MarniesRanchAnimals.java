package com.group16.stardewvalley.model.Shops;

public enum MarniesRanchAnimals {
    CHICKEN("chicken", model.animal.AnimalType.HEN,800, BuildingType.Coop,2),
    COW("cow", model.animal.AnimalType.COW,1500, BuildingType.Barn,2),
    GOAT("goat", model.animal.AnimalType.GOAT,4000, BuildingType.Big_Barn,2),
    DUCK("duck", model.animal.AnimalType.DUCK,1200, BuildingType.Big_Coop,2),
    SHEEP("sheep", model.animal.AnimalType.SHEEP,8000, BuildingType.Deluxe_Barn,2),
    RABBIT("rabbit", model.animal.AnimalType.RABBIT,8000, BuildingType.Deluxe_Coop,2),
    DINOSAUR("dinosaur", model.animal.AnimalType.DINOSAUR,14000, BuildingType.Big_Coop,2),
    PIG("pig", model.animal.AnimalType.PIG,16000, BuildingType.Deluxe_Barn,2);

    private final String name;
    private final model.animal.AnimalType animalType;
    private final Integer price;
    private final BuildingType buildingRequired;
    private final Integer dailyLimit;
    private Integer dailySold = 0;

    MarniesRanchAnimals(String name, model.animal.AnimalType animalType, Integer price, BuildingType buildingRequired, Integer dailyLimit) {
        this.name = name;
        this.animalType = animalType;
        this.price = price;
        this.buildingRequired = buildingRequired;
        this.dailyLimit = dailyLimit;
    }
}
