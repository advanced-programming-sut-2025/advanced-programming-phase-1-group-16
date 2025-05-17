package com.group16.stardewvalley.model.crafting;

import com.group16.stardewvalley.model.items.Item;

import java.util.Map;

public class CraftItem extends Item {
    public CraftItem(String name) {
        super(name);
    }

    private CraftingRecipes recipe;

    public CraftItem(String name, CraftingRecipes recipe) {
        super(name);
        this.recipe = recipe;
    }
}