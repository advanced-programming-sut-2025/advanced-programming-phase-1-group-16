package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.map.Farm;

public class Player {
    private User user;
    private Farm farm;

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Farm getFarm() {
        return farm;
    }

}
