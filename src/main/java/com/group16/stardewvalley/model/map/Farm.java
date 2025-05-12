package com.group16.stardewvalley.model.map;


import com.group16.stardewvalley.model.food.FoodIngredient;

import java.util.HashMap;

public class Farm {
    private FarmType type;
    private Pos startPosition;
    private HashMap<FoodIngredient, Integer> refrigerator;

    public Farm(FarmType type) {
        this.type = type;
        this.refrigerator = new HashMap<>();
    }

    public HashMap<FoodIngredient, Integer> getRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(HashMap<FoodIngredient, Integer> refrigerator) {
        this.refrigerator = refrigerator;
    }

    public void addToRefrigerator(FoodIngredient ingredient, int amount) {
        refrigerator.put(ingredient, amount);
    }

    public FarmType getType() {
        return type;
    }

    public void setType(FarmType type) {
        this.type = type;
    }

    public Pos getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Pos startPosition) {
        this.startPosition = startPosition;
    }
}
