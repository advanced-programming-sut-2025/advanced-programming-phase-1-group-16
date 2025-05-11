package com.group16.stardewvalley.model.crafting;

import java.util.Map;

public enum CraftingRecipes {
    CherryBomb("Cherry Bomb", Map.of(
            CraftingIngredients.CopperOre, 4,
            CraftingIngredients.Coal, 1
    )),
    Bomb("Bomb", Map.of(
            CraftingIngredients.IronOre, 4,
            CraftingIngredients.Coal, 1
    )),
    MegaBomb("Mega Bomb", Map.of(
            CraftingIngredients.GoldOre, 4,
            CraftingIngredients.Coal, 1
    )),
    Sprinkler("Sprinkler", Map.of(
            CraftingIngredients.CopperBar, 1,
            CraftingIngredients.IronBar, 1
    )),
    QualitySprinkler("Quality Sprinkler", Map.of(
            CraftingIngredients.IronBar, 1,
            CraftingIngredients.GoldBar, 1
    )),
    IridiumSprinkler("Iridium Sprinkler", Map.of(
            CraftingIngredients.GoldBar, 1,
            CraftingIngredients.IridiumBar, 1
    )),
    CharcoalKlin("Charcoal Klin", Map.of(
            CraftingIngredients.Wood, 20,
            CraftingIngredients.CopperBar, 2
    )),
    Furnace("Furnace", Map.of(
            CraftingIngredients.CopperOre, 20,
            CraftingIngredients.Stone, 25
    )),
    Scarecrow("Scarecrow", Map.of(
            CraftingIngredients.Wood, 50,
            CraftingIngredients.Coal, 1,
            CraftingIngredients.Fiber, 20
    )),
    DeluxeScarecrow("Deluxe Scarecrow", Map.of(
            CraftingIngredients.Wood, 50,
            CraftingIngredients.Coal, 1,
            CraftingIngredients.Fiber, 20,
            CraftingIngredients.IridiumOre, 1
    )),
    BeeHouse("Bee House", Map.of(
            CraftingIngredients.Wood, 40,
            CraftingIngredients.Coal, 8,
            CraftingIngredients.IronBar, 1
    )),
    CheesePress("Cheese Press", Map.of(
            CraftingIngredients.Wood, 45,
            CraftingIngredients.Stone, 45,
            CraftingIngredients.CopperBar, 1
    )),
    Keg("Keg", Map.of(
            CraftingIngredients.Wood, 30,
            CraftingIngredients.CopperBar, 1,
            CraftingIngredients.IronBar, 1
    )),
    Loom("Loom", Map.of(
            CraftingIngredients.Wood, 60,
            CraftingIngredients.Fiber, 30
    )),
    MayonnaiseMachine("Mayonnaise Machine", Map.of(
            CraftingIngredients.Wood, 15,
            CraftingIngredients.Stone, 15,
            CraftingIngredients.CopperBar, 1
    )),
    OilMaker("Oil Maker", Map.of(
            CraftingIngredients.Wood, 100,
            CraftingIngredients.GoldBar, 1,
            CraftingIngredients.IronBar, 1
    )),
    PreservesJar("Preserves Jar", Map.of(
            CraftingIngredients.Wood, 50,
            CraftingIngredients.Stone, 40,
            CraftingIngredients.Coal, 8
    )),
    Dehydrator("Dehydrator", Map.of(
            CraftingIngredients.Wood, 30,
            CraftingIngredients.Stone, 20,
            CraftingIngredients.Fiber, 30
    )),
    GrassStarter("Grass Starter", Map.of(
            CraftingIngredients.Wood, 1,
            CraftingIngredients.Fiber, 1
    )),
    FishSmoker("Fish Smoker", Map.of(
            CraftingIngredients.Wood, 50,
            CraftingIngredients.IronBar, 3,
            CraftingIngredients.Coal, 10
    )),
    MysticTreeSeed("Mystic Tree Seed", Map.of(
            CraftingIngredients.Acorn, 5,
            CraftingIngredients.MapleSeed, 5,
            CraftingIngredients.PineCone, 5,
            CraftingIngredients.MahoganySeed, 5
    ));


    private String name;
    private  Map<CraftingIngredients, Integer> neededIngredients;

    CraftingRecipes(String name, Map<CraftingIngredients, Integer> neededIngredients) {
        this.name = name;
        this.neededIngredients = neededIngredients;
    }

    public Map<CraftingIngredients, Integer> getNeededIngredients() {
        return neededIngredients;
    }

    public String getName() {
        return name;
    }
}
