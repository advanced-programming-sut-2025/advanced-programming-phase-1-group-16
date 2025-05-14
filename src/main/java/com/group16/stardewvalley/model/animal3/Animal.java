package com.group16.stardewvalley.model.animal3;

import com.group16.stardewvalley.model.user.Player;

import java.util.Random;

public class Animal  {
    private AnimalType animalType;
    private model.products.AnimalProduct todayProduct;
    private Integer relationShipQuality = 0;
    private Boolean isFeed = false;
    private Boolean pet = false;
    private final String name;
    private Boolean isAnimalStayOutAllNight = false;
    private Player owner;

    public Animal(AnimalType animalType,String name) {
        this.animalType = animalType;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getContainingEnergy() {return 0;}

    @Override
    public int getSellPrice() {
        return this.animalType.getSellPrice();
    }

    public void produceAnimalProduct(){
        if (!this.isFeed){
            this.todayProduct = null;
            return;
        }
        double quality = generateQuality();
        model.products.AnimalProductType animalProductType = this.animalType.getProductList().get(0);
        if (canHaveSecondProduct()){
            animalProductType = this.animalType.getProductList().get(1);
        }
        model.products.AnimalProduct animalProduct = new model.products.AnimalProduct(animalProductType);
        animalProduct.setProductQuality(ProductQuality.getQualityByDouble(quality));
        this.todayProduct = animalProduct;
    }

    public void changeFriendShip(int value) {
        int oldValue = this.relationShipQuality;
        this.setRelationShipQuality(Math.min(1000,Math.max(0,oldValue + value)));
    }

    private double generateQuality(){
        Random random = new Random();
        double R = random.nextDouble(0,1);
        return ((double) this.relationShipQuality / 1000) * (0.5 + 0.5 * R);
    }

    private boolean canHaveSecondProduct(){
        if (this.relationShipQuality < 100){
            return false;
        }
        if (this.animalType.getProductList().size() != 2){
            return false;
        }
        Random random = new Random();
        double R = random.nextDouble(0.5,1.5);
        double probability = (this.relationShipQuality + (150 * R)) / 1500;
        return probability > 0.5;
    }