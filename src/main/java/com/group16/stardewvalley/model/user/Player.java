package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.map.Farm;
import com.group16.stardewvalley.model.map.Pos;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private User user;
    private Farm farm;
    private int energy;
    private Pos position;
    private Inventory inventory;
    private Gadget currentEquipment;
    private Set<Food> knownRecipes = new HashSet<>();



    public Player(User user) {
        this.user = user;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void learnRecipe(Food food) {
        knownRecipes.add(food);
    }

    public Set<Food> getKnownRecipes() {
        return knownRecipes;
    }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Pos getPosition() {
        return position;
    }

    public void setPosition(Pos position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Farm getFarm() {
        return farm;
    }

    public void faint(){

    }

}
