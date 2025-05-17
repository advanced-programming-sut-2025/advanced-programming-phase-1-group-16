package com.group16.stardewvalley.model.crafting;

import java.util.Map;

public enum CraftingRecipes {
    CherryBomb("Cherry Bomb", Map.of(
            CraftingIngredientsTypes.CopperOre, 4,
            CraftingIngredientsTypes.Coal, 1
    ), Map.of(CraftingSource.miningAbilityLevel, 1)),
    Bomb("Bomb", Map.of(
            CraftingIngredientsTypes.IronOre, 4,
            CraftingIngredientsTypes.Coal, 1
    ), Map.of(CraftingSource.miningAbilityLevel, 2)),
    MegaBomb("Mega Bomb", Map.of(
            CraftingIngredientsTypes.GoldOre, 4,
            CraftingIngredientsTypes.Coal, 1
    ), Map.of(CraftingSource.miningAbilityLevel, 3)),
    Sprinkler("Sprinkler", Map.of(
            CraftingIngredientsTypes.CopperBar, 1,
            CraftingIngredientsTypes.IronBar, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 1)),
    QualitySprinkler("Quality Sprinkler", Map.of(
            CraftingIngredientsTypes.IronBar, 1,
            CraftingIngredientsTypes.GoldBar, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 2)),
    IridiumSprinkler("Iridium Sprinkler", Map.of(
            CraftingIngredientsTypes.GoldBar, 1,
            CraftingIngredientsTypes.IridiumBar, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 3)),
    CharcoalKlin("Charcoal Klin", Map.of(
            CraftingIngredientsTypes.Wood, 20,
            CraftingIngredientsTypes.CopperBar, 2
    ), Map.of(CraftingSource.foragingAbilityLevel, 1)),
    Furnace("Furnace", Map.of(
            CraftingIngredientsTypes.CopperOre, 20,
            CraftingIngredientsTypes.Stone, 25
    ), Map.of()),
    Scarecrow("Scarecrow", Map.of(
            CraftingIngredientsTypes.Wood, 50,
            CraftingIngredientsTypes.Coal, 1,
            CraftingIngredientsTypes.Fiber, 20
    ), Map.of()),
    DeluxeScarecrow("Deluxe Scarecrow", Map.of(
            CraftingIngredientsTypes.Wood, 50,
            CraftingIngredientsTypes.Coal, 1,
            CraftingIngredientsTypes.Fiber, 20,
            CraftingIngredientsTypes.IridiumOre, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 2)),
    BeeHouse("Bee House", Map.of(
            CraftingIngredientsTypes.Wood, 40,
            CraftingIngredientsTypes.Coal, 8,
            CraftingIngredientsTypes.IronBar, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 1)),
    CheesePress("Cheese Press", Map.of(
            CraftingIngredientsTypes.Wood, 45,
            CraftingIngredientsTypes.Stone, 45,
            CraftingIngredientsTypes.CopperBar, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 2)),
    Keg("Keg", Map.of(
            CraftingIngredientsTypes.Wood, 30,
            CraftingIngredientsTypes.CopperBar, 1,
            CraftingIngredientsTypes.IronBar, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 3)),
    Loom("Loom", Map.of(
            CraftingIngredientsTypes.Wood, 60,
            CraftingIngredientsTypes.Fiber, 30
    ), Map.of(CraftingSource.farmingAbilityLevel, 3)),
    MayonnaiseMachine("Mayonnaise Machine", Map.of(
            CraftingIngredientsTypes.Wood, 15,
            CraftingIngredientsTypes.Stone, 15,
            CraftingIngredientsTypes.CopperBar, 1
    ), Map.of()),
    OilMaker("Oil Maker", Map.of(
            CraftingIngredientsTypes.Wood, 100,
            CraftingIngredientsTypes.GoldBar, 1,
            CraftingIngredientsTypes.IronBar, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 3)),
    PreservesJar("Preserves Jar", Map.of(
            CraftingIngredientsTypes.Wood, 50,
            CraftingIngredientsTypes.Stone, 40,
            CraftingIngredientsTypes.Coal, 8
    ), Map.of(CraftingSource.farmingAbilityLevel, 2)),
    Dehydrator("Dehydrator", Map.of(
            CraftingIngredientsTypes.Wood, 30,
            CraftingIngredientsTypes.Stone, 20,
            CraftingIngredientsTypes.Fiber, 30
    ), Map.of(CraftingSource.PierresGeneralStore, 0)),
    GrassStarter("Grass Starter", Map.of(
            CraftingIngredientsTypes.Wood, 1,
            CraftingIngredientsTypes.Fiber, 1
    ), Map.of(CraftingSource.PierresGeneralStore, 0)),
    FishSmoker("Fish Smoker", Map.of(
            CraftingIngredientsTypes.Wood, 50,
            CraftingIngredientsTypes.IronBar, 3,
            CraftingIngredientsTypes.Coal, 10
    ), Map.of(CraftingSource.FishShop,0)),
    MysticTreeSeed("Mystic Tree Seed", Map.of(
            CraftingIngredientsTypes.Acorn, 5,
            CraftingIngredientsTypes.MapleSeed, 5,
            CraftingIngredientsTypes.PineCone, 5,
            CraftingIngredientsTypes.MahoganySeed, 5
    ), Map.of(CraftingSource.foragingAbilityLevel, 4));


    private String name;
    private  Map<CraftingIngredientsTypes, Integer> neededIngredients;
    private Map<CraftingSource, Integer> source;

    CraftingRecipes(String name, Map<CraftingIngredientsTypes, Integer> neededIngredients, Map<CraftingSource, Integer> source) {
        this.name = name;
        this.neededIngredients = neededIngredients;
        this.source = source;
    }



    public Map<CraftingIngredientsTypes, Integer> getNeededIngredients() {
        return neededIngredients;
    }

    public String getName() {
        return name;
    }


    public Map<CraftingSource, Integer> getSource() {
        return source;
    }

    public CraftingSource getCraftingSourcetype() {

        Map.Entry<CraftingSource, Integer> sourceEntry = this.getSource().entrySet().iterator().next();
        return sourceEntry.getKey();
    }

    public Integer getCraftingValueNumber() {
        Map.Entry<CraftingSource, Integer> sourceEntry = this.getSource().entrySet().iterator().next();
        return sourceEntry.getValue();
    }


}
