package com.group16.stardewvalley.model.agriculture;

import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.time.Season;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Tree extends PlantedSeed {
    private final String name;
    private final SeedType source;
    private HashMap<Integer, Integer> stages;
    private int totalHarvestTime;
    private int harvestTime;
    private final Ingredient fruit;
    private final int fruitHarvestCycle;
    private final int fruitBaseSellPrice;
    private final boolean isFruitEdible;
    private final int fruitEnergy;
    private final int fruitBaseHealth;
    private final Season season;
    private JSObject type;

    public Tree(JSObject type) {
        this.type = type;
        this.name = type.getMember("name").toString();
        this.source = SeedType.valueOf(
                type.getMember("source").toString().toUpperCase().replace(" ", "_")
        );

        // مراحل رشد
        ArrayList<Object> stageList = (ArrayList<Object>) type.getMember("stages");
        for (int i = 0; i < stageList.size(); i++) {
            int days = Integer.parseInt(stageList.get(i).toString());
            stages.put(i, days);
        }

        this.totalHarvestTime = Integer.parseInt(type.getMember("totalHarvestTime").toString());

        // میوه
        String fruitName = type.getMember("fruit").toString();
        this.fruit = Ingredient.valueOf(fruitName.toUpperCase().replace(" ", "_"));

        this.fruitHarvestCycle = Integer.parseInt(type.getMember("fruitHarvestCycle").toString());
        this.fruitBaseSellPrice = Integer.parseInt(type.getMember("fruitBaseSellPrice").toString());

        // قابل خوردن بودن میوه
        this.isFruitEdible = Boolean.parseBoolean(type.getMember("isFruitEdible").toString());

        // انرژی میوه
        String fruitEnergyStr = type.getMember("fruitEnergy").toString();
        if (fruitEnergyStr.equals("-")) {
            this.fruitEnergy = 0;
        } else {
            this.fruitEnergy = Integer.parseInt(fruitEnergyStr);
        }

        // سلامت میوه - اگر لازم نیست مقدار بده، بذار صفر بمونه
        this.fruitBaseHealth = 0; // یا می‌تونی از JSON بگیری اگر اضافه کردی

        // فصل
        ArrayList<Object> seasonList = (ArrayList<Object>) type.getMember("season");
        this.season = Season.valueOf(seasonList.get(0).toString().toUpperCase());

        // برداشت اصلی که می‌شه اولی
        this.harvestTime = stages.getOrDefault(0, 0);
    }

    public String getName() {
        return name;
    }

    public SeedType getSource() {
        return source;
    }

    public HashMap<Integer, Integer> getStages() {
        return stages;
    }

    public void setStages(HashMap<Integer, Integer> stages) {
        this.stages = stages;
    }

    public int getFruitBaseSellPrice() {
        return fruitBaseSellPrice;
    }

    public JSObject getType() {
        return type;
    }

    public void setType(JSObject type) {
        this.type = type;
    }

    public Season getSeason() {
        return season;
    }

    public int getFruitBaseHealth() {
        return fruitBaseHealth;
    }

    public boolean isFruitEdible() {
        return isFruitEdible;
    }

    public int getFruitEnergy() {
        return fruitEnergy;
    }

    public int getFruitHarvestCycle() {
        return fruitHarvestCycle;
    }

    public Ingredient getFruit() {
        return fruit;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public void setTotalHarvestTime(int totalHarvestTime) {
        this.totalHarvestTime = totalHarvestTime;
    }

    public int getHarvestTime() {
        return harvestTime;
    }

    public void setHarvestTime(int harvestTime) {
        this.harvestTime = harvestTime;
    }
}
