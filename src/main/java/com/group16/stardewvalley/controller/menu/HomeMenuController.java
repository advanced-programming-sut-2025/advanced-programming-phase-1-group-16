package com.group16.stardewvalley.controller.menu;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.model.user.Player;

import java.util.Set;

public class HomeMenuController {

    public Result putItemInRefrigerator(String input){
        if(input == null || input.isEmpty()){
            return new Result(false, "empty input!");
        }
        Ingredient ingredient = findIngredient(input);
        if(ingredient == null){
            return new Result(false, "You can't put an uneatable item in refrigerator!");
        }
        FoodIngredient foodIngredient = App.getActiveGame().getCurrentPlayer().getInventory().getFoodIngredient(ingredient);
        if (foodIngredient == null) {
            return new Result(false, "You don't have this in your inventory!");
        }
        App.getActiveGame().getCurrentPlayer().getFarm().addToRefrigerator(foodIngredient, 1);
        return new Result(true, "You put an item in refrigerator!");
    }

    public Result pickItemInRefrigerator(String input){
        if(input == null || input.isEmpty()){
            return new Result(false, "empty input!");
        }
        Ingredient ingredient = findIngredient(input);
        if(ingredient == null){
            return new Result(false, "You can't put an uneatable item in refrigerator!");
        }
        FoodIngredient foodIngredient = App.getActiveGame().getCurrentPlayer().getFarm().getIngredientInRef(ingredient);
        if (foodIngredient == null) {
            return new Result(false, "You don't have this in your refrigerator!");
        }
        App.getActiveGame().getCurrentPlayer().getInventory().getItems().put(foodIngredient, App.getActiveGame().getCurrentPlayer().getFarm().getRefrigerator().get(foodIngredient));
        App.getActiveGame().getCurrentPlayer().getFarm().getRefrigerator().remove(foodIngredient);
        return new Result(true, "You pick an item in refrigerator!");
    }

    public Result showRecipeOfFood() {
        Set<Food> foods = App.getActiveGame().getCurrentPlayer().getKnownRecipes();
        if (foods == null || foods.isEmpty()){
            return new Result(false, "You don't have any known recipes!");
        }
        StringBuilder output = new StringBuilder();
        for (Food food : foods) {
            output.append(food.getName()).append(" Recipe: ").append(food.getFormattedRecipe());
        }
        return new Result(true, output.toString());
    }

    public Result cooking(String foodName) {
        Food food = getFoodByName(foodName);
        if (food == null) {
            return new Result(false, "You don't know this food recipe!");
        }
        if (!haveIngredient(food)) {
            return new Result(false, "You don't have this food Ingredients!");
        }
        if (!App.getActiveGame().getCurrentPlayer().hasEnoughEnergy(3)) {
            return new Result(false, "You don't have enough energy!");
        }
        reduceIngredient(food);
        App.getActiveGame().getCurrentPlayer().decreaseEnergy(3);
        return App.getActiveGame().getCurrentPlayer().getInventory().addItem(new Food(food), 1);
    }

    public Result eat(String foodName) {
        Food food = App.getActiveGame().getCurrentPlayer().getInventory().getFood(foodName);
        if (food == null) {
            return new Result(false, "You don't have this food in your inventory!");
        }
        Player player = App.getActiveGame().getCurrentPlayer();
        player.getInventory().getItems().remove(foodName);
        player.increaseEnergy(food.getEnergy());
        return App.getActiveGame().getCurrentPlayer().getInventory().addItem(new Food(food), 1);
    }

    private void reduceIngredient(Food food) {
        for (Ingredient ingredient : food.getIngredients().keySet()) {
            FoodIngredient foodIngredient = App.getActiveGame().getCurrentPlayer().getInventory().getFoodIngredient(ingredient);
            App.getActiveGame().getCurrentPlayer().getInventory().getItems().put(foodIngredient, -1);
        }

    }

    private boolean haveIngredient(Food food) {
        for (Ingredient ingredient : food.getIngredients().keySet()) {
            FoodIngredient foodIngredient = App.getActiveGame().getCurrentPlayer().getInventory().getFoodIngredient(ingredient);
            if (foodIngredient == null) {
                return false;
            }
        }
        return true;
    }

    private Food getFoodByName(String name) {
        for (Food food : App.getActiveGame().getCurrentPlayer().getKnownRecipes()) {
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


    public Result exitMenu(){
        App.setCurrentMenu(Menu.GameMenu);
        return new Result(true, "you are in the game menu!");
    }
}
