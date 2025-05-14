package com.group16.stardewvalley.model.agriculture;


public class Crop {
    private CropType cropType;
    private int sellPrice;
    private int energy;
    private int stage;
    private final int finalStage;
    private int dayPastFromLastStage;
    private int daysSinceLastHarvest;
    private int daySinceLastWater;
    private int daysSincePlanting;
    private boolean isHarvested;
    private boolean isWatered;
    private boolean isMature;
    private boolean isFertilized;
    private boolean isColossal;

    public Crop(CropType cropType) {
        this.cropType = cropType;
        this.sellPrice = cropType.getBaseSellPrice();
        this.energy = cropType.getEnergy();
        this.finalStage = cropType.getStages().length;

        // متغیرهای زمان بازی
        this.stage = 0;
        this.dayPastFromLastStage = 0;
        this.daysSinceLastHarvest = 0;
        this.daySinceLastWater = 0;
        this.daysSincePlanting = 0;
        this.isWatered = false;
        this.isMature = false;
        this.isHarvested = false;
        this.isFertilized = false;
    }

    public boolean isHarvested() {
        return isHarvested;
    }

    public void setHarvested(boolean harvested) {
        isHarvested = harvested;
    }

    public int getDaySinceLastWater() {
        return daySinceLastWater;
    }

    public void setDaySinceLastWater(int daySinceLastWater) {
        this.daySinceLastWater = daySinceLastWater;
    }

    public void setDaysSincePlanting(int daysSincePlanting) {
        this.daysSincePlanting = daysSincePlanting;
    }

    public boolean isColossal() {
        return isColossal;
    }
    public boolean isFertilized() {
        return isFertilized;
    }

    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }

    public int getDaysSincePlanting() {
        return daysSincePlanting;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    public int getDaysSinceLastHarvest() {
        return daysSinceLastHarvest;
    }

    public void setDaysSinceLastHarvest(int daysSinceLastHarvest) {
        this.daysSinceLastHarvest = daysSinceLastHarvest;
    }

    public boolean isMature() {
        return isMature;
    }

    public void setMature(boolean mature) {
        isMature = mature;
    }

    public boolean isWatered() {
        return isWatered;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
    }

    public int getDayPastFromLastStage() {
        return dayPastFromLastStage;
    }

    public void setDayPastFromLastStage(int dayPastFromLastStage) {
        this.dayPastFromLastStage = dayPastFromLastStage;
    }

    public int getFinalStage() {
        return finalStage;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void advanceStage() {
        if (!isMature) {
            dayPastFromLastStage++;
            daysSincePlanting++;
            if (dayPastFromLastStage >= cropType.getStages()[stage]) {
                stage++;
                if (stage == finalStage) {
                    isMature = true;
                }
                dayPastFromLastStage = 0;
            }
        }

    }


}

