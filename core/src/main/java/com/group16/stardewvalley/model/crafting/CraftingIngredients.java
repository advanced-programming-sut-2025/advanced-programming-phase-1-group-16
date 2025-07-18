package com.group16.stardewvalley.model.crafting;

import com.group16.stardewvalley.model.items.Item;

public class CraftingIngredients extends Item {

    private CraftingIngredientsTypes ingredientType;

    public CraftingIngredients(String name, int price) {
        super(name, price);
    }

    public CraftingIngredientsTypes getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(CraftingIngredientsTypes ingredientType) {
        this.ingredientType = ingredientType;
    }
}
