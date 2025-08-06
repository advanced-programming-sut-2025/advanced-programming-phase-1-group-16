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
import com.group16.stardewvalley.controller.shops.ShopController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.shops.CarpentersShop;
import com.group16.stardewvalley.model.user.Player;

import java.util.*;
import java.util.List;

public class CarpenterMenu extends Window {
    private final Skin skin;
    private final Window tooltip;
    private Set<Item> availableItems;
    private Player player;
    private CarpentersShop shop = new CarpentersShop();
    private ShopController shopController = new ShopController();
    private final Map<String, Texture> carpenterTextures = new HashMap<>();

    private final Map<Item, Table> tooltipCache = new HashMap<>();
    private Label resultLabel;


    public CarpenterMenu(Skin skin) {
        super("Carpenter's Shop", skin);
        this.skin = skin;
        this.availableItems = shop.getAvailableItems();
        this.player = App.getActiveGame().getCurrentPlayer();

        tooltip = new com.badlogic.gdx.scenes.scene2d.ui.Window("", skin);
        tooltip.setMovable(true);
        tooltip.setVisible(false);
        tooltip.setKeepWithinStage(true);
        tooltip.pad(10);

        setSize(800, 600);
        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - getHeight() / 2f);

        carpenterGrid();
    }
    private void carpenterGrid() {
        Table grid = new Table();
        grid.defaults().pad(10);

        Set<Item> allProducts = shop.getAllProducts();
        int cols = 4;
        int count = 0;
        availableItems = shop.getAvailableItems();

        // First, put wood and stone explicitly at the beginning
        List<String> priorityItems = Arrays.asList("Wood", "Stone");

        List<Item> sortedItems = new ArrayList<>();

        // Add priority items first
        for (String name : priorityItems) {
            for (Item item : allProducts) {
                if (item.getName().equalsIgnoreCase(name)) {
                    sortedItems.add(item);
                    break; // prevent duplicates if multiple items have same name
                }
            }
        }

        // Add the rest of the items (excluding already added ones)
        for (Item item : allProducts) {
            if (!priorityItems.contains(item.getName())) {
                sortedItems.add(item);
            }
        }

        for (Item item : sortedItems) {
            boolean isAvailable = isAvailableProduct(item);
            Texture texture = getTexture(item);
            Image img = new Image(texture);

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = img.getDrawable();
            ImageButton btn = new ImageButton(style);

            if (!isAvailable) {
                btn.getImage().setColor(0.3f, 0.3f, 0.3f, 0.7f);
            }

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!isAvailable) return;

                    Result result = shop.buildCoop_Barn(item.getName(), player.getX(), player.getY());
                    if (result.isSuccessful()) {
                        System.out.println("DONE -- built  " + item.getName());
                    } else {
                        System.out.println(result.message());
                    }
                    resultLabel.setText(result.message());
                }
            });

            btn.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    tooltip.clear();
                    Table content = tooltipCache.computeIfAbsent(item, itemKey -> getDescriptionTable(itemKey));
                    tooltip.add(content).pad(50);
                    tooltip.pack();
                    tooltip.setVisible(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    tooltip.setVisible(false);
                }
            });

            // Make Wood and Stone larger
            if (item.getName().equalsIgnoreCase("Wood") || item.getName().equalsIgnoreCase("Stone")) {
                grid.add(btn).size(96);
            } else {
                grid.add(btn).size(64);
            }

            count++;
            if (count % cols == 0) grid.row();
        }

        ScrollPane scroll = new ScrollPane(grid, skin);

        clear();
        add(scroll).expand().fill().row();

        resultLabel = new Label("", skin);
        resultLabel.setColor(Color.LIGHT_GRAY);
        resultLabel.setWrap(true);
        add(resultLabel).padBottom(15).padLeft(200).fillX().height(50);
    }

//    private void carpenterGrid() {
//        Table grid = new Table();
//        grid.defaults().pad(10);
//
//        Set<Item> allProducts = shop.getAllProducts();
//        int cols = 4;
//        int count = 0;
//        availableItems = shop.getAvailableItems();
//
//        for (Item item : allProducts) {
//            boolean isAvailable = isAvailableProduct(item);
//            Texture texture = getTexture(item);
//            Image img = new Image(texture);
//
//            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
//            style.imageUp = img.getDrawable();
//            ImageButton btn = new ImageButton(style);
//
//            if (!isAvailable) {
//                btn.getImage().setColor(0.3f, 0.3f, 0.3f, 0.7f);
//            }
//
//            btn.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    if (!isAvailable) return;
//
////                    for(Ingredient ingredient: craftItem.getNeededIngredients().keySet()){
////                        CheatController.addIngredient(ingredient.getName());
////                    }
//                    Result result = shop.buildCoop_Barn(item.getName(), player.getX(), player.getY());
//                    if(result.isSuccessful()){
//                        System.out.println("DONE -- built  " + item.getName());
//                    }else {
//                        System.out.println(result.message());
//                    }
//                    resultLabel.setText(result.message());
//                }
//            });
//
//            btn.addListener(new InputListener() {
//                @Override
//                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                    tooltip.clear();
//
//                    Table content = tooltipCache.computeIfAbsent(item, item -> getDescriptionTable(item));
//
//                    tooltip.add(content).pad(50);
//                    tooltip.pack();
//                    tooltip.setVisible(true);
//                }
//
//
//                @Override
//                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                    tooltip.setVisible(false);
//                }
//            });
//
//            grid.add(btn).size(96);
//            count++;
//            if (count % cols == 0) grid.row();
//        }
//
//        ScrollPane scroll = new ScrollPane(grid, skin);
//
//        // Clear any previous children from the window and start clean layout
//        clear(); // clear previous layout children (like multiple scrolls if called again)
//
//        add(scroll).expand().fill().row(); // put scrollpane and go to next row
//
//        resultLabel = new Label("", skin);
//        resultLabel.setColor(Color.LIGHT_GRAY);
//        resultLabel.setWrap(true); // wrap text for long messages
//
//        add(resultLabel).padBottom(15).padLeft(200).fillX().height(50);
//
//    }


    private Table getDescriptionTable(Item item) {
        Table content = new Table();
        content.defaults().left().padBottom(5);

        Label title = new Label(item.getName(), skin, "button");
        title.setColor(Color.ORANGE);
        content.add(title).row();

        Label buyingLabel = new Label("Buy", skin);
        buyingLabel.setColor(Color.SALMON);
        content.add(buyingLabel).row();

        content.add(new Label("------------------------", skin)).row();

        //TODO

        return content;
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        if (tooltip.isVisible()) {
            Vector2 mousePos = Main.getMain().getGameScreen().getStage().screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            tooltip.setPosition(mousePos.x + 10, mousePos.y - 10);
        }
    }

    public Window getTooltip() {
        return tooltip;
    }


    private boolean isAvailableProduct(Item item) {
        for (Item i : availableItems) {
            if (i.getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }

    public Texture getTexture(Item item) {
        String name = item.getName().replace(" ", "_");
        if (!carpenterTextures.containsKey(name)) {
            try {
                Texture texture = new Texture("Shops/Carpenter/" + name + ".png");
                carpenterTextures.put(name, texture);
            } catch (Exception e) {
                carpenterTextures.put(name, GameAssetManager.getGameAssetManager().getBasicItemTexture());
            }
        }
        return carpenterTextures.get(name);
    }
}
