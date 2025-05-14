package com.group16.stardewvalley.model.animal;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.Shops.MarniesRanchAnimals;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.user.Player;

public class Animal {
    private AnimalType animalType;
    private MarniesRanchAnimals fromShopType;
    private Pos animalPos;
    private AnimalProductTypes todayProduct;
    private Integer friendship;
    private boolean isFeed = false;
    private boolean isPet = false;
    private final String name;
    private boolean isOut;
    private boolean isAnimalStayOutAllNight = false;
    private Player owner;

    public Animal(MarniesRanchAnimals fromShopType, AnimalType animalType, String name, Player owner ) {
        this.fromShopType = fromShopType;
        this.animalType = animalType;
        this.name = name;
        this.owner = owner;
        this.friendship = 0;
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


    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public Pos getAnimalPos() {
        return animalPos;
    }

    public void setAnimalPos(Pos animalPos) {
        this.animalPos = animalPos;
    }

    public MarniesRanchAnimals getFromShopType() {
        return fromShopType;
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

    public boolean getIsFeed() {
        return isFeed;
    }

    public boolean getIsPet() {
        return isPet;
    }

    public String getName() {
        return name;
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

    public void setFeed(boolean feed) {
        isFeed = feed;
    }

    public void setIsPet(boolean isPet) {
        this.isPet = isPet;
    }

    public void setAnimalStayOutAllNight(boolean animalStayOutAllNight) {
        isAnimalStayOutAllNight = animalStayOutAllNight;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}