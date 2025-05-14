package com.group16.stardewvalley.model;


import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.items.Seed;
import com.group16.stardewvalley.model.Tools.Gadget;
import com.group16.stardewvalley.model.agriculture.SeedType;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.user.BackPackType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Gadget, Integer> tools;
    private Map<Item, Integer> items;
    private int capacity;
    private ArrayList<CraftingRecipes> craftingRecipes;
    private BackPackType backPackType;

    public Inventory() {
        this.tools = new HashMap<>();
        this.items = new HashMap<>();
        this.backPackType = BackPackType.Base_Pack;

    }

    public Result addTool(Gadget gadget, int count) {
        if (isFull()) {
            return new Result(false, "Oops! Your backpack is completely full ");
        }
        String name = gadget.getName();
        tools.put(gadget, tools.getOrDefault(gadget, 0) + count);
        return new Result(true, gadget.getName() + " added to inventory successfully");
    }

    public Result addItem(Item item, int count) {
        if (isFull()) {
            return new Result(false, "Oops! Your backpack is completely full ");
        }
        String name = item.getName();
        items.put(item, items.getOrDefault(item, 0) + count);
        return new Result(true, item.getName() + " added to inventory successfully");
    }

    public ArrayList<CraftingRecipes> getCraftingRecipes() {
        return craftingRecipes;
    }

    public void setCraftingRecipes(ArrayList<CraftingRecipes> craftingRecipes) {
        this.craftingRecipes = craftingRecipes;
    }

    public void addCraftingRecipes(CraftingRecipes craftingRecipes) {
        this.craftingRecipes.add(craftingRecipes);
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public BackPackType getBackPackType() {
        return backPackType;
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

    public boolean isFull() {
        return getTotalItemsCount() >= backPackType.getCapacity();
    }

    public boolean isSeedInInventory(SeedType seedType) {
        for (Item item : items.keySet()) {
            if (item instanceof Seed) {
                Seed seed = (Seed) item;
                if (items.get(item) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getTotalItemsCount() {
        int toolCount = tools.values().stream().mapToInt(Integer::intValue).sum();
        int itemCount = items.values().stream().mapToInt(Integer::intValue).sum();
        return toolCount+ itemCount;
    }
}