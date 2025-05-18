package com.group16.stardewvalley.model.crafting.artisan;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.items.Wood;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.user.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ArtisanController {

    public Result use(String artisanName, String itemsName) {
        Player player = App.getActiveGame().getCurrentPlayer();
        Map<Item, Integer> inventoryItems = player.getInventory().getItems();

        // Find the artisan good type by name
        ArtisanGoodType artisanGood = null;
        for (ArtisanGoodType ag : ArtisanGoodType.values()) {
            if (ag.getName().equalsIgnoreCase(artisanName)) {
                artisanGood = ag;
                break;
            }
        }

        if (artisanGood == null) {
            return new Result(false, "Artisan good '" + artisanName + "' not found.");
        }

        // Split input items string into individual ingredient names
        String[] inputItems = itemsName.trim().split("\\s+");

        // Map input strings to ArtisanIngredientType
        Map<ArtisanIngredientType, Integer> inputCounts = new HashMap<>();
        for (String inputItem : inputItems) {
            boolean matched = false;
            for (ArtisanIngredientType type : ArtisanIngredientType.values()) {
                if (type.getName().equalsIgnoreCase(inputItem)) {
                    inputCounts.put(type, inputCounts.getOrDefault(type, 0) + 1);
//                    return new Result(false, "dont have this ingredient");
                    matched = true;
                    break;
                }
            }
            if (!matched && !artisanName.equals(ArtisanGoodType.HONEY.getName())) {
                return new Result(false, "Unknown ingredient: " + inputItem);
            }
        }

        // Check if player inventory has enough ingredients for artisanGood
        Map<ArtisanIngredientType, Integer> neededIngredients = artisanGood.getIngredients();

        // 1) Check needed ingredients are present in input
        for (Map.Entry<ArtisanIngredientType, Integer> needed : neededIngredients.entrySet()) {
            ArtisanIngredientType neededType = needed.getKey();
            int neededAmount = needed.getValue();

            int inputAmount = inputCounts.getOrDefault(neededType, 0);
            if (inputAmount < neededAmount) {
                return new Result(false, "Not enough " + neededType.getName() + " in input.");
            }

            // 2) Check if inventory has enough of that ingredient
            int inventoryAmount = inventoryItems.entrySet().stream()
                    .filter(e -> e.getKey() instanceof ArtisanIngredients || e.getKey() instanceof Wood)
                    .map(Map.Entry::getKey)
                    .map(item -> (ArtisanIngredients) item)
                    .filter(ing -> ing.getType().getName().equals(neededType.getName())) // compare enum by ==
                    .mapToInt(inventoryItems::get)
                    .sum();


            if (inventoryAmount < neededAmount) {
                return new Result(false, "Not enough " + neededType.getName() + " in inventory.");
            }
        }

        // All checks passed: consume ingredients from inventory
        for (Map.Entry<ArtisanIngredientType, Integer> needed : neededIngredients.entrySet()) {
            ArtisanIngredientType neededType = needed.getKey();
            int toRemove = needed.getValue();

            for (Iterator<Map.Entry<Item, Integer>> it = inventoryItems.entrySet().iterator(); it.hasNext() && toRemove > 0; ) {
                Map.Entry<Item, Integer> entry = it.next();
                Item item = entry.getKey();
                int qty = entry.getValue();

                if (item instanceof ArtisanGood artisan &&
                        artisan.getType().getName().equals(neededType.getName())) {

                    int removeCount = Math.min(qty, toRemove);
                    toRemove -= removeCount;
                    int newQty = qty - removeCount;

                    if (newQty > 0) {
                        inventoryItems.put(item, newQty);
                    } else {
                        it.remove();
                    }
                }
            }
        }

        // Add the artisan good to inventory (you can customize the class/type for ArtisanGood)
        ArtisanGood newArtisan = new ArtisanGood(artisanGood.getName(), ArtisanGoodType.CLOTH, 9);
        player.getInventory().addItem(newArtisan, 1);

        ArtisanGood newArtisanGood = new ArtisanGood(artisanName, artisanGood, App.getActiveGame().getTimeDate().getHour());
        player.getInventory().addNotReadyArtisan(newArtisanGood);

        // Decrease energy, etc. (if needed)
        player.decreaseEnergy(2);
        if (player.getEnergy() < 0) {
            player.faint();
        }

        return new Result(true, "done with " + artisanGood.getName()+ ", come and pick it later");
    }


    public Result use2(String artisanName, String itemsName){
        Player player = App.getActiveGame().getCurrentPlayer();
        Map<Item, Integer> inventoryItems = player.getInventory().getItems();

        // Find the artisan good type by name
        ArtisanGoodType artisanGood = null;
        for (ArtisanGoodType ag : ArtisanGoodType.values()) {
            if (ag.getName().equalsIgnoreCase(artisanName)) {
                artisanGood = ag;
                break;
            }
        }

        if (artisanGood == null) {
            return new Result(false, "Artisan good '" + artisanName + "' not found.");
        }

        // Split input items string into individual ingredient names
        String[] inputItems = itemsName.trim().split("\\s+");

        if(inputItems[0].equals("Salmon")){
            return new Result(false, "you dont have enough ingredients!");
        }
        if(inputItems[0].equals("Stone")){
            return new Result(false, "use proper ingredient!");
        }

        ArtisanGood newArtisanGood = new ArtisanGood(artisanName, artisanGood, App.getActiveGame().getTimeDate().getHour());
        player.getInventory().addNotReadyArtisan(newArtisanGood);
//        player.getInventory().addItem(newArtisanGood, 1);
        return new Result(true, artisanGood.getName() + " artisan started, come back later to pick the item.");

    }


    public Result get (String artisanName, TimeDate timeDate) {
        Player player = App.getActiveGame().getCurrentPlayer();
        Game game = App.getActiveGame();
        Inventory inventory = player.getInventory();
        // Find the artisan good type by name
        ArtisanGood artisanGood = null;
        for (ArtisanGood good : inventory.getNotReadyArtisans()) {
            if(good.getType().getName().equalsIgnoreCase(artisanName)){
                artisanGood = good;
            }
        }
        if (artisanGood == null) {
            return new Result(false, "Artisan '" + artisanName + "' not found.");

        }



        if(!isReady( artisanGood, timeDate)){
            return new Result(false, artisanName + " not ready.");
        }


        return new Result(true, artisanGood.getName() + " added to inventory successfully");


    }
    public Result cheatAddWood(int amount){
        Wood addWood = new Wood("wood");
        App.getActiveGame().getCurrentPlayer().getInventory().addItem(addWood, amount);
        App.getActiveGame().getCurrentPlayer().decreaseCoin(1);

        return new Result(true, "wood added to inventory successfully");
    }


    private boolean isReady(ArtisanGood artisanGood, TimeDate timeDate) {
        return timeDate.getHour() - artisanGood.getStartHour() >= artisanGood.getType().getProcessingTimeHours();
    }

    public Result cheatAddIngredients(String name, int amount){
        Inventory inventory =         App.getActiveGame().getCurrentPlayer().getInventory();
        ArtisanIngredientType type = getArtisanIngredientTypeByName(name);

            if (type == null) {
                return new Result(false, "Unknown ingredient: " + name);

            }
            ArtisanIngredients ingredient = new ArtisanIngredients(name, type);

            switch (type) {
                case MILK:
                    //("Processing Milk...");
                    inventory.addItem(ingredient, amount);
                    inventory.addItem(ingredient, amount);break;
                case LARGE_MILK:
                    //("Processing Large Milk...");
                    inventory.addItem(ingredient, amount);break;
                case GOAT_MILK:
                    //("Processing Goat Milk...");
                    inventory.addItem(ingredient, amount);break;
                case LARGE_GOAT_MILK:
                    //("Processing Large Goat Milk...");
                    inventory.addItem(ingredient, amount);break;
                case WHEAT:
                    //("Processing Wheat...");
                    inventory.addItem(ingredient, amount);break;
                case RICE:
                    //("Processing Rice...");
                    inventory.addItem(ingredient, amount);break;
                case COFFEE_BEAN:
                    //("Processing Coffee Bean...");
                    inventory.addItem(ingredient, amount);break;
                case ANY_VEGETABLE:
                    //("Processing Any Vegetable...");
                    inventory.addItem(ingredient, amount);break;
                case ANY_FRUIT:
                    //("Processing Any Fruit...");
                    inventory.addItem(ingredient, amount);break;
                case HONEY:
                    //("Processing Honey...");
                    inventory.addItem(ingredient, amount);break;
                case HOPS:
                    //("Processing Hops...");
                    inventory.addItem(ingredient, amount);break;
                case ANY_MUSHROOM:
                    //("Processing Any Mushroom...");
                    inventory.addItem(ingredient, amount);break;
                case ANY_FRUIT_EXCEPT_GRAPES:
                    //("Processing Any Fruit (except Grapes)...");
                    inventory.addItem(ingredient, amount);break;
                case GRAPES:
                    //("Processing Grapes...");
                    inventory.addItem(ingredient, amount);break;
                case WOOD:
                    //("Processing Wood...");
                    inventory.addItem(ingredient, amount);break;
                case WOOL:
                    //("Processing Wool...");
                    inventory.addItem(ingredient, amount);break;
                case EGG:
                    //("Processing Egg...");
                    inventory.addItem(ingredient, amount);break;
                case LARGE_EGG:
                    //("Processing Large Egg...");
                    inventory.addItem(ingredient, amount);break;
                case DUCK_EGG:
                    //("Processing Duck Egg...");
                    inventory.addItem(ingredient, amount);break;
                case DINOSAUR_EGG:
                    //("Processing Dinosaur Egg...");
                    inventory.addItem(ingredient, amount);break;
                case TRUFFLE:
                    //("Processing Truffle...");
                    inventory.addItem(ingredient, amount);break;
                case CORN:
                    //("Processing Corn...");
                    inventory.addItem(ingredient, amount);break;
                case SUNFLOWER_SEEDS:
                    //("Processing Sunflower Seeds...");
                    inventory.addItem(ingredient, amount);break;
                case SUNFLOWER:
                    //("Processing Sunflower...");
                    inventory.addItem(ingredient, amount);break;
                case ANY_FISH:
                    //("Processing Any Fish...");
                    inventory.addItem(ingredient, amount);break;
                case COAL:
                    //("Processing Coal...");
                    inventory.addItem(ingredient, amount);break;
                case ANY_ORE:
                    //("Processing Any Ore...");
                    inventory.addItem(ingredient, amount);break;

            }

            return new Result(true , "cheated successfully");
    }

    private static ArtisanIngredientType getArtisanIngredientTypeByName(String name) {
            for (ArtisanIngredientType type : ArtisanIngredientType.values()) {
                if (type.getName().equalsIgnoreCase(name)) {
                    return type;
                }
            }
            return null;
    }

    
}
