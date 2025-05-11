package com.group16.stardewvalley.model.Items;

public abstract class Item {
    private final String name;
    private int price;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

}
