package com.group16.stardewvalley.model.animal;

public class AnimalProduct {

    AnimalProductTypes productType;
    ProductQuality productQuality;

    public AnimalProduct(AnimalProductTypes productType, ProductQuality productQuality) {
        this.productType = productType;
        this.productQuality = productQuality;
    }

    public AnimalProductTypes getProductType() {
        return productType;
    }

    public ProductQuality getProductQuality() {
        return productQuality;
    }
}