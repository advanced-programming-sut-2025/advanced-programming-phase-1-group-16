package com.group16.stardewvalley.model.Items;

public enum OreType {
    COPPER_ORE("Copper Ore",
            "A common ore that can be smelted into bars",
            75,
            false),
    IRON_ORE("Iron Ore",
            "A fairly common ore that can be smelted into bars",
            150,
            false),
    COAL("Coal",
            "A combustible rock that is useful for crafting and smelting",
            150,
            false),
    GOLD_ORE("Gold Ore",
            "A precious ore that can be smelted into bars",
            400,
            false);

    private final String name;
    private final String description;
    private final int price;
    private final boolean hasDailyLimit;

    OreType(String name, String description, int price, boolean hasDailyLimit) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.hasDailyLimit = hasDailyLimit;
    }

    public String getName() {
        return name;
    }
}
