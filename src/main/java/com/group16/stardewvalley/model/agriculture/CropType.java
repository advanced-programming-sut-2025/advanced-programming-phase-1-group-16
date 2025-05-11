package com.group16.stardewvalley.model.agriculture;
import com.group16.stardewvalley.model.time.Season;

import java.util.List;

public enum CropType {
    BLUE_JAZZ("Blue Jazz", SeedType.JAZZ_SEEDS, new int[]{1, 2, 2, 2}, 7, true, -1, 50, true, 45, 20, List.of(Season.Spring), false),
    CARROT("Carrot", SeedType.CARROT_SEEDS, new int[]{1, 1, 1}, 3, true, -1, 35, true, 75, 33, List.of(Season.Spring), false),
    CAULIFLOWER("Cauliflower", SeedType.CAULIFLOWER_SEEDS, new int[]{1, 2, 4, 4, 1}, 12, true, -1, 175, true, 75, 33, List.of(Season.Spring), true),
    COFFEE_BEAN("Coffee Bean", SeedType.COFFEE_BEANS, new int[]{1, 2, 2, 3, 2}, 10, false, 2, 15, false, -1, -1, List.of(Season.Spring, Season.Summer), false),
    GARLIC("Garlic", SeedType.GARLIC_SEEDS, new int[]{1, 1, 1, 1}, 4, true, -1, 60, true, 20, 9, List.of(Season.Spring), false),
    GREEN_BEAN("Green Bean", SeedType.BEAN_STARTER, new int[]{1, 1, 1, 3, 4}, 10, false, 3, 40, true, 25, 11, List.of(Season.Spring), false),
    KALE("Kale", SeedType.KALE_SEEDS, new int[]{1, 2, 2, 1}, 6, true, -1, 110, true, 50, 22, List.of(Season.Spring), false),
    PARSNIP("Parsnip", SeedType.PARSNIP_SEEDS, new int[]{1, 1, 1, 1}, 4, true, -1, 35, true, 25, 11, List.of(Season.Spring), false),
    POTATO("Potato", SeedType.POTATO_SEEDS, new int[]{1, 1, 1, 2, 1}, 6, true, -1, 80, true, 25, 11, List.of(Season.Spring), false),
    RHUBARB("Rhubarb", SeedType.RHUBARB_SEEDS, new int[]{2, 2, 2, 3, 4}, 13, true, -1, 220, false, -1, -1, List.of(Season.Spring), false),
    STRAWBERRY("Strawberry", SeedType.STRAWBERRY_SEEDS, new int[]{1, 1, 2, 2, 2}, 8, false, 4, 120, true, 50, 22, List.of(Season.Spring), false),
    TULIP("Tulip", SeedType.TULIP_BULB, new int[]{1, 1, 2, 2}, 6, true, -1, 30, true, 45, 20, List.of(Season.Spring), false),
    UNMILLED_RICE("Unmilled Rice", SeedType.RICE, new int[]{1, 2, 2, 3}, 8, true, -1, 30, true, 3, 1, List.of(Season.Spring), false),
    BLUEBERRY("Blueberry", SeedType.BLUEBERRY_SEEDS, new int[]{1, 3, 3, 4, 2}, 13, false, 4, 50, true, 25, 11, List.of(Season.Summer), false),
    CORN("Corn", SeedType.CORN_SEEDS, new int[]{2, 3, 3, 3, 3}, 14, false, 4, 50, true, 25, 11, List.of(Season.Summer, Season.Fall), false),
    HOPS("Hops", SeedType.HOPS_STARTER, new int[]{1, 1, 2, 3, 4}, 11, false, 1, 25, true, 45, 20, List.of(Season.Summer), false),
    HOT_PEPPER("Hot Pepper", SeedType.PEPPER_SEEDS, new int[]{1, 1, 1, 1, 1}, 5, false, 3, 40, true, 13, 5, List.of(Season.Summer), false),
    MELON("Melon", SeedType.MELON_SEEDS, new int[]{1, 2, 3, 3, 3}, 12, true, -1, 250, true, 113, 50, List.of(Season.Summer), true),
    POPPY("Poppy", SeedType.POPPY_SEEDS, new int[]{1, 2, 2, 2}, 7, true, -1, 140, true, 45, 20, List.of(Season.Summer), false),
    RADISH("Radish", SeedType.RADISH_SEEDS, new int[]{2, 1, 2, 1}, 6, true, -1, 90, true, 45, 20, List.of(Season.Summer), false),
    RED_CABBAGE("Red Cabbage", SeedType.RED_CABBAGE_SEEDS, new int[]{2, 1, 2, 2, 2}, 9, true, -1, 260, true, 75, 33, List.of(Season.Summer), false),
    STARFRUIT("Starfruit", SeedType.STARFRUIT_SEEDS, new int[]{2, 3, 2, 3, 3}, 13, true, -1, 750, true, 125, 56, List.of(Season.Summer), false),
    SUMMER_SPANGLE("Summer Spangle", SeedType.SPANGLE_SEEDS, new int[]{1, 2, 3, 1}, 8, true, -1, 90, true, 45, 20, List.of(Season.Summer), false),
    SUMMER_SQUASH("Summer Squash", SeedType.SUMMER_SQUASH_SEEDS, new int[]{1, 1, 1, 2, 1}, 6, false, 3, 45, true, 63, 28, List.of(Season.Summer), false),
    SUNFLOWER("Sunflower", SeedType.SUNFLOWER_SEEDS, new int[]{1, 2, 3, 2}, 8, true, -1, 80, true, 45, 20, List.of(Season.Summer, Season.Fall), false),
    TOMATO("Tomato", SeedType.TOMATO_SEEDS, new int[]{2, 2, 2, 2, 3}, 11, false, 4, 60, true, 20, 9, List.of(Season.Summer), false),
    WHEAT("Wheat", SeedType.WHEAT_SEEDS, new int[]{1, 1, 1, 1}, 4, true, -1, 25, false, -1, -1, List.of(Season.Summer, Season.Fall), false),
    AMARANTH("Amaranth", SeedType.AMARANTH_SEEDS, new int[]{1, 2, 2, 2}, 7, true, -1, 150, true, 50, 22, List.of(Season.Fall), false),
    ARTICHOKE("Artichoke", SeedType.ARTICHOKE_SEEDS, new int[]{2, 2, 1, 2, 1}, 8, true, -1, 160, true, 30, 13, List.of(Season.Fall), false),
    BEET("Beet", SeedType.BEET_SEEDS, new int[]{1, 1, 2, 2}, 6, true, -1, 100, true, 30, 13, List.of(Season.Fall), false),
    BOK_CHOYS("Bok Choy", SeedType.BOK_CHOY_SEEDS, new int[]{1, 1, 1, 1}, 4, true, -1, 80, true, 25, 11, List.of(Season.Fall), false),
    BROCCOLI("Broccoli", SeedType.BROCCOLI_SEEDS, new int[]{2, 2, 2, 2}, 8, false, 4, 70, true, 63, 28, List.of(Season.Fall), false),
    CRANBERRIES("Cranberries", SeedType.CRANBERRY_SEEDS, new int[]{1, 2, 1, 1, 2}, 7, false, 5, 75, true, 38, 17, List.of(Season.Fall), false),
    EGGPLANT("Eggplant", SeedType.EGGPLANT_SEEDS, new int[]{1, 1, 1, 1}, 5, false, 5, 60, true, 20, 9, List.of(Season.Fall), false),
    FAIRY_ROSE("Fairy Rose", SeedType.FAIRY_SEEDS, new int[]{1, 4, 4, 3}, 12, true, -1, 290, true, 45, 20, List.of(Season.Fall), false),
    GRAPE("Grape", SeedType.GRAPE_STARTER, new int[]{1, 1, 2, 3, 3}, 10, false, 3, 80, true, 38, 17, List.of(Season.Fall), false),
    PUMPKIN("Pumpkin", SeedType.PUMPKIN_SEEDS, new int[]{1, 2, 3, 4, 3}, 13, true, -1, 320, false, -1, -1, List.of(Season.Fall), true);


    private final String name;
    private final SeedType source;
    private final int[] stages;
    private final int harvestTime;
    private final boolean oneTime;
    private final int regrowthTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final int baseHealth;
    private final List<Season> season;
    private final boolean canBecomeGiant;

    CropType(String name, SeedType source, int[] stages, int harvestTime, boolean oneTime, int regrowthTime,
         int baseSellPrice, boolean isEdible, int energy, int baseHealth, List<Season> season, boolean canBecomeGiant) {
        this.name = name;
        this.source = source;
        this.stages = stages;
        this.harvestTime = harvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.baseHealth = baseHealth;
        this.season = season;
        this.canBecomeGiant = canBecomeGiant;
    }

    public String getName() {
        return name;
    }

    public SeedType getSource() {
        return source;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    public int[] getStages() {
        return stages;
    }

    public int getHarvestTime() {
        return harvestTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public int getRegrowthTime() {
        return regrowthTime;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public List<Season> getSeason() {
        return season;
    }

    public boolean canBecomeGiant() {
        return canBecomeGiant;
    }
}
