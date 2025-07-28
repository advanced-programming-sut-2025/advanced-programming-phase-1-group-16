package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;

public class FoodItemUI extends Table {
    public FoodItemUI(Food food, Inventory inventory, Skin skin) {
        super(skin);
        Image foodIcon = new Image(GameAssetManager.getGameAssetManager().getFoodTexture(food));
        Label nameLabel = new Label(food.getName(), skin);

        boolean canCook = haveIngredient(food);

        TextButton cookBtn = new TextButton("Cook", skin);
        cookBtn.setDisabled(!canCook);
        cookBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (canCook) {
                    // پختن غذا
                }
            }
        });

        add(foodIcon).size(32, 32);
        add(nameLabel).padLeft(10).left();
        add(cookBtn).right();
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
}
