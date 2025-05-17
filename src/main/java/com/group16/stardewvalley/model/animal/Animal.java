package com.group16.stardewvalley.model.animal;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.shops.MarniesRanchAnimals;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.shops.BuildingType;
import com.group16.stardewvalley.model.user.Player;

public class Animal extends Item {
    private final AnimalType animalType;
    private Pos animalPos;
    private AnimalProductTypes todayProduct;
    private Integer friendship;
    private boolean isFeed = false;
    private boolean isPet = false;
    private boolean isOut;
    private boolean isAnimalStayOutAllNight = false;
    private boolean haveFedWithHayToday = false;
    private Player owner;
    private final BuildingType buildingRequired;

    private boolean havePickedProducts = false;

    public Animal(AnimalType animalType,
                  String name,
                  Player owner,
                  BuildingType buildingRequired) {
        super(name);
        this.animalType = animalType;
        this.owner = owner;
        this.friendship = 0;
        this.buildingRequired = buildingRequired;
    }

    public BuildingType getBuildingRequired() {
        return buildingRequired;
    }

    public Result increaseFriendship(int amount) {
        if(this.friendship + amount < 1000) {
            this.friendship += amount;
        }else  {
            this.friendship = 1000;
        }

        return new Result(true, "friendship increased");
    }







    //getter and setter


    public boolean haveFedWithHayToday() {
        return haveFedWithHayToday;
    }

    public void setHaveFedWithHayToday(boolean haveFedWithHayToday) {
        this.haveFedWithHayToday = haveFedWithHayToday;
    }

    public boolean havePickedProducts() {
        return havePickedProducts;
    }

    public void setHavePickedProducts(boolean havePickedProducts) {
        this.havePickedProducts = havePickedProducts;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setIsOut(boolean out) {
        isOut = out;
    }

    public Pos getAnimalPos() {
        return animalPos;
    }

    public void setAnimalPos(Pos animalPos) {
        this.animalPos = animalPos;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public AnimalProductTypes getTodayProduct() {
        return todayProduct;
    }

    public Integer getFriendship() {
        return friendship;
    }

    public boolean isFeed() {
        return isFeed;
    }

    public void setIsFeed(boolean feed) {
        isFeed = feed;
    }

    public boolean isPet() {
        return isPet;
    }

    public void setIsPet(boolean isPet) {
        this.isPet = isPet;
    }

    public String getName() {return getName();
    }

    public boolean getAnimalStayOutAllNight() {
        return isAnimalStayOutAllNight;
    }

    public Player getOwner() {
        return owner;
    }

    public void setTodayProduct(AnimalProductTypes todayProduct) {
        this.todayProduct = todayProduct;
    }

    public void setFriendship(Integer friendship) {
        this.friendship = friendship;
    }

    public void setAnimalStayOutAllNight(boolean animalStayOutAllNight) {
        isAnimalStayOutAllNight = animalStayOutAllNight;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}