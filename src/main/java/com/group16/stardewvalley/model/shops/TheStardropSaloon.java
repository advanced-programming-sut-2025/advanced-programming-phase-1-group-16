package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodRecipe;

public class TheStardropSaloon extends Shop{
    private static TheStardropSaloon instance;

    public TheStardropSaloon() {
        super("The Stardrop Saloon", "Gus", 12, 24);
    }

    public static TheStardropSaloon getInstance() {
        if (instance == null) {
            instance = new TheStardropSaloon();
        }
        return instance;
    }

    public void initializeItems() {
        addItem(new Food("Beer",), Integer.MAX_VALUE);
        addItem(new Food("Salad", ), Integer.MAX_VALUE);
        addItem(new Food("Bread", ), Integer.MAX_VALUE);
        addItem(new Food("Spaghetti", ), Integer.MAX_VALUE);
        addItem(new Food("Pizza", ), Integer.MAX_VALUE);
        addItem(new Food("Coffee", ), Integer.MAX_VALUE);
        addItem(new FoodRecipe(new Food("Hashbrowns"), "Hashbrowns Recipe"));
        addItem(new FoodRecipe(new Food("Omelet"), "Omelet"));
        addItem(new FoodRecipe(new Food("Pancakes"), "Pancakes"));
        addItem(new FoodRecipe(new Food("Bread"), "Bread Recipe"));
        addItem(new FoodRecipe(new Food("Tortilla"), "Tortilla Recipe"));
        addItem(new FoodRecipe(new Food("Pizza"), "Pizza Recipe"));
        addItem(new FoodRecipe(new Food("Maki Roll"), "Maki Roll Recipe"));
        addItem(new FoodRecipe(new Food("Triple Shot Espresso"), "Triple Shot Espresso Recipe"));
        addItem(new FoodRecipe(new Food("Cookie"), "Cookie Recipe"));
    }
}
