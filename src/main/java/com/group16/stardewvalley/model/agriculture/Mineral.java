package com.group16.stardewvalley.model.agriculture;

import com.group16.stardewvalley.model.Items.Item;

public class Mineral extends Item {
    private final MineralType type;
    public Mineral(String name, MineralType type) {
        super(name);
        this.type = type;
    }
    public MineralType getType() {
        return type;
    }
}
