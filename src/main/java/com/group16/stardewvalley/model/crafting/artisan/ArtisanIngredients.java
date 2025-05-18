package com.group16.stardewvalley.model.crafting.artisan;

import com.group16.stardewvalley.model.items.Item;

public class ArtisanIngredients extends Item {

    private ArtisanIngredientType type;

    public ArtisanIngredients(String name, ArtisanIngredientType type) {
        super(name);
        this.type = type;
    }

    public ArtisanIngredientType getType() {
        return type;
    }
}
