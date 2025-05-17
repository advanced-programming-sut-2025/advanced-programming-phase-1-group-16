package com.group16.stardewvalley.controller;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Fertilizer;
import com.group16.stardewvalley.model.agriculture.FertilizerType;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodFactory;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.tools.*;

import javax.swing.text.Position;

public class CheatCodeController {
    public Result addTool(String tool) {
        switch (tool) {
            case "axe":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new Axe("axe", "base"), 1);
                break;
            case "hoe":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new Hoe("hoe", "base"), 1);
                break;
            case "milk pail":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new MilkPail("milk pail"), 1);
                break;
            case "scythe":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new Scythe("scythe", "base"), 1);
                break;
            case "pickaxe":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new Pickaxe("pickaxe", "base"), 1);
                break;
            case "shear":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new Shear("shear"), 1);
                break;
            case "watering can":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new WateringCan("watering can", "base"), 1);
                break;
            default:
                return new Result(false, "Unknown tool");
        }
        return new Result(true, "added tool");
    }

    public Result addFertilizer(String fertilizer) {
        switch (fertilizer) {
            case "speed":
                Fertilizer fertilizer1 = new Fertilizer("speed gro", FertilizerType.SPEED_GRO);
                App.getActiveGame().getCurrentPlayer().getInventory().addItem(fertilizer1, 1);
                break;
            case "deluxe":
                Fertilizer fertilizer2 = new Fertilizer("deluxe retaining soil", FertilizerType.DELUXE_RETAINING_SOIL);
                App.getActiveGame().getCurrentPlayer().getInventory().addItem(fertilizer2, 1);
                break;
        }
        return new Result(true, "added fertilizer");
    }

    public Result showPosition() {
        Pos position = App.getActiveGame().getCurrentPlayer().getPosition();
        return new Result(true, position.toString());
    }

    public Result addIngredient(String name) {
        Ingredient ingredient = findIngredient(name);
        if (ingredient == null) {
            return new Result(false, "Ingredient not found");
        }
        FoodIngredient foodIngredient = new FoodIngredient(name, ingredient);
        App.getActiveGame().getCurrentPlayer().getInventory().addItem(foodIngredient, 1);
        return new Result(true, "added ingredient");
    }

    public Result learnRecipe(String foodName) {
        Food food = getFoodByName(foodName);
        if (food == null) {
            return new Result(false, "Food not found");
        }
        App.getActiveGame().getCurrentPlayer().learnRecipe(food);
        return new Result(true, "learned recipe");
    }

    public Result cookFood(String foodName) {
        Food food = getFoodByName(foodName);
        if (food == null) {
            return new Result(false, "Food not found");
        }
        App.getActiveGame().getCurrentPlayer().getInventory().addItem(food, 1);
        return new Result(true, "cooked food");
    }

    private Food getFoodByName(String name) {
        for (Food food : FoodFactory.getAllFoods()) {
            if (food.getName().equals(name)) {
                return food;
            }
        }
        return null;
    }

    private Ingredient findIngredient(String input){
        for (Ingredient ingredient : Ingredient.values()) {
            if (ingredient.getName().equals(input)) {
                return ingredient;
            }
        }
        return null;
    }
}
