package com.group16.stardewvalley.model;


import com.group16.stardewvalley.model.Tools.Gadget;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Gadget, Integer> tools;
    private int capacity;

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

    public Map<Gadget, Integer> getTools() {
        return tools;
    }
}