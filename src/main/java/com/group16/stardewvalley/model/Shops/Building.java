package com.group16.stardewvalley.model.Shops;

import com.group16.stardewvalley.model.items.Item;

public class Building extends Item {
    public Building(String name) {
        super(name);
    }

    private BuildingType buildingType;

    public Building(String name, BuildingType buildingType) {
        super(name);
        this.buildingType = buildingType;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
