package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.map.Farm;
import com.group16.stardewvalley.model.map.Pos;

public class Player {
    private User user;
    private Farm farm;
    private int energy;
    private Pos position;
    private Inventory playerInventory;
    private Gadget currentEquipment;

    public Player(User user) {
        this.user = user;
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
