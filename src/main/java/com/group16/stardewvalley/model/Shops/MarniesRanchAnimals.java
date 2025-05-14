package com.group16.stardewvalley.model.Shops;

import com.group16.stardewvalley.model.animal.AnimalType;


import java.util.ArrayList;
import java.util.List;



public enum MarniesRanchAnimals {
    CHICKEN("chicken", AnimalType.HEN,800, List.of(BuildingType.Coop, BuildingType.Big_Coop, BuildingType.Deluxe_Coop),2),
    COW("cow", AnimalType.COW,1500,List.of(BuildingType.Barn, BuildingType.Big_Barn, BuildingType.Deluxe_Barn),2),
    GOAT("goat", AnimalType.GOAT,4000, List.of(BuildingType.Big_Barn, BuildingType.Deluxe_Barn) ,2),
    DUCK("duck", AnimalType.DUCK,1200,List.of(BuildingType.Big_Coop, BuildingType.Deluxe_Coop),2),
    SHEEP("sheep", AnimalType.SHEEP,8000, List.of(BuildingType.Deluxe_Barn),2),
    RABBIT("rabbit", AnimalType.RABBIT,8000, List.of(BuildingType.Deluxe_Coop),2),
    DINOSAUR("dinosaur", AnimalType.DINOSAUR,14000, List.of(BuildingType.Big_Coop, BuildingType.Deluxe_Coop),2),
    PIG("pig", AnimalType.PIG,16000, List.of(BuildingType.Deluxe_Barn),2);

    private final String name;
    private final AnimalType animalType;
    private final Integer price;
    private final List<BuildingType> buildingRequired;
    private final Integer dailyLimit;
    private Integer dailySold = 0;

    MarniesRanchAnimals(String name, AnimalType animalType, Integer price, List<BuildingType> buildingRequired, Integer dailyLimit  ) {
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

    public List<BuildingType> getBuildingRequired() {
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

    public static MarniesRanchAnimals sellAnimalFromName(String animal) {
        for (MarniesRanchAnimals type : MarniesRanchAnimals.values()) {
            if (type.name.equalsIgnoreCase(animal)) {
                return type;
            }
        }
        return null;
    }


}
