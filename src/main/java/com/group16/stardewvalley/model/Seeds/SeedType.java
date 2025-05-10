package com.group16.stardewvalley.model.Seeds;

import com.group16.stardewvalley.model.time.Season;

import java.util.Arrays;
import java.util.List;

public enum SeedType {
    ParsnipSeeds("Parsnip Seeds", 4, Arrays.asList(Season.Spring)),
    BeanStarter("Bean Starter", 10, Arrays.asList(Season.Spring)),
    CauliflowerSeeds("Cauliflower Seeds", 12, Arrays.asList(Season.Spring)),
    PotatoSeeds("Potato Seeds", 6, Arrays.asList(Season.Spring)),
    TulipBulb("Tulip Bulb", 6, Arrays.asList(Season.Spring)),
    KaleSeeds("Kale Seeds", 6, Arrays.asList(Season.Spring)),
    JazzSeeds("Jazz Seeds", 7, Arrays.asList(Season.Spring)),
    GarlicSeeds("Garlic Seeds", 4, Arrays.asList(Season.Spring)),
    StrawberrySeeds("Strawberry Seeds", 8, Arrays.asList(Season.Spring)),
    CarrotSeed("Carrot Seed", 3, Arrays.asList(Season.Spring)),
    RHUBARB_SEEDS("Rhubarb Seeds", 13, Arrays.asList(Season.Spring)),
    CoffeeBean("Coffee Bean", 10, Arrays.asList(Season.Spring)),
    RiceShoot("Rice Shoot", 8, Arrays.asList(Season.Spring)),

    // Summer Seeds
    BlueberrySeeds("Blueberry Seeds", 13, Arrays.asList(Season.Summer)),
    MelonSeeds("Melon Seeds", 12, Arrays.asList(Season.Summer)),
    PepperSeeds("Pepper Seeds", 5, Arrays.asList(Season.Summer)),
    TomatoSeeds("Tomato Seeds", 11, Arrays.asList(Season.Summer)),
    WheatSeeds("Wheat Seeds", 4, Arrays.asList(Season.Summer)),
    RadishSeeds("Radish Seeds", 6, Arrays.asList(Season.Summer)),
    PoppySeeds("Poppy Seeds", 7, Arrays.asList(Season.Summer)),
    SpangleSeeds("Spangle Seeds", 8, Arrays.asList(Season.Summer)),
    HopsStarter("Hops Starter", 11, Arrays.asList(Season.Summer)),
    CornSeeds("Corn Seeds", 14, Arrays.asList(Season.Summer)),
    RedCabbageSeeds("Red Cabbage Seeds", 9, Arrays.asList(Season.Summer)),
    SunflowerSeeds("Sunflower Seeds", 8, Arrays.asList(Season.Summer)),
    STARFRUIT_SEEDS("Starfruit Seeds", 13, Arrays.asList(Season.Summer)),
    SUMMER_SQUASH_SEEDS("Summer Squash Seeds", 6, Arrays.asList(Season.Summer)),

    // Fall Seeds
    PumpkinSeeds("Pumpkin Seeds", 13, Arrays.asList(Season.Fall)),
    YamSeeds("Yam Seeds", 10, Arrays.asList(Season.Fall)),
    EggplantSeeds("Eggplant Seeds", 5, Arrays.asList(Season.Fall)),
    BokChoySeeds("Bok Choy Seeds", 4, Arrays.asList(Season.Fall)),
    AmaranthSeeds("Amaranth Seeds", 7, Arrays.asList(Season.Fall)),
    GrapeStarter("Grape Starter", 10, Arrays.asList(Season.Fall)),
    ArtichokeSeeds("Artichoke Seeds", 8, Arrays.asList(Season.Fall)),
    FairySeeds("Fairy Seeds", 12, Arrays.asList(Season.Fall)),
    BROCCOLI_SEEDS("Broccoli Seeds", 8, Arrays.asList(Season.Fall)),
    BEET_SEEDS("Beet Seeds", 6, Arrays.asList(Season.Fall)),
    CRANBERRYSeeds("Cranberry Seeds", 7, Arrays.asList(Season.Fall)),
    RARE_SEEDS("Rare Seeds", 365, Arrays.asList(Season.Fall)),

    // Winter Seeds
    POWDER_MELON_SEEDS("Powdermelon Seeds", 7, Arrays.asList(Season.Winter)),

    // All Seasons
    AncientSeeds("Ancient Seeds", -100, Arrays.asList(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    MixedSeeds("Mixed Seeds", 0, Arrays.asList(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),

    // Misc
    GrassStarter("Grass Starter", 0, Arrays.asList());

    private final String name;
    private final int day;
    public final List<Season> seasons;

    SeedType(String name, int day, List<Season> seasons) {
        this.name = name;
        this.day = day;
        this.seasons = seasons;
    }

    public String getName() {
        return name;
    }

    public int getDay() {
        return day;
    }

    public static SeedType stringToSeed(String seed) {
        for (SeedType type : SeedType.values()) {
            if (seed.equals(type.name)) {
                return type;
            }
        }
        return null;
    }
}
