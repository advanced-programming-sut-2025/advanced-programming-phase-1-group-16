package com.group16.stardewvalley.model.map;

import com.group16.stardewvalley.model.Item;

import java.util.ArrayList;

public class Tile {
    private TileType type;
    private ArrayList<Item> items;

    public Tile(TileType tileType) {
        this.type = tileType;
        this.items = new ArrayList<>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }
}
