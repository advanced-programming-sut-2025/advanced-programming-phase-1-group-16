package com.group16.stardewvalley.model.crafting.artisan;

import com.group16.stardewvalley.model.items.Item;

public class ArtisanGood extends Item {
    ArtisanGoodType type;

    public ArtisanGood(String name, ArtisanGoodType type) {
        super(name);
        this.type = type;
    }

    public ArtisanGoodType getType() {
        return type;
    }
}
