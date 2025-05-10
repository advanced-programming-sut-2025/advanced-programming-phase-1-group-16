package com.group16.stardewvalley.model.map;

import com.group16.stardewvalley.model.Item;
import com.group16.stardewvalley.model.agriculture.Crop;

public class Tile {
    private TileType type;
    private Item item;
    Crop crop;

    public Tile(TileType tileType) {
        this.type = tileType;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Crop getPlantedSeed() {
        return crop;
    }

    public void setPlantedSeed(Crop crop) {
        this.crop = crop;
    }

    public Item getItem() {
        return item;
    }

    public void setItems(Item item) {
        this.item = item;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }
}
