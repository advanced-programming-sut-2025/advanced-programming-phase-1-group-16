package com.group16.stardewvalley.model.crafting;

import com.group16.stardewvalley.model.items.Item;

import java.util.Map;

public class CraftItem extends Item {
    public CraftItem(String name) {
        super(name);
    }

    private Map<CraftingIngredientsTypes, Integer> ingredients;

    public CraftItem(String name, Map<CraftingIngredientsTypes, Integer> ingredients) {
        super(name);
        this.ingredients = ingredients;
    }



}
