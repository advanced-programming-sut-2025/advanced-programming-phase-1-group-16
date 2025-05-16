package com.group16.stardewvalley.model;

import com.group16.stardewvalley.model.agriculture.Seed;
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

    public Result removeItem(Item item, int count) {
        if (!items.containsKey(item)) {
            return new Result(false, "You don't have it");
        }

        int currentCount = items.get(item);
        if (currentCount < count) {
            return new Result(false, "Not enough " + item.getName() + " in inventory! (Available: " + currentCount + ")");
        }

        int newCount = currentCount - count;
        if (newCount > 0) {
            items.put(item, newCount);
        } else {
            items.remove(item);
        }

        return new Result(true, count + " " + item.getName() + "(s) removed from inventory");
    }

    public boolean isFull() {
        return getTotalItemsCount() >= backPackType.getCapacity();
    }

    private int getTotalItemsCount() {
        int toolCount = tools.values().stream().mapToInt(Integer::intValue).sum();
        int itemCount = items.values().stream().mapToInt(Integer::intValue).sum();
        return toolCount+ itemCount;
    }

    public int getNumberOfItem(Item item) {
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            if (entry.getKey().equals(item)) {
                return entry.getValue();
            }
        }
        return 0;
    }

    public Item getItemByName(String name) {
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean isSeedInInventory(SeedType seedType) {
        for (Item item : items.keySet()) {
            if (item instanceof Seed) {
                Seed seed = (Seed) items;
                if (items.get(item) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

}