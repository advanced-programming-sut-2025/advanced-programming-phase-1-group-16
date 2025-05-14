package com.group16.stardewvalley.model.map;


import com.group16.stardewvalley.model.agriculture.Crop;
import com.group16.stardewvalley.model.agriculture.Tree;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;

public class Farm {
    private FarmType type;
    private Pos startPosition;
    private HashMap<FoodIngredient, Integer> refrigerator;
    private ArrayList<Crop> plantedCrops;
    private ArrayList<Tree> plantedTrees;
    private ArrayList<Crop> greenHouseCrops;
    private ArrayList<Tree> greenHouseTrees;

    public Farm(FarmType type) {
        this.type = type;
        this.refrigerator = new HashMap<>();
        this.plantedCrops = new ArrayList<>();
        this.plantedTrees = new ArrayList<>();
        this.greenHouseCrops = new ArrayList<>();
        this.greenHouseTrees = new ArrayList<>();
    }

    public void addPlantedCrop(Crop crop) {
        plantedCrops.add(crop);
    }

    public void addPlantedTree(Tree tree) {
        plantedTrees.add(tree);
    }

    public void addGreenHouseCrop(Crop crop) {
        greenHouseCrops.add(crop);
    }

    public void addGreenHouseTree(Tree tree) {
        greenHouseTrees.add(tree);
    }

    public ArrayList<Crop> getPlantedCrops() {
        return plantedCrops;
    }

    public ArrayList<Tree> getGreenHouseTrees() {
        return greenHouseTrees;
    }

    public ArrayList<Crop> getGreenHouseCrops() {
        return greenHouseCrops;
    }

    public ArrayList<Tree> getPlantedTrees() {
        return plantedTrees;
    }

    public HashMap<FoodIngredient, Integer> getRefrigerator() {
        return refrigerator;
    }

    public FoodIngredient getIngredientInRef(Ingredient ingredient) {
        for (FoodIngredient food : refrigerator.keySet()) {
            if (food.getType().equals(ingredient)) {
                return food;
            }
        }
        return null;
    }

    public void setRefrigerator(HashMap<FoodIngredient, Integer> refrigerator) {
        this.refrigerator = refrigerator;
    }

    public void addToRefrigerator(FoodIngredient ingredient, int amount) {
        refrigerator.put(ingredient, amount);
    }

    public FarmType getType() {
        return type;
    }

    public void setType(FarmType type) {
        this.type = type;
    }

    public Pos getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Pos startPosition) {
        this.startPosition = startPosition;
    }
}
