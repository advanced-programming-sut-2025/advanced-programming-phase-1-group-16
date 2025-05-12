package com.group16.stardewvalley.model.crafting;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.Things.Item;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.map.Direction;
import com.group16.stardewvalley.model.user.Player;

import java.util.Iterator;
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
        Map<Item, Integer> inventoryItems = player.getPlayerInventory().getItems();  // Inventory content

        if (!isInsideHouse(App.getActiveGame().getCurrentPlayer())) {
            return new Result(false, "You are not inside your house !");
        }

        for (CraftingRecipes recipe : CraftingRecipes.values()) {
            if (recipe.getName().equalsIgnoreCase(itemName)) {
                Map<CraftingIngredientsTypes, Integer> neededIngredients = recipe.getNeededIngredients();

                for (Map.Entry<CraftingIngredientsTypes, Integer> entry : neededIngredients.entrySet()) {
                    CraftingIngredientsTypes requiredType = entry.getKey();
                    int requiredAmount = entry.getValue();

                    int availableAmount = inventoryItems.entrySet().stream()
                            .filter(e -> e.getKey() instanceof CraftingIngredients)
                            .map(Map.Entry::getKey)
                            .map(item -> (CraftingIngredients) item)
                            .filter(ing -> ing.getIngredientType().equals(requiredType)) // match by type
                            .mapToInt(inventoryItems::get)  // get quantity
                            .sum();

                    if (availableAmount < requiredAmount) {
                        return new Result(false, "not enough of this ingredient");
                    }
                }
                //return true;  // All ingredients are available in required amounts
                //create new crafting item
                CraftItem newCraftItem = new CraftItem(recipe.getName(), recipe.getNeededIngredients());
                player.getPlayerInventory().addItem(newCraftItem, 1);

                //consume energy
                player.decreaseEnergy(2);
                //delete used ingredients from inventory
                for (Map.Entry<CraftingIngredientsTypes, Integer> entry : neededIngredients.entrySet()) {
                    CraftingIngredientsTypes neededType = entry.getKey();
                    int toRemove = entry.getValue();

                    for (Iterator<Map.Entry<Item, Integer>> iterator = inventoryItems.entrySet().iterator(); iterator.hasNext() && toRemove > 0; ) {
                        Map.Entry<Item, Integer> invEntry = iterator.next();
                        Item item = invEntry.getKey();
                        int quantity = invEntry.getValue();

                        if (item instanceof CraftingIngredients craftingItem &&
                                craftingItem.getIngredientType().equals(neededType)) {

                            int removeCount = Math.min(quantity, toRemove);
                            toRemove -= removeCount;
                            int newCount = quantity - removeCount;

                            if (newCount > 0) {
                                inventoryItems.put(item, newCount);
                            } else {
                                iterator.remove();
                            }
                        }
                    }

                }
                return new Result(true, "successfully crafted " + recipe.getName());
            }
        }
        return new Result(false, "recipe name not found");

    }

    public Result placeItems(String itemName, String inputDirection) {

        try {
            Direction dir = Direction.fromString(inputDirection);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid direction entered.");
        }

        Inventory inventory = App.getActiveGame().getCurrentPlayer().getPlayerInventory();


        for(Item item: inventory.getItems().keySet()){
            if(item.getName().equalsIgnoreCase(itemName)){
                //that crafting item exists in inventory


                //place item on the ground
                //delete from inventory

                //do what it should do
            }
        }
        return new Result(false, "item not found in inventory");

    }


    public Result cheatAddItem(String itemName, int count) {
        Player player = App.getActiveGame().getCurrentPlayer();
        Inventory inventory = player.getPlayerInventory();

        //create new crafting item
        for (CraftingRecipes recipe : CraftingRecipes.values()) {//find which item is it
            if (recipe.getName().equalsIgnoreCase(itemName)) {
                CraftItem newCraftItem = new CraftItem(recipe.getName(), recipe.getNeededIngredients());
                player.getPlayerInventory().addItem(newCraftItem, 1);
                return new Result(true, "successfully cheated" );
            }
        }

        return new Result(false, "item not found");
    }

    private boolean isInsideHouse(Player player) {  //TODO : map structure?
        return true;
    }
}
