// TileDTO.java
package com.group16.stardewvalley.model.DTO;

import com.group16.stardewvalley.model.map.TileType;

public class TileDTO {
    private TileType type;
    private boolean hasWater;
    private boolean isPlowed;
    private boolean isFertilized;
    private boolean isBurned;
    private String cropName;
    private int cropStage;
    private String treeType;
    private String itemName;

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public int getCropStage() {
        return cropStage;
    }

    public void setCropStage(int cropStage) {
        this.cropStage = cropStage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public boolean isHasWater() {
        return hasWater;
    }

    public void setHasWater(boolean hasWater) {
        this.hasWater = hasWater;
    }

    public boolean isPlowed() {
        return isPlowed;
    }

    public void setPlowed(boolean plowed) {
        isPlowed = plowed;
    }

    public boolean isFertilized() {
        return isFertilized;
    }

    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }

    public boolean isBurned() {
        return isBurned;
    }

    public void setBurned(boolean burned) {
        isBurned = burned;
    }
}
