package com.group16.stardewvalley.model;

import com.group16.stardewvalley.model.Tools.Gadget;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Gadget, Integer> tools;
    private int capacity;
    private ArrayList<CraftingRecipes> craftingRecipes;

    public Inventory() {
        this.tools = new HashMap<>();
    }

    public void addTool(Gadget gadget, int count) {
        String name = gadget.getName();
        tools.put(gadget, tools.getOrDefault(gadget, 0) + count);
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

    public ArrayList<CraftingRecipes> getCraftingRecipes() {
        return craftingRecipes;
    }

    public void setCraftingRecipes(ArrayList<CraftingRecipes> craftingRecipes) {
        this.craftingRecipes = craftingRecipes;
    }

    public void addCraftingRecipes(CraftingRecipes craftingRecipes) {
        this.craftingRecipes.add(craftingRecipes);
    }

    public Map<Gadget, Integer> getTools() {
        return tools;
    }
}