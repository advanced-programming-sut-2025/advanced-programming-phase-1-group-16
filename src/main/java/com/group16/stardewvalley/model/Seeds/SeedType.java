package com.group16.stardewvalley.model.Seeds;
import com.group16.stardewvalley.model.time.Season;

import java.util.Arrays;
import java.util.List;

public enum SeedType {
    // Permanent Stock
    JOJA_COLA("Joja Cola", 75, Integer.MAX_VALUE),
    ANCIENT_SEED("Ancient Seed", 500, 1),
    GRASS_STARTER("Grass Starter", 125, Integer.MAX_VALUE),
    SUGAR("Sugar", 125, Integer.MAX_VALUE),
    WHEAT_FLOUR("Wheat Flour", 125, Integer.MAX_VALUE),
    RICE("Rice", 250, Integer.MAX_VALUE),

    // Spring Stock
    PARSNIP_SEEDS("Parsnip Seeds", 25, 5),
    BEAN_STARTER("Bean Starter", 75, 5),
    CAULIFLOWER_SEEDS("Cauliflower Seeds", 100, 5),
    POTATO_SEEDS("Potato Seeds", 62, 5),
    STRAWBERRY_SEEDS("Strawberry Seeds", 100, 5),
    TULIP_BULB("Tulip Bulb", 25, 5),
    KALE_SEEDS("Kale Seeds", 87, 5),
    COFFEE_BEANS_SPRING("Coffee Beans", 200, 1),
    CARROT_SEEDS("Carrot Seeds", 5, 10),
    RHUBARB_SEEDS("Rhubarb Seeds", 100, 5),
    JAZZ_SEEDS("Jazz Seeds", 37, 5),

    // Summer Stock
    TOMATO_SEEDS("Tomato Seeds", 62, 5),
    PEPPER_SEEDS("Pepper Seeds", 50, 5),
    WHEAT_SEEDS_SUMMER("Wheat Seeds", 12, 10),
    SUMMER_SQUASH_SEEDS("Summer Squash Seeds", 10, 10),
    RADISH_SEEDS("Radish Seeds", 50, 5),
    MELON_SEEDS("Melon Seeds", 100, 5),
    HOPS_STARTER("Hops Starter", 75, 5),
    POPPY_SEEDS("Poppy Seeds", 125, 5),
    SPANGLE_SEEDS("Spangle Seeds", 62, 5),
    STARFRUIT_SEEDS("Starfruit Seeds", 400, 5),
    COFFEE_BEANS_SUMMER("Coffee Beans", 200, 1),
    SUNFLOWER_SEEDS_SUMMER("Sunflower Seeds", 125, 5),

    // Fall Stock
    CORN_SEEDS("Corn Seeds", 187, 5),
    EGGPLANT_SEEDS("Eggplant Seeds", 25, 5),
    PUMPKIN_SEEDS("Pumpkin Seeds", 125, 5),
    BROCCOLI_SEEDS("Broccoli Seeds", 15, 5),
    AMARANTH_SEEDS("Amaranth Seeds", 87, 5),
    GRAPE_STARTER("Grape Starter", 75, 5),
    BEET_SEEDS("Beet Seeds", 20, 5),
    YAM_SEEDS("Yam Seeds", 75, 5),
    BOK_CHOY_SEEDS("Bok Choy Seeds", 62, 5),
    CRANBERRY_SEEDS("Cranberry Seeds", 300, 5),
    SUNFLOWER_SEEDS_FALL("Sunflower Seeds", 125, 5),
    FAIRY_SEEDS("Fairy Seeds", 250, 5),
    RARE_SEED("Rare Seed", 1000, 1),
    WHEAT_SEEDS_FALL("Wheat Seeds", 12, 5),

    // Winter Stock
    POWDERMELON_SEEDS("Powdermelon Seeds", 20, 10);

    private final String name;
    private final int price;
    private final int dailyLimit;

    SeedType(String name, int price, int dailyLimit) {
        this.name = name;
        this.price = price;
        this.dailyLimit = dailyLimit;
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
}