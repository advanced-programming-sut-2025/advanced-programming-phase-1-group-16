package com.group16.stardewvalley.model.agriculture;

import com.group16.stardewvalley.model.time.Season;

public enum TreeType {
    APRICOT("Apricot Tree", SeedType.APRICOT_SAPLING, new int[]{7, 7, 7, 7}, 28,
            "Apricot", 1, 59, true, 38, 17, Season.Spring),

    CHERRY("Cherry Tree", SeedType.CHERRY_SAPLING, new int[]{7, 7, 7, 7}, 28,
            "Cherry", 1, 80, true, 38, 17, Season.Spring),

    BANANA("Banana Tree", SeedType.BANANA_SAPLING, new int[]{7, 7, 7, 7}, 28,
            "Banana", 1, 150, true, 75, 33, Season.Summer),

    MANGO("Mango Tree", SeedType.MANGO_SAPLING, new int[]{7, 7, 7, 7}, 28,
            "Mango", 1, 130, true, 100, 45, Season.Summer),

    ORANGE("Orange Tree", SeedType.ORANGE_SAPLING, new int[]{7, 7, 7, 7}, 28,
            "Orange", 1, 100, true, 38, 17, Season.Summer),

    PEACH("Peach Tree", SeedType.PEACH_SAPLING, new int[]{7, 7, 7, 7}, 28,
            "Peach", 1, 140, true, 38, 17, Season.Summer),

    APPLE("Apple Tree", SeedType.APPLE_SAPLING, new int[]{7, 7, 7, 7}, 28,
            "Apple", 1, 100, true, 38, 17, Season.Fall),

    POMEGRANATE("Pomegranate Tree", SeedType.POMEGRANATE_SAPLING, new int[]{7, 7, 7, 7}, 28,
            "Pomegranate", 1, 140, true, 38, 17, Season.Fall),

    OAK("Oak Tree", SeedType.ACORNS, new int[]{7, 7, 7, 7}, 28,
            "Oak Resin", 7, 150, false, -1, -1, Season.Special),

    MAPLE("Maple Tree", SeedType.MAPLE_SEEDS, new int[]{7, 7, 7, 7}, 28,
            "Maple Syrup", 9, 200, false, -1, -1, Season.Special),

    PINE("Pine Tree", SeedType.PINE_CONES, new int[]{7, 7, 7, 7}, 28,
            "Pine Tar", 5, 100, false, -1, -1, Season.Special),

    MAHOGANY("Mahogany Tree", SeedType.MAHOGANY_SEEDS, new int[]{7, 7, 7, 7}, 28,
            "Sap", 1, 2, true, -2, 0, Season.Special),

    MUSHROOM("Mushroom Tree", SeedType.MUSHROOM_TREE_SEEDS, new int[]{7, 7, 7, 7}, 28,
            "Common Mushroom", 1, 40, true, 38, 17, Season.Special),

    MYSTIC("Mystic Tree", SeedType.MYSTIC_TREE_SEEDS, new int[]{7, 7, 7, 7}, 28,
            "Mystic Syrup", 7, 1000, true, 500, 225, Season.Special);

    private final String name;
    private final SeedType seed;
    private final int[] growthStages;
    private final int totalGrowthTime;
    private final String fruitName;
    private final int fruitCycleDays;
    private final int fruitSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final int health;
    private final Season season;

    TreeType(String name, SeedType seed, int[] growthStages, int totalGrowthTime,
             String fruitName, int fruitCycleDays, int fruitSellPrice,
             boolean isEdible, int energy, int health, Season season) {
        this.name = name;
        this.seed = seed;
        this.growthStages = growthStages;
        this.totalGrowthTime = totalGrowthTime;
        this.fruitName = fruitName;
        this.fruitCycleDays = fruitCycleDays;
        this.fruitSellPrice = fruitSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.health = health;
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public Season getSeason() {
        return season;
    }

    public int getHealth() {
        return health;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getFruitSellPrice() {
        return fruitSellPrice;
    }

    public int getFruitCycleDays() {
        return fruitCycleDays;
    }

    public String getFruitName() {
        return fruitName;
    }

    public int getTotalGrowthTime() {
        return totalGrowthTime;
    }

    public SeedType getSeed() {
        return seed;
    }

    public int[] getGrowthStages() {
        return growthStages;
    }
}

