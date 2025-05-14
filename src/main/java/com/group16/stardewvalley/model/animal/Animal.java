package com.group16.stardewvalley.model.animal;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.user.Player;

import java.util.Random;

public class Animal {
    private AnimalType animalType;
    private AnimalProductTypes todayProduct;
    private Integer freindship = 0;
    private Boolean isFeed = false;
    private Boolean pet = false;
    private final String name;
    private Boolean isAnimalStayOutAllNight = false;
    private Player owner;

    public Animal(AnimalType animalType, String name) {
        this.animalType = animalType;
        this.name = name;
    }


    public Result increaseFriendship(int amount) {
        if(freindship + amount < 1000) {
            freindship += amount;
        }else  {
            freindship = 1000;
        }

        return new Result(true, "friendship increased");
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public AnimalProductTypes getTodayProduct() {
        return todayProduct;
    }

    public Integer getFreindship() {
        return freindship;
    }

    public Boolean getFeed() {
        return isFeed;
    }

    public Boolean getPet() {
        return pet;
    }

    public String getName() {
        return name;
    }

    public Boolean getAnimalStayOutAllNight() {
        return isAnimalStayOutAllNight;
    }

    public Player getOwner() {
        return owner;
    }
}