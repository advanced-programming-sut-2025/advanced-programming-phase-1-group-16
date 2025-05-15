package com.group16.stardewvalley.model.animal;


import com.group16.stardewvalley.model.items.Item;

public class Fish extends Item {
    private final FishType fishType;
    private Integer containingEnergy = 20; //TODO Energy not found
    private ProductQuality productQuality;

    public int getSellPrice() {
        return (int) (this.fishType.getPrice() * productQuality.getPriceCoefficient());
    }

    public Fish(FishType fishType, ProductQuality productQuality) {
        super(fishType.getName());
        this.fishType = fishType;
        this.productQuality = productQuality;
    }

    public ProductQuality getProductQuality() {
        return productQuality;
    }

    public void setProductQuality(ProductQuality productQuality) {
        this.productQuality = productQuality;
    }

    public Integer getContainingEnergy() {
        return containingEnergy;
    }

    public void setContainingEnergy(Integer containingEnergy) {
        this.containingEnergy = containingEnergy;
    }

    public FishType getFishType() {
        return fishType;
    }
}