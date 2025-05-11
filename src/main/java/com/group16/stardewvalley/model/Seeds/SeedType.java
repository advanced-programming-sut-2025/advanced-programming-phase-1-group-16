package com.group16.stardewvalley.model.Seeds;
import com.group16.stardewvalley.model.time.Season;

import java.util.List;

public enum SeedType {
    // Permanent Stock
    JOJA_COLA("Joja Cola",
            75,
            Integer.MAX_VALUE,
            List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    ANCIENT_SEED("Ancient Seed",
            500,
            1,
            List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    GRASS_STARTER("Grass Starter",
            125,
            Integer.MAX_VALUE,
            List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    SUGAR("Sugar",
            125,
            Integer.MAX_VALUE,
            List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    WHEAT_FLOUR("Wheat Flour",
            125,
            Integer.MAX_VALUE,
            List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    RICE("Rice",
            250,
            Integer.MAX_VALUE,
            List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),

    // Spring Stock
    PARSNIP_SEEDS("Parsnip Seeds", 25, 5, List.of(Season.Spring)),
    BEAN_STARTER("Bean Starter", 75, 5, List.of(Season.Spring)),
    CAULIFLOWER_SEEDS("Cauliflower Seeds", 100, 5, List.of(Season.Spring)),
    POTATO_SEEDS("Potato Seeds", 62, 5, List.of(Season.Spring)),
    STRAWBERRY_SEEDS("Strawberry Seeds", 100, 5, List.of(Season.Spring)),
    TULIP_BULB("Tulip Bulb", 25, 5, List.of(Season.Spring)),
    KALE_SEEDS("Kale Seeds", 87, 5, List.of(Season.Spring)),
    COFFEE_BEANS("Coffee Beans", 200, 1, List.of(Season.Spring, Season.Summer)),
    CARROT_SEEDS("Carrot Seeds", 5, 10, List.of(Season.Spring)),
    RHUBARB_SEEDS("Rhubarb Seeds", 100, 5, List.of(Season.Spring)),
    JAZZ_SEEDS("Jazz Seeds", 37, 5, List.of(Season.Spring)),

    // Summer Stock
    TOMATO_SEEDS("Tomato Seeds", 62, 5, List.of(Season.Summer)),
    PEPPER_SEEDS("Pepper Seeds", 50, 5, List.of(Season.Spring)),
    WHEAT_SEEDS("Wheat Seeds", 12, 10, List.of(Season.Spring, Season.Fall)),
    SUMMER_SQUASH_SEEDS("Summer Squash Seeds", 10, 10, List.of(Season.Summer)),
    RADISH_SEEDS("Radish Seeds", 50, 5, List.of(Season.Spring)),
    MELON_SEEDS("Melon Seeds", 100, 5, List.of(Season.Spring)),
    HOPS_STARTER("Hops Starter", 75, 5, List.of(Season.Spring)),
    POPPY_SEEDS("Poppy Seeds", 125, 5, List.of(Season.Spring)),
    SPANGLE_SEEDS("Spangle Seeds", 62, 5, List.of(Season.Spring)),
    STARFRUIT_SEEDS("Starfruit Seeds", 400, 5, List.of(Season.Spring)),
    SUNFLOWER_SEEDS_SUMMER("Sunflower Seeds", 125, 5, List.of(Season.Spring, Season.Fall)),

    // Fall Stock
    CORN_SEEDS("Corn Seeds", 187, 5, List.of(Season.Fall)),
    EGGPLANT_SEEDS("Eggplant Seeds", 25, 5, List.of(Season.Fall)),
    PUMPKIN_SEEDS("Pumpkin Seeds", 125, 5, List.of(Season.Fall)),
    BROCCOLI_SEEDS("Broccoli Seeds", 15, 5, List.of(Season.Fall)),
    AMARANTH_SEEDS("Amaranth Seeds", 87, 5, List.of(Season.Fall)),
    GRAPE_STARTER("Grape Starter", 75, 5, List.of(Season.Fall)),
    BEET_SEEDS("Beet Seeds", 20, 5, List.of(Season.Fall)),
    YAM_SEEDS("Yam Seeds", 75, 5, List.of(Season.Fall)),
    BOK_CHOY_SEEDS("Bok Choy Seeds", 62, 5, List.of(Season.Fall)),
    CRANBERRY_SEEDS("Cranberry Seeds", 300, 5, List.of(Season.Fall)),
    FAIRY_SEEDS("Fairy Seeds", 250, 5, List.of(Season.Fall)),
    RARE_SEED("Rare Seed", 1000, 1, List.of(Season.Fall)),

    // Winter Stock
    POWDERMELON_SEEDS("Powdermelon Seeds", 20, 10,List.of(Season.Winter));

    private final String name;
    private final int price;
    private final int dailyLimit;
    private final List<Season> availableSeasons;

    SeedType(String name, int price, int dailyLimit, List<Season> availableSeasons) {
        this.name = name;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.availableSeasons = availableSeasons;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public List<Season> getAvailableSeasons() {
        return availableSeasons;
    }
}