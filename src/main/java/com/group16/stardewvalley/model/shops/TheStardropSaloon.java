package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.map.PlaceType;

import java.util.ArrayList;

public class TheStardropSaloon extends Shop{
    private static TheStardropSaloon instance;

    public TheStardropSaloon() {
        super("The Stardrop Saloon", "Gus", 12, 24, PlaceType.TheStardropSaloon);
    }

    public static TheStardropSaloon getInstance() {
        if (instance == null) {
            instance = new TheStardropSaloon();
        }
        return instance;
    }

    public void initializeItems() {
        addItem(FoodFactory.salad(), Integer.MAX_VALUE);
        addItem(FoodFactory.bread(), Integer.MAX_VALUE);
        addItem(FoodFactory.spaghetti(), Integer.MAX_VALUE);
        addItem(FoodFactory.pizza(), Integer.MAX_VALUE);
        addItem(FoodFactory.tripleShotEspresso(), Integer.MAX_VALUE);
        addItem(new FoodRecipe("Hashbrowns Recipe", FoodFactory.hashBrowns()), 1);
        addItem(new FoodRecipe("Omelet Recipe", FoodFactory.omelet()), 1);
        addItem(new FoodRecipe("Pancakes Recipe", FoodFactory.pancakes()), 1);
        addItem(new FoodRecipe("Bread Recipe", FoodFactory.bread()), 1);
        addItem(new FoodRecipe("Tortilla Recipe", FoodFactory.tortilla()), 1);
        addItem(new FoodRecipe("Pizza Recipe", FoodFactory.pizza()), 1);
        addItem(new FoodRecipe("Maki Roll Recipe", FoodFactory.makiRoll()), 1);
        addItem(new FoodRecipe("Triple Shot Espresso Recipe", FoodFactory.tripleShotEspresso()), 1);
        addItem(new FoodRecipe("Cookie Recipe", FoodFactory.cookie()), 1);
    }
}
