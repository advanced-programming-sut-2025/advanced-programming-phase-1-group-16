package com.group16.stardewvalley.model.map;

import com.group16.stardewvalley.model.Items.*;
import com.group16.stardewvalley.model.Items.Item;
import com.group16.stardewvalley.model.agriculture.Crop;
import com.group16.stardewvalley.model.agriculture.PlantedSeed;
import com.group16.stardewvalley.model.agriculture.Tree;

public class Tile {
    private TileType type;
    private Item item;
    private Tree tree;
    private Crop crop;
    private boolean isPloughed;
    private boolean isWet;
    private boolean hasWater;  // بسته به نوع باید تعیین بشه نمیدونم مدیریتشو

    public Tile(TileType tileType) {
        this.type = tileType;
        this.isPloughed = false;
    }

    public void setHasWater(boolean b) {
        this.hasWater = b;
    }
    public boolean isPloughed() {
        return isPloughed;
    }

    public boolean isWet() {
        return isWet;
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

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public boolean isHasWater() {
        return hasWater;
    }
}