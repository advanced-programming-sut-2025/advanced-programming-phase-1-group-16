package com.group16.stardewvalley.model.food;

import java.util.Map;

public record Food(String name, Map<Ingredient, Integer> ingredients, int energy, String buff, String source, int sellPrice) {

    @Override
    public String toString() {
        return name + " (Energy: " + energy + ", Buff: " + (buff == null || buff.isEmpty() ? "None" : buff) + ")";
    }
}
