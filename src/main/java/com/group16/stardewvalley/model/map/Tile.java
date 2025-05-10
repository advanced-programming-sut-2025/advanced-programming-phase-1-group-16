package com.group16.stardewvalley.model.map;

import com.group16.stardewvalley.model.Things.Item;

public class Tile {
    private TileType type;
    private Item item;

    public Tile(TileType type) {
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }
}
