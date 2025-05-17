package com.group16.stardewvalley.model.crafting;

import com.group16.stardewvalley.model.items.Item;

import java.util.Map;

public class CraftItem extends Item {
    public CraftItem(String name) {
        super(name);
    }

   private CraftingRecipes recipes;

    public CraftItem(String name, CraftingRecipes recipes) {
        super(name);
        this.recipes = recipes;
    }

    public CraftingRecipes getRecipes() {
        return recipes;
    }
}