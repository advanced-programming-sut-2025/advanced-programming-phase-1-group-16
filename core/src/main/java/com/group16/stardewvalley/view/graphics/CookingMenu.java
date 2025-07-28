package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.group16.stardewvalley.controller.menu.HomeMenuController;
import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodFactory;
import com.group16.stardewvalley.model.graphics.GameAssetManager;

import java.util.List;
import java.util.Set;

public class CookingMenu extends Window {
    private final Skin skin;
    private final Set<Food> knownRecipes;
    private final Inventory inventory;

    public CookingMenu(Skin skin, Set<Food> knownRecipes, Inventory inventory) {
        super("Cooking Menu", skin);
        this.skin = skin;
        this.knownRecipes = knownRecipes;
        this.inventory = inventory;

        setSize(800, 600);
        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - getHeight() / 2f);

        createFoodGrid();
    }

    private void createFoodGrid() {
        Table grid = new Table();
        grid.defaults().pad(10);

        List<Food> allFoods = FoodFactory.getAllFoods();
        int cols = 5;
        int count = 0;

        for (Food food : allFoods) {
            boolean isKnown = knownRecipes.contains(food);
            Texture texture = GameAssetManager.getGameAssetManager().getFoodTexture(food);
            Image img = new Image(texture);

            if (!isKnown) {
                img.setColor(0.3f, 0.3f, 0.3f, 0.7f);
            }

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = img.getDrawable();
            ImageButton btn = new ImageButton(style);

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!isKnown) return;

                    HomeMenuController controller = new HomeMenuController();
                    Result result = controller.cooking(food.getName());
                    if (result.isSuccessful()) {
                        System.out.println("✅ Cooked: " + food.getName());
                    } else {
                        System.out.println("❌ " + result.message());
                    }
                }
            });

            grid.add(btn).size(64);
            count++;
            if (count % cols == 0) grid.row();
        }

        ScrollPane scroll = new ScrollPane(grid, skin);
        add(scroll).expand().fill();
    }
}


