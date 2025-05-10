package com.group16.stardewvalley.model.agriculture;

import com.group16.stardewvalley.model.time.Season;
import netscape.javascript.JSObject;

import java.util.HashMap;

public class Crop extends PlantedSeed {
    JSObject productType;
    private String name;
    private SeedType source;
    private HashMap<Integer, Integer> stageTime;
    private final int totalHarvestTime;
    private boolean isOneTime;
    private final int regrowthTime;
    private int sellPrice;
    private final boolean isEdible;
    private int energy;
    private final Season season;
    private final boolean canBecomeGiant;
    private int stage;
    private final int lastStage;
    private int dayPastFromLastStage;
    private int daysSinceLastHarvest;
    private boolean isWatered;
    private boolean isMature = false;

    public Crop(JSObject productType) {
        this.productType = productType;

        this.name = (String) productType.getMember("name");
        this.source = SeedType.valueOf(((String) productType.getMember("source")).toUpperCase().replace(" ", "_"));

        // مرحله‌ها
        this.stageTime = new HashMap<>();
        Object[] stages = (Object[]) productType.getMember("stages");
        for (int i = 0; i < stages.length; i++) {
            stageTime.put(i, ((Number) stages[i]).intValue());
        }

        this.totalHarvestTime = ((Number) productType.getMember("totalHarvestTime")).intValue();
        this.isOneTime = (Boolean) productType.getMember("oneTime");

        Object regrowthObj = productType.getMember("regrowthTime");
        this.regrowthTime = regrowthObj == null ? 0 : ((Number) regrowthObj).intValue();

        this.sellPrice = ((Number) productType.getMember("baseSellPrice")).intValue();
        this.isEdible = (Boolean) productType.getMember("isEdible");
        this.energy = ((Number) productType.getMember("energy")).intValue();

        // فصل – فقط اولین فصل را استفاده می‌کنیم
        Object[] seasons = (Object[]) productType.getMember("season");
        this.season = Season.valueOf(((String) seasons[0]).toUpperCase());

        this.canBecomeGiant = (Boolean) productType.getMember("canBecomeGiant");

        // متغیرهای زمان بازی
        this.stage = 0;
        this.dayPastFromLastStage = 0;
        this.daysSinceLastHarvest = 0;
        this.isWatered = false;
        this.lastStage = stageTime.size();
    }

    public int getLastStage() {
        return lastStage;
    }

    public boolean isMature() {
        return isMature;
    }

    public void setMature(boolean mature) {
        isMature = mature;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    public int getDaysSinceLastHarvest() {
        return daysSinceLastHarvest;
    }

    public void setDaysSinceLastHarvest(int daysSinceLastHarvest) {
        this.daysSinceLastHarvest = daysSinceLastHarvest;
    }

    public boolean isWatered() {
        return isWatered;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
    }

    public Season getSeason() {
        return season;
    }

    public int getDayPastFromLastStage() {
        return dayPastFromLastStage;
    }

    public void setDayPastFromLastStage(int dayPastFromLastStage) {
        this.dayPastFromLastStage = dayPastFromLastStage;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getRegrowthTime() {
        return regrowthTime;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public boolean isOneTime() {
        return isOneTime;
    }

    public void setOneTime(boolean oneTime) {
        isOneTime = oneTime;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public HashMap<Integer, Integer> getStageTime() {
        return stageTime;
    }

    public void setStageTime(HashMap<Integer, Integer> stageTime) {
        this.stageTime = stageTime;
    }

    public SeedType getSource() {
        return source;
    }

    public void setSource(SeedType source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSObject getProductType() {
        return productType;
    }

    public void setProductType(JSObject productType) {
        this.productType = productType;
    }

    public void advanceStage() {
        if (!isMature) {
            dayPastFromLastStage++;
            if (dayPastFromLastStage >= stageTime.get(stage)) {
                stage++;
                if (stage == lastStage) {
                    isMature = true;
                }
                dayPastFromLastStage = 0;
            }
        }

    }


}

