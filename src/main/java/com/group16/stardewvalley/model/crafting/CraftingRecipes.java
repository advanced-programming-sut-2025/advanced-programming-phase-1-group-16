package com.group16.stardewvalley.model.crafting;

import java.util.Map;

public enum CraftingRecipes {
    CherryBomb("Cherry Bomb", Map.of(
            CraftingIngredientsTypes.CopperOre, 4,
            CraftingIngredientsTypes.Coal, 1
    )),
    Bomb("Bomb", Map.of(
            CraftingIngredientsTypes.IronOre, 4,
            CraftingIngredientsTypes.Coal, 1
    )),
    MegaBomb("Mega Bomb", Map.of(
            CraftingIngredientsTypes.GoldOre, 4,
            CraftingIngredientsTypes.Coal, 1
    )),
    Sprinkler("Sprinkler", Map.of(
            CraftingIngredientsTypes.CopperBar, 1,
            CraftingIngredientsTypes.IronBar, 1
    )),
    QualitySprinkler("Quality Sprinkler", Map.of(
            CraftingIngredientsTypes.IronBar, 1,
            CraftingIngredientsTypes.GoldBar, 1
    )),
    IridiumSprinkler("Iridium Sprinkler", Map.of(
            CraftingIngredientsTypes.GoldBar, 1,
            CraftingIngredientsTypes.IridiumBar, 1
    )),
    CharcoalKlin("Charcoal Klin", Map.of(
            CraftingIngredientsTypes.Wood, 20,
            CraftingIngredientsTypes.CopperBar, 2
    )),
    Furnace("Furnace", Map.of(
            CraftingIngredientsTypes.CopperOre, 20,
            CraftingIngredientsTypes.Stone, 25
    )),
    Scarecrow("Scarecrow", Map.of(
            CraftingIngredientsTypes.Wood, 50,
            CraftingIngredientsTypes.Coal, 1,
            CraftingIngredientsTypes.Fiber, 20
    )),
    DeluxeScarecrow("Deluxe Scarecrow", Map.of(
            CraftingIngredientsTypes.Wood, 50,
            CraftingIngredientsTypes.Coal, 1,
            CraftingIngredientsTypes.Fiber, 20,
            CraftingIngredientsTypes.IridiumOre, 1
    )),
    BeeHouse("Bee House", Map.of(
            CraftingIngredientsTypes.Wood, 40,
            CraftingIngredientsTypes.Coal, 8,
            CraftingIngredientsTypes.IronBar, 1
    )),
    CheesePress("Cheese Press", Map.of(
            CraftingIngredientsTypes.Wood, 45,
            CraftingIngredientsTypes.Stone, 45,
            CraftingIngredientsTypes.CopperBar, 1
    )),
    Keg("Keg", Map.of(
            CraftingIngredientsTypes.Wood, 30,
            CraftingIngredientsTypes.CopperBar, 1,
            CraftingIngredientsTypes.IronBar, 1
    )),
    Loom("Loom", Map.of(
            CraftingIngredientsTypes.Wood, 60,
            CraftingIngredientsTypes.Fiber, 30
    )),
    MayonnaiseMachine("Mayonnaise Machine", Map.of(
            CraftingIngredientsTypes.Wood, 15,
            CraftingIngredientsTypes.Stone, 15,
            CraftingIngredientsTypes.CopperBar, 1
    )),
    OilMaker("Oil Maker", Map.of(
            CraftingIngredientsTypes.Wood, 100,
            CraftingIngredientsTypes.GoldBar, 1,
            CraftingIngredientsTypes.IronBar, 1
    )),
    PreservesJar("Preserves Jar", Map.of(
            CraftingIngredientsTypes.Wood, 50,
            CraftingIngredientsTypes.Stone, 40,
            CraftingIngredientsTypes.Coal, 8
    )),
    Dehydrator("Dehydrator", Map.of(
            CraftingIngredientsTypes.Wood, 30,
            CraftingIngredientsTypes.Stone, 20,
            CraftingIngredientsTypes.Fiber, 30
    )),
    GrassStarter("Grass Starter", Map.of(
            CraftingIngredientsTypes.Wood, 1,
            CraftingIngredientsTypes.Fiber, 1
    )),
    FishSmoker("Fish Smoker", Map.of(
            CraftingIngredientsTypes.Wood, 50,
            CraftingIngredientsTypes.IronBar, 3,
            CraftingIngredientsTypes.Coal, 10
    )),
    MysticTreeSeed("Mystic Tree Seed", Map.of(
            CraftingIngredientsTypes.Acorn, 5,
            CraftingIngredientsTypes.MapleSeed, 5,
            CraftingIngredientsTypes.PineCone, 5,
            CraftingIngredientsTypes.MahoganySeed, 5
    ));


    private String name;
    private  Map<CraftingIngredientsTypes, Integer> neededIngredients;

    CraftingRecipes(String name, Map<CraftingIngredientsTypes, Integer> neededIngredients) {
        this.name = name;
        this.neededIngredients = neededIngredients;
    }

    public Map<CraftingIngredientsTypes, Integer> getNeededIngredients() {
        return neededIngredients;
    }

    public String getName() {
        return name;
    }


}
