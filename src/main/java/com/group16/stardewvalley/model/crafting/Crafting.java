package com.group16.stardewvalley.model.crafting;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.user.Player;

import java.util.Map;

public class Crafting {
    public Result showRecipes() {
        Player player = App.getActiveGame().getCurrentPlayer();
        Inventory inventory = player.getPlayerInventory();

        if(!isInsideHouse(App.getActiveGame().getCurrentPlayer())){
            return new Result(false, "You are not inside your house !");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("your recipes:\n");
        for (CraftingRecipes recipe : inventory.getCraftingRecipes()) {
            sb.append(recipe.toString()).append("\n");
        }

        return new Result(true, sb.toString());

    }

    public Result craft(String itemName) {
        Player player = App.getActiveGame().getCurrentPlayer();
        Inventory inventory = player.getPlayerInventory();

        if(!isInsideHouse(App.getActiveGame().getCurrentPlayer())){
            return new Result(false, "You are not inside your house !");
        }

        for (CraftingRecipes recipe : CraftingRecipes.values()) {
            if (recipe.getName().equalsIgnoreCase(itemName)) {

            }
        }
        return new Result(false, "recipe name not found");

    }






    private boolean isInsideHouse(Player player) {  //TODO : map structure?
        return true;
    }
}
