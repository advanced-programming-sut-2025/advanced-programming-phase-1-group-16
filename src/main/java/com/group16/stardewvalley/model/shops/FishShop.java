package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.crafting.CraftItem;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodFactory;
import com.group16.stardewvalley.model.food.FoodRecipe;
import com.group16.stardewvalley.model.tools.FishingPole;

public class FishShop extends Shop{

    private static FishShop instance;

    public FishShop() {
        super("Fish Shop", "Willy", 9, 17);
        initializeItems();
    }

    public static FishShop getInstance() {
        if (instance == null) {
            instance = new FishShop();
        }
        return instance;
    }

    public void initializeItems() {
        addItem(new CraftItem("fish smoker", CraftingRecipes.FishSmoker), 1);
        addItem(new FishingPole("fishing pole", "bamboo"), 1);
        addItem(new FishingPole("fishing pole", "training"), 1);
        addItem(new FishingPole("fishing pole", "fiberglass"), 1);
        addItem(new FishingPole("fishing pole", "iridium"), 1);
    }
}
