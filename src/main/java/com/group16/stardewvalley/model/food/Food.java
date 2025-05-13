package com.group16.stardewvalley.model.food;

import com.group16.stardewvalley.model.Items.Item;

import java.util.Map;

public class Food extends Item {
    private final String name;
    private final Map<Ingredient, Integer> ingredients;
    private final int energy;
    private final String buff;
    private final String source;
    private final int sellPrice;

    public Food(String name, Map<Ingredient, Integer> ingredients, int energy, String buff, String source, int sellPrice) {
        super(name);
        this.name = name;
        this.ingredients = ingredients;
        this.energy = energy;
        this.buff = buff;
        this.source = source;
        this.sellPrice = sellPrice;
    }

    public Food(Food other) {
        super(other.name);
        this.name = other.name;
        this.ingredients = Map.copyOf(other.ingredients); // یا یک نسخه جدید از Map
        this.energy = other.energy;
        this.buff = other.buff;
        this.source = other.source;
        this.sellPrice = other.sellPrice;
    }

    @Override
    public String getName() {
        return name;
    }

    public Map<Ingredient, Integer> getIngredients() {
        return ingredients;
    }

    public int getEnergy() {
        return energy;
    }

    public String getSource() {
        return source;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public String getBuff() {
        return buff;
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
