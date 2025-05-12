package com.group16.stardewvalley.model.agriculture;

import com.group16.stardewvalley.model.items.Item;

public class Seed extends Item {
    private SeedType type;
    private String name;

    Seed(String name, SeedType type) {
        super(name);
        this.type = type;
    }

    public String getName() {
        return type.getName();
    }
}
