package com.group16.stardewvalley.model;

import com.group16.stardewvalley.model.Items.Item;
import com.group16.stardewvalley.model.Tools.*;
import com.group16.stardewvalley.model.agriculture.Seed;
import com.group16.stardewvalley.model.agriculture.SeedType;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.tools.*;
import com.group16.stardewvalley.model.agriculture.Crop;

import java.util.HashMap;
import java.util.Map;


public class Inventory {
    private Map<Gadget, Integer> tools;
    private Map<Item, Integer> items;
    private Map<Crop, Integer> crops;
    private BackPackType backPackType;

    public Inventory() {
        this.tools = new HashMap<>();
        this.items = new HashMap<>();
        this.crops = new HashMap<>();
        this.backPackType = BackPackType.Base_Pack;
    }

    public void setTools(Map<Gadget, Integer> tools) {
        this.tools = tools;
    }

    public BackPackType getBackPackType() {
        return backPackType;
    }

    public void setBackPackType(BackPackType backPackType) {
        this.backPackType = backPackType;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    public Result addTool(Gadget gadget, int count) {
        if (isFull()) {
            return new Result(false, "Oops! Your backpack is completely full ");
        }
        tools.put(gadget, tools.getOrDefault(gadget, 0) + count);
        return new Result(true, gadget.getName() + " added to inventory successfully");
    }

    public Void addCrop(Crop crop, int count) {
        crops.put(crop, crops.getOrDefault(crop, 0));
    }

    public Gadget findToolByName(String name) {
        for (Map.Entry<Gadget, Integer> entry : tools.entrySet()) {
            Gadget gadget = entry.getKey();
            if (gadget.getName().equalsIgnoreCase(name)) {
                return gadget;
            }
        }
        return null;
    }

    public Map<Gadget, Integer> getTools() {
        return tools;
    }

    public Result addItem(Item item, int count) {
        if (isFull()) {
            return new Result(false, "Oops! Your backpack is completely full!");
        }
        String name = item.getName();
        items.put(item, items.getOrDefault(item, 0) + count);
        return new Result(true, name + " added to inventory successfully ^^");
    }

    public boolean isFull() {
        return getTotalItemsCount() >= backPackType.getCapacity();
    }

    private int getTotalItemsCount() {
        int toolCount = tools.values().stream().mapToInt(Integer::intValue).sum();
        int itemCount = items.values().stream().mapToInt(Integer::intValue).sum();
        return toolCount+ itemCount;
    }

    public boolean isSeedInInventory(SeedType seedType) {
        for (Item item : items.keySet()) {
            if (item instanceof Seed) {
                Seed seed = (Seed) item;
                if (item.getSeedType == seedType && items.get(item) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public Food getFood(String foodName) {
        for (Item item : items.keySet()) {
            if (item instanceof Food food) {
                if (food.getName().equalsIgnoreCase(foodName)) {
                    return food;
                }
            }
        }
        return null;
    }

    public FoodIngredient getFoodIngredient(Ingredient ingredient) {
        for (Item item : items.keySet()) {
            if (item instanceof FoodIngredient foodIngredient) {
                if (foodIngredient.getType().equals(ingredient) && items.get(item) > 0) {
                    return foodIngredient;
                }
            }
        }
        return null;
    }
}