package com.group16.stardewvalley.model.map;

import com.group16.stardewvalley.model.Item;
import com.group16.stardewvalley.model.agriculture.Crop;

public class Tile {
    private TileType type;
    private Item item;
    private Crop crop;
    private boolean isPloughed;

    public Tile(TileType tileType) {
        this.type = tileType;
        this.isPloughed = false;
    }

    public boolean isPloughed() {
        return isPloughed;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public void setPloughed(boolean ploughed) {
        isPloughed = ploughed;
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
