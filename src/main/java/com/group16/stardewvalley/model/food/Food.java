package com.group16.stardewvalley.model.food;

import java.util.Map;

public record Food(String name, Map<Ingredient, Integer> ingredients, int energy, String buff, String source, int sellPrice) {

    @Override
    public String toString() {
        return name + " (Energy: " + energy + ", Buff: " + (buff == null || buff.isEmpty() ? "None" : buff) + ")";
    }

    public String getFormattedRecipe() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
            if (!sb.isEmpty()) sb.append(" + ");
            sb.append(entry.getValue()).append(" ").append(formatName(entry.getKey().name()));
        }
        return sb.toString();
    }

    private String formatName(String name) {
        String lower = name.toLowerCase().replace('_', ' ');
        String[] words = lower.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return sb.toString().trim();
    }

}
