package com.group16.stardewvalley.model.agriculture;

public class Tree extends PlantedSeed {
    private final TreeType treeType;
    private final int totalHarvestTime;
    private int fruitSellPrice;
    private int dayPastFromLastHarvest;
    private int dayPastFromLastStage;
    private int stage;
    private boolean isMature;
    private boolean isBurned;


    public Tree(TreeType type) {
        this.treeType = type;
        this.totalHarvestTime = type.getTotalGrowthTime();
        this.fruitSellPrice = type.getFruitSellPrice();
        this.dayPastFromLastHarvest = 0;
        this.dayPastFromLastStage = 0;
        this.stage = 0;
        this.isMature = false;
        this.isBurned = false;

    }

    public TreeType getTreeType() {
        return treeType;
    }

    public boolean isBurned() {
        return isBurned;
    }

    public void setBurned(boolean burned) {
        isBurned = burned;
    }

    public boolean isMature() {
        return isMature;
    }

    public void setMature(boolean mature) {
        isMature = mature;
    }

    public int getDayPastFromLastHarvest() {
        return dayPastFromLastHarvest;
    }

    public void setDayPastFromLastHarvest(int dayPastFromLastHarvest) {
        this.dayPastFromLastHarvest = dayPastFromLastHarvest;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public int getFruitSellPrice() {
        return fruitSellPrice;
    }

    public void setFruitSellPrice(int fruitSellPrice) {
        this.fruitSellPrice = fruitSellPrice;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void advanceStage() {
        if (!isMature) {
            dayPastFromLastStage++;
            if (dayPastFromLastStage >= 7) {
                stage++;
                if (stage == 4) {
                    isMature = true;
                }
                dayPastFromLastStage = 0;
            }
        }
    }
}
