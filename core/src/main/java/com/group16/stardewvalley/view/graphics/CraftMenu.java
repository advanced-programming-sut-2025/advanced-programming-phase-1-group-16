package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.group16.stardewvalley.controller.CheatCodeController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.crafting.Crafting;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;

import java.util.ArrayList;
import java.util.List;

public class CraftMenu extends Window {
    private final Skin skin;
    private ArrayList<CraftingRecipes> knownCraftingRecipes;
    private final Window tooltip;
    private Crafting craftingController = new Crafting();
    private CheatCodeController CheatController = new CheatCodeController();

    public CraftMenu(Skin skin, ArrayList<CraftingRecipes> knownCraftingRecipes){
        super("Cooking Menu", skin);
        this.skin = skin;
        this.knownCraftingRecipes = knownCraftingRecipes;


        tooltip = new com.badlogic.gdx.scenes.scene2d.ui.Window("Crafting", skin);
        tooltip.setMovable(true);
        tooltip.setVisible(false);
        tooltip.setKeepWithinStage(true);
        tooltip.pad(10);

        setSize(800, 600);
        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - getHeight() / 2f);

        craftGrid();
    }


    private void craftGrid() {
        Table grid = new Table();
        grid.defaults().pad(10);

        List<CraftingRecipes> allCraftingItems = List.of(CraftingRecipes.values());
        int cols = 5;
        int count = 0;
        knownCraftingRecipes = App.getActiveGame().getCurrentPlayer().getInventory().getCraftingRecipes();

        for (CraftingRecipes craftItem : allCraftingItems) {
            boolean isKnown = isKnownRecipe(craftItem);
            Texture texture = GameAssetManager.getGameAssetManager().getCraftingTexture(craftItem);
            Image img = new Image(texture);

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = img.getDrawable();
            ImageButton btn = new ImageButton(style);

            if (!isKnown) {
                btn.getImage().setColor(0.3f, 0.3f, 0.3f, 0.7f);
            }

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!isKnown) return;

                    for(Ingredient ingredient: craftItem.getNeededIngredients().keySet()){
                        CheatController.addIngredient(ingredient.getName());
                    }
                    Result result = craftingController.craft(craftItem.getName());
                    if(result.isSuccessful()){
                        System.out.println("DONE -- crafted  " + craftItem.getName());
                    }else {
                        System.out.println(result.message());
                    }
                }
            });

            btn.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    tooltip.clear();

                    Table content = getDescriptionTable(food);

                    tooltip.add(content).pad(50);
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


    private boolean isKnownRecipe(CraftingRecipes craftItem) {
        for (CraftingRecipes f : knownCraftingRecipes) {
            if (f.equals(craftItem)) {
                return true;
            }
        }
        return false;
    }
}
