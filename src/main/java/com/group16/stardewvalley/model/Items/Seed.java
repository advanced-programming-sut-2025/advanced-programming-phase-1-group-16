package com.group16.stardewvalley.model.Items;

import com.group16.stardewvalley.model.Seeds.SeedType;

public class Seed extends Item {
    private SeedType type;

    Seed(String name, SeedType type) {
        super(name);
        this.type = type;
    }
}
