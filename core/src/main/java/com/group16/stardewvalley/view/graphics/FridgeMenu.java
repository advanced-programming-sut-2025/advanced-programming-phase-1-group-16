package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;

import java.util.HashMap;
import java.util.Map;

public class FridgeMenu extends Window {
    private final Skin skin;
    private final HashMap<FoodIngredient, Integer> refrigerator;

    private final Window tooltip;

    public FridgeMenu(Skin skin, HashMap<FoodIngredient, Integer> refrigerator) {
        super("Fridge", skin);
        this.skin = skin;
        this.refrigerator = refrigerator;

        tooltip = new Window("", skin);
        tooltip.setMovable(false);
        tooltip.setVisible(false);
        tooltip.setKeepWithinStage(true);
        tooltip.pad(10);

        setSize(600, 500);
        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - getHeight() / 2f);

        createIngredientGrid();
    }

    private void createIngredientGrid() {
        Table grid = new Table();
        grid.defaults().pad(10);

        int cols = 5;
        int count = 0;

        for (Map.Entry<FoodIngredient, Integer> entry : refrigerator.entrySet()) {
            FoodIngredient ingredient = entry.getKey();
            int quantity = entry.getValue();

            Texture texture = GameAssetManager.getGameAssetManager().getIngredientTexture(ingredient.getType());
            Image img = new Image(texture);

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = img.getDrawable();
            ImageButton btn = new ImageButton(style);

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // اینجا می‌توانی منطق استفاده از آیتم یا انتقالش به موجودی بازیکن رو بگذاری
                    System.out.println("✅ Selected from fridge: " + ingredient.getName() + " x" + quantity);
                }
            });

            btn.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    tooltip.clear();
                    Table content = getDescriptionTable(ingredient, quantity);
                    tooltip.add(content).pad(20);
                    tooltip.pack();
                    tooltip.setVisible(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    tooltip.setVisible(false);
                }
            });

            grid.add(btn).size(64);
            count++;
            if (count % cols == 0) grid.row();
        }

        ScrollPane scroll = new ScrollPane(grid, skin);
        add(scroll).expand().fill();
    }

    private Table getDescriptionTable(FoodIngredient ingredient, int quantity) {
        Table content = new Table();
        content.defaults().left().padBottom(5);

        Label title = new Label(ingredient.getName(), skin, "button");
        title.setColor(Color.GOLD);
        content.add(title).row();

        content.add(new Label("------------------------", skin)).row();

        Table qtyRow = new Table();
        qtyRow.add(new Label("Quantity: " + quantity, skin)).left();
        content.add(qtyRow).left().row();

        Table priceRow = new Table();
        priceRow.add(new Label("Sell Price: " + ingredient.getPrice(), skin)).left();
        content.add(priceRow).left().row();

        return content;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (tooltip.isVisible()) {
            Vector2 mousePos = Main.getMain().getGameScreen().getStage()
                    .screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            tooltip.setPosition(mousePos.x + 10, mousePos.y - 10);
        }
    }

    public Window getTooltip() {
        return tooltip;
    }
}
