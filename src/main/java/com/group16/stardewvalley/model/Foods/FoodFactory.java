package com.group16.stardewvalley.model.Foods;

import java.util.List;
import java.util.Map;

public class FoodFactory {

    public static Food friedEgg() {
        return new Food("Fried Egg", Map.of(Ingredient.EGG, 1), 50, "", "Starter", 35);
    }

    public static Food bakedFish() {
        return new Food("Baked Fish", Map.of(Ingredient.SARDINE, 1, Ingredient.SALMON, 1, Ingredient.WHEAT, 1), 75, "", "Starter", 100);
    }

    public static Food salad() {
        return new Food("Salad", Map.of(Ingredient.LEEK, 1, Ingredient.DANDELION, 1), 113, "", "Starter", 110);
    }

    public static Food omelet() {
        return new Food("Omelet", Map.of(Ingredient.EGG, 1, Ingredient.MILK, 1), 100, "", "Stardrop Saloon", 125);
    }

    public static Food pumpkinPie() {
        return new Food("Pumpkin Pie", Map.of(Ingredient.PUMPKING, 1, Ingredient.WHEAT_FLOUR, 1, Ingredient.MILK, 1, Ingredient.SUGAR, 1), 225, "", "Stardrop Saloon", 385);
    }

    public static Food spaghetti() {
        return new Food("Spaghetti", Map.of(Ingredient.WHEAT_FLOUR, 1, Ingredient.TOMATO, 1), 75, "", "Stardrop Saloon", 120);
    }

    public static Food pizza() {
        return new Food("Pizza", Map.of(Ingredient.WHEAT_FLOUR, 1, Ingredient.TOMATO, 1, Ingredient.CHEESE, 1), 150, "", "Stardrop Saloon", 300);
    }

    public static Food tortilla() {
        return new Food("Tortilla", Map.of(Ingredient.CORN, 1), 50, "", "Stardrop Saloon", 50);
    }

    public static Food makiRoll() {
        return new Food("Maki Roll", Map.of(Ingredient.FISH, 1, Ingredient.RICE, 1, Ingredient.FIBER, 1), 100, "", "Stardrop Saloon", 220);
    }

    public static Food tripleShotEspresso() {
        return new Food("Triple Shot Espresso", Map.of(Ingredient.COFFEE, 3), 200, "Max Energy + 100 (5 hours)", "Stardrop Saloon", 450);
    }

    public static Food cookie() {
        return new Food("Cookie", Map.of(Ingredient.WHEAT_FLOUR, 1, Ingredient.SUGAR, 1, Ingredient.EGG, 1), 90, "", "Stardrop Saloon", 140);
    }

    public static Food hashBrowns() {
        return new Food("Hash Browns", Map.of(Ingredient.HASH_BROWN, 1), 90, "Farming (5 hours)", "Stardrop Saloon", 120);
    }

    public static Food pancakes() {
        return new Food("Pancakes", Map.of(Ingredient.WHEAT_FLOUR, 1, Ingredient.EGG, 1), 90, "Foraging (11 hours)", "Stardrop Saloon", 80);
    }

    public static Food fruitSalad() {
        return new Food("Fruit Salad", Map.of(Ingredient.BLUEBERRY, 1, Ingredient.MELON, 1, Ingredient.APRICOT, 1), 263, "", "Stardrop Saloon", 450);
    }

    public static Food redPlate() {
        return new Food("Red Plate", Map.of(Ingredient.RED_CABBAGE, 1, Ingredient.RADISH, 1), 240, "Max Energy +50 (3 hours)", "Stardrop Saloon", 400);
    }

    public static Food bread() {
        return new Food("Bread", Map.of(Ingredient.WHEAT_FLOUR, 1), 50, "", "Stardrop Saloon", 60);
    }

    public static Food salmonDinner() {
        return new Food("Salmon Dinner", Map.of(Ingredient.SALMON, 1, Ingredient.AMARANTH, 1, Ingredient.KALE, 1), 125, "", "Leah reward", 300);
    }

    public static Food vegetableMedley() {
        return new Food("Vegetable Medley", Map.of(Ingredient.TOMATO, 1, Ingredient.BEET, 1), 165, "", "Foraging Level 2", 120);
    }

    public static Food farmersLunch() {
        return new Food("Farmer's Lunch", Map.of(Ingredient.OMELET, 1, Ingredient.PARSNIP, 1), 200, "Farming (5 hours)", "Farming level 1", 150);
    }

    public static Food survivalBurger() {
        return new Food("Survival Burger", Map.of(Ingredient.BREAD, 1, Ingredient.CARROT, 1, Ingredient.EGGPLANT, 1), 125, "Foraging (5 hours)", "Foraging level 3", 180);
    }

    public static Food dishOTheSea() {
        return new Food("Dish O' the Sea", Map.of(Ingredient.SARDINE, 2, Ingredient.HASH_BROWN, 1), 150, "Fishing (5 hours)", "Fishing level 2", 220);
    }

    public static Food seafoamPudding() {
        return new Food("Seafoam Pudding", Map.of(Ingredient.FLOUNDER, 1, Ingredient.MIDNIGHT_CARP, 1), 175, "Fishing (10 hours)", "Fishing level 3", 300);
    }

    public static Food minersTreat() {
        return new Food("Miner's Treat", Map.of(Ingredient.CARROT, 2, Ingredient.SUGAR, 1, Ingredient.MILK, 1), 125, "Mining (5 hours)", "Mining level 1", 200);
    }

    public static List<Food> getAllFoods() {
        return List.of(
                friedEgg(),
                bakedFish(),
                salad(),
                omelet(),
                pumpkinPie(),
                spaghetti(),
                pizza(),
                tortilla(),
                makiRoll(),
                tripleShotEspresso(),
                cookie(),
                hashBrowns(),
                pancakes(),
                fruitSalad(),
                redPlate(),
                bread(),
                salmonDinner(),
                vegetableMedley(),
                farmersLunch(),
                survivalBurger(),
                dishOTheSea(),
                seafoamPudding(),
                minersTreat()
        );
    }
}