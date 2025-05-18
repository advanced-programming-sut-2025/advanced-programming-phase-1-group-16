package com.group16.stardewvalley.model.food;

import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.items.Item;

public class FoodRecipe extends Item {
    private final Food food;
    public FoodRecipe(String name, Food food) {
        super(name);
        this.food = food;
    }

    public Food getFood() {
        return food;
    }

    @Override
    public int getPrice() {
        return super.getPrice();
    }
}