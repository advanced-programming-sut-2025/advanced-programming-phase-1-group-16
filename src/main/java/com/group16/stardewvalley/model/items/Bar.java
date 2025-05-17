package com.group16.stardewvalley.model.items;

public class Bar extends Item{
    private final BarType type;
    public Bar(String name, BarType type) {
        super(name);
        this.type = type;
    }
}