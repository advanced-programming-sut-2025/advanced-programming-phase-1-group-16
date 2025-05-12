package com.group16.stardewvalley.model.Items;

public class Seed extends Item {
    private SeedType type;

    Seed(String name, SeedType type) {
        super(name);
        this.type = type;
    }

    public SeedType getSeedType() {
        return type;
    }
}