package com.group16.stardewvalley.model.Seeds;

import com.group16.stardewvalley.model.Items.Item;

public class Seed extends Item {
    private SeedType type;
    private String name;

    Seed(String name, SeedType type) {
        super(name);
        this.type = type;
    }
}
