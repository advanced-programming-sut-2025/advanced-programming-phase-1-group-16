package com.group16.stardewvalley.model.food;

import java.util.List;

public record Food(String name, List<String> ingredients, int energy, String buff, String source, int sellPrice) {

    @Override
    public String toString() {
        return name + " (Energy: " + energy + ", Buff: " + (buff == null || buff.isEmpty() ? "None" : buff) + ")";
    }
}
