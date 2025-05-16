package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.shops.BuildingType;

import java.util.ArrayList;

public class Building extends Item {
    public Building(String name) {
        super(name);
    }

    private BuildingType buildingType;
    private int capacity;
    private Pos startPosition;
    private ArrayList<Animal> buildingAnimals = new ArrayList<>();


    public Building(String name, BuildingType buildingType, Pos startPosition) {
        super(name);
        this.buildingType = buildingType;
        this.startPosition = startPosition;
    }

    public Pos getStartPosition() {
        return startPosition;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Animal> getBuildingAnimals() {
        return buildingAnimals;
    }

    public void setBuildingAnimals(ArrayList<Animal> buildingAnimals) {
        this.buildingAnimals = buildingAnimals;
    }

    public void addAnimal(Animal animal) {
        buildingAnimals.add(animal);
    }

    public void increaseCapacity() {
        this.capacity += 1;
    }

}