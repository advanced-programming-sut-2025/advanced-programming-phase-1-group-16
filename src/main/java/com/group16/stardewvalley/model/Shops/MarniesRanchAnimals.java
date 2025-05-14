package com.group16.stardewvalley.model.Shops;

import com.group16.stardewvalley.model.animal.AnimalType;


public enum MarniesRanchAnimals {
    CHICKEN("chicken", AnimalType.HEN,800, BuildingType.Coop,2),
    COW("cow", AnimalType.COW,1500, BuildingType.Barn,2),
    GOAT("goat", AnimalType.GOAT,4000, BuildingType.Big_Barn,2),
    DUCK("duck", AnimalType.DUCK,1200, BuildingType.Big_Coop,2),
    SHEEP("sheep", AnimalType.SHEEP,8000, BuildingType.Deluxe_Barn,2),
    RABBIT("rabbit", AnimalType.RABBIT,8000, BuildingType.Deluxe_Coop,2),
    DINOSAUR("dinosaur", AnimalType.DINOSAUR,14000, BuildingType.Big_Coop,2),
    PIG("pig", AnimalType.PIG,16000, BuildingType.Deluxe_Barn,2);

    private final String name;
    private final AnimalType animalType;
    private final Integer price;
    private final BuildingType buildingRequired;
    private final Integer dailyLimit;
    private Integer dailySold = 0;

    MarniesRanchAnimals(String name, AnimalType animalType, Integer price, BuildingType buildingRequired, Integer dailyLimit) {
        this.name = name;
        this.animalType = animalType;
        this.price = price;
        this.buildingRequired = buildingRequired;
        this.dailyLimit = dailyLimit;
    }

    public String getName() {
        return name;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public Integer getPrice() {
        return price;
    }

    public BuildingType getBuildingRequired() {
        return buildingRequired;
    }

    public Integer getDailyLimit() {
        return dailyLimit;
    }

    public Integer getDailySold() {
        return dailySold;
    }

    public void setDailySold(Integer dailySold) {
        this.dailySold = dailySold;
    }
}
