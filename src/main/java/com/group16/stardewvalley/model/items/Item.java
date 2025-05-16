package com.group16.stardewvalley.model.items;

public abstract class Item {
    private final String name;
    private int price;
    private String material;

    public Item(String name) {
        this.name = name;
        this.material = "base";
    }

    public String getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

}
