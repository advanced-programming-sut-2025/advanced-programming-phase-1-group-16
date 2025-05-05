package com.group16.stardewvalley.model.food;


import java.util.List;

public class FoodFactory {

    public static Food friedEgg() {
        return new Food("Fried Egg", List.of("1 egg"), 50, "", "Starter", 35);
    }

    public static Food bakedFish() {
        return new Food("Baked Fish", List.of("1 Sardine", "1 Salmon", "1 wheat"), 75, "", "Starter", 100);
    }

    public static Food salad() {
        return new Food("Salad", List.of("1 leek", "1 dandelion"), 113, "", "Starter", 110);
    }

    public static Food omelet() {
        return new Food("Omelet", List.of("1 egg", "1 milk"), 100, "", "Stardrop Saloon", 125);
    }

    public static Food pumpkinPie() {
        return new Food("Pumpkin Pie", List.of("1 pumpkin", "1 wheat flour", "1 milk", "1 sugar"), 225, "", "Stardrop Saloon", 385);
    }

    public static Food spaghetti() {
        return new Food("Spaghetti", List.of("1 wheat flour", "1 tomato"), 75, "", "Stardrop Saloon", 120);
    }

    public static Food pizza() {
        return new Food("Pizza", List.of("1 wheat flour", "1 tomato", "1 cheese"), 150, "", "Stardrop Saloon", 300);
    }

    public static Food tortilla() {
        return new Food("Tortilla", List.of("1 corn"), 50, "", "Stardrop Saloon", 50);
    }

    public static Food makiRoll() {
        return new Food("Maki Roll", List.of("1 any fish", "1 rice", "1 fiber"), 100, "", "Stardrop Saloon", 220);
    }

    public static Food tripleShotEspresso() {
        return new Food("Triple Shot Espresso", List.of("3 coffee"), 200, "Max Energy + 100 (5 hours)", "Stardrop Saloon", 450);
    }

    public static Food cookie() {
        return new Food("Cookie", List.of("1 wheat flour", "1 sugar", "1 egg"), 90, "", "Stardrop Saloon", 140);
    }

    public static Food hashBrowns() {
        return new Food("Hash Browns", List.of("1 potato", "1 oil"), 90, "Farming (5 hours)", "Stardrop Saloon", 120);
    }

    public static Food pancakes() {
        return new Food("Pancakes", List.of("1 wheat flour", "1 egg"), 90, "Foraging (11 hours)", "Stardrop Saloon", 80);
    }

    public static Food fruitSalad() {
        return new Food("Fruit Salad", List.of("1 blueberry", "1 melon", "1 apricot"), 263, "", "Stardrop Saloon", 450);
    }

    public static Food redPlate() {
        return new Food("Red Plate", List.of("1 red cabbage", "1 radish"), 240, "Max Energy +50 (3 hours)", "Stardrop Saloon", 400);
    }

    public static Food bread() {
        return new Food("Bread", List.of("1 wheat flour"), 50, "", "Stardrop Saloon", 60);
    }

    public static Food salmonDinner() {
        return new Food("Salmon Dinner", List.of("1 salmon", "1 Amaranth", "1 Kale"), 125, "", "Leah reward", 300);
    }

    public static Food vegetableMedley() {
        return new Food("Vegetable Medley", List.of("1 tomato", "1 beet"), 165, "", "Foraging Level 2", 120);
    }

    public static Food farmersLunch() {
        return new Food("Farmer's Lunch", List.of("1 omelet", "1 parsnip"), 200, "Farming (5 hours)", "Farming level 1", 150);
    }

    public static Food survivalBurger() {
        return new Food("Survival Burger", List.of("1 bread", "1 carrot", "1 eggplant"), 125, "Foraging (5 hours)", "Foraging level 3", 180);
    }

    public static Food dishOTheSea() {
        return new Food("Dish O' the Sea", List.of("2 sardines", "1 hash browns"), 150, "Fishing (5 hours)", "Fishing level 2", 220);
    }

    public static Food seafoamPudding() {
        return new Food("Seafoam Pudding", List.of("1 Flounder", "1 midnight carp"), 175, "Fishing (10 hours)", "Fishing level 3", 300);
    }

    public static Food minersTreat() {
        return new Food("Miner's Treat", List.of("2 carrot", "1 sugar", "1 milk"), 125, "Mining (5 hours)", "Mining level 1", 200);
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