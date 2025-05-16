package com.group16.stardewvalley.model.crafting.artisan;

import com.group16.stardewvalley.model.crafting.CraftingRecipes;

import java.util.Map;

public enum ArtisanGood {
    HONEY("Honey", CraftingRecipes.BeeHouse, "It's a sweet syrup produced by bees.", 75, 96, Map.of(), 350),

    CHEESE("Cheese", CraftingRecipes.CheesePress, "It's your basic cheese.", 100, 3, Map.of("Milk or Large Milk", 1), 230),
    GOAT_CHEESE("Goat_Cheese", CraftingRecipes.CheesePress, "Soft cheese made from goat's milk.", 100, 3, Map.of("Goat Milk or Large Goat Milk", 1), 400),

    BEER("Beer", CraftingRecipes.Keg, "Drink in moderation.", 50, 24, Map.of("Wheat", 1), 200),
    VINEGAR("Vinegar", CraftingRecipes.Keg, "An aged fermented liquid used in many cooking recipes.", 13, 10, Map.of("Rice", 1), 100),
    COFFEE("Coffee", CraftingRecipes.Keg, "It smells delicious. This is sure to give you a boost.", 75, 2, Map.of("Coffee Bean", 5), 150),
    JUICE("Juice", CraftingRecipes.Keg, "A sweet, nutritious beverage.", 0, 96, Map.of("Any Vegetable", 1), 0),
    MEAD("Mead", CraftingRecipes.Keg, "A fermented beverage made from honey. Drink in moderation.", 100, 10, Map.of("Honey", 1), 300),
    PALE_ALE("Pale_Ale", CraftingRecipes.Keg, "Drink in moderation.", 50, 72, Map.of("Hops", 1), 300),
    WINE("Wine", CraftingRecipes.Keg, "Drink in moderation.", 0, 168, Map.of("Any Fruit", 1), 0),

    DRIED_MUSHROOMS("Dried_Mushrooms", CraftingRecipes.Dehydrator, "A package of gourmet mushrooms.", 50, 8, Map.of("Any Mushroom", 5), 0),
    DRIED_FRUIT("Dried_Fruit", CraftingRecipes.Dehydrator, "Chewy pieces of dried fruit.", 75, 8, Map.of("Any Fruit (except Grapes)", 5), 0),
    RAISINS("Raisins", CraftingRecipes.Dehydrator, "It's said to be the Junimos' favorite food.", 125, 8, Map.of("Grapes", 5), 600),

    COAL("Coal", CraftingRecipes.CharcoalKlin, "Turns 10 pieces of wood into one piece of coal.", 0, 1, Map.of("Wood", 10), 50),

    CLOTH("Cloth", CraftingRecipes.Loom, "A bolt of fine wool cloth.", 0, 4, Map.of("Wool", 1), 470),

    MAYONNAISE("Mayonnaise", CraftingRecipes.MayonnaiseMachine, "It looks spreadable.", 50, 3, Map.of("Egg or Large Egg", 1), 190),
    DUCK_MAYONNAISE("Duck_Mayonnaise", CraftingRecipes.MayonnaiseMachine, "It's a rich, yellow mayonnaise.", 75, 3, Map.of("Duck Egg", 1), 37),
    DINOSAUR_MAYONNAISE("Dinosaur_Mayonnaise", CraftingRecipes.MayonnaiseMachine, "It's thick and creamy, with a vivid green hue. It smells like grass and leather.", 125, 3, Map.of("Dinosaur Egg", 1), 800),

    TRUFFLE_OIL("Truffle_Oil", CraftingRecipes.OilMaker, "A gourmet cooking ingredient.", 38, 6, Map.of("Truffle", 1), 1065),
    OIL("Oil", CraftingRecipes.OilMaker, "All purpose cooking oil.", 13, 6, Map.of("Corn or Sunflower Seeds or Sunflower", 1), 100),

    PICKLES("Pickles", CraftingRecipes.PreservesJar, "A jar of your home-made pickles.", 0, 6, Map.of("Any Vegetable", 1), 0),
    JELLY("Jelly", CraftingRecipes.PreservesJar, "Gooey.", 0, 72, Map.of("Any Fruit", 1), 0),

    SMOKED_FISH("Smoked_Fish", CraftingRecipes.FishSmoker, "A whole fish, smoked to perfection.", 0, 1, Map.of("Any Fish", 1, "Coal", 1), 0),

    METAL_BAR("Any_Metal_Bar", CraftingRecipes.Furnace, "Turns ore and coal into metal bars.", 0, 4, Map.of("Any Ore", 5, "Coal", 1), 0);

    private final String name;
    private final CraftingRecipes craftMachine;
    private final String description;
    private final int energy;
    private final int processingTimeHours;
    private final Map<String, Integer> ingredients;
    private final int sellPrice;

    ArtisanGood(String name, CraftingRecipes craftMachine, String description, int energy, int processingTimeHours, Map<String, Integer> ingredients, int sellPrice) {
        this.name = name;
        this.craftMachine = craftMachine;
        this.description = description;
        this.energy = energy;
        this.processingTimeHours = processingTimeHours;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public CraftingRecipes getCraftMachine() {
        return craftMachine;
    }

    public String getDescription() {
        return description;
    }

    public int getEnergy() {
        return energy;
    }

    public int getProcessingTimeHours() {
        return processingTimeHours;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}

