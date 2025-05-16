package com.group16.stardewvalley.model.animal;


import com.group16.stardewvalley.model.items.Item;

public class Fish  extends Item {
    private FishType fishType;
    private Integer containingEnergy = 20; //TODO Energy not found
    private ProductQuality productQuality;

    public int getSellPrice() {
        return (int) (this.fishType.getPrice() * productQuality.());
    }

    public Fish(FishType fishType, String name) {
        super(name);
        this.fishType = fishType;
    }
    public Fish(String name, FishType fishType, ProductQuality productQuality) {
        super(name);
        this.fishType = fishType;
        this.productQuality = productQuality;
    }


}