package com.group16.stardewvalley.model.Foods;

import com.group16.stardewvalley.model.items.Item;

public class FoodIngredient extends Item {
    private Ingredient type;

    public FoodIngredient(String name , Ingredient type) {
        super(name);
        this.type = type;
    }
    public Ingredient getType() {
        return type;
    }
    public void setType(Ingredient type) {
        this.type = type;
    }
}