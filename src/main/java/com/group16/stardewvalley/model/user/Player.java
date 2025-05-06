package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.map.Farm;
import com.group16.stardewvalley.model.map.Pos;

public class Player {
    private User user;
    private Farm farm;
    private Pos pos;

    public Player(User user) {
        this.user = user;
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

    public Pos getPos() {
        return pos;
    }
}
