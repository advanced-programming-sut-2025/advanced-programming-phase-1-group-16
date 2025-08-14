package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.crafting.CraftItem;
import com.group16.stardewvalley.model.crafting.artisan.ArtisanController;
import com.group16.stardewvalley.model.crafting.artisan.ArtisanGoodType;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.Result;

import java.util.*;
import java.util.List;

public class ArtisanMenu extends Window {

    private final Label feedbackLabel;
    private final List<String> droppedIngredients = new ArrayList<>();
    private ArtisanController artisanController = new ArtisanController();
    private final Map<String, Texture> artisanIngredientTextures = new HashMap<>();
    private ProgressBar processingBar;
    private boolean isProcessing = false;
    private long processStartTime;
    private float processDurationMillis;

    public ArtisanMenu(Skin skin, CraftItem craftItem, DragAndDrop dragAndDrop, Stage stage) {
        super("Artisan Menu", skin);

        // Close button in title bar
        this.getTitleTable().add().expandX();
        TextButton closeButton = new TextButton("X", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove(); // closes this window
            }
        });
        this.getTitleTable().add(closeButton).padRight(5);

        // Main layout for this window
        Table mainTable = new Table();
        mainTable.pad(10).top().left();

        // Progress bar setup
        processingBar = new ProgressBar(0f, 1f, 0.01f, false, skin);
        processingBar.setValue(0f);
        processingBar.setAnimateDuration(0.25f);
        processingBar.setVisible(false);

        mainTable.add(new Label("Processing Progress:", skin)).left().row();
        mainTable.add(processingBar).width(400).padBottom(10).row();


        // 1. Show recipe ingredients at the top
        Table recipeTable = new Table();
        recipeTable.defaults().pad(5);


        Map<Ingredient, Integer> recipeIngredients = ArtisanGoodType.HONEY.getIngredients();
        ArtisanGoodType artisanGoodType = ArtisanGoodType.HONEY;

        for (ArtisanGoodType type : ArtisanGoodType.values()) {
            if (type.getCraftMachine() == craftItem.getRecipe()) {
                artisanGoodType = type;
                recipeIngredients =  type.getIngredients(); // First match found
            }
        }


        recipeTable.add(new Label("Making: " + artisanGoodType.getName(), skin)).left().row();
        recipeTable.add(new Label("Required Ingredients:", skin)).left().row();

        for (Map.Entry<Ingredient, Integer> entry : recipeIngredients.entrySet()) {
            Ingredient ingredient = entry.getKey();
            int quantity = entry.getValue();

            Texture texture = getArtisanIngredientTexture(ingredient);
            Image icon = new Image(texture);
            icon.setSize(32, 32);

            Label qtyLabel = new Label("x" + quantity, skin);

            Table ingredientRow = new Table();
            ingredientRow.add(icon).size(32).padRight(5);
            ingredientRow.add(new Label(ingredient.getName(), skin)).padRight(10);
            ingredientRow.add(qtyLabel);

            recipeTable.add(ingredientRow).left().row();
        }
        mainTable.add(recipeTable).left().row();

        // 2. Drop area for ingredients
        Table dropTable = new Table(skin);
        dropTable.setBackground(new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("Inventory/InventorySlotFrame.png")))));
        dropTable.defaults().pad(5);

// This size will actually be respected in the table:
        Label dropLabel = new Label("Drop Ingredients Here", skin);
        dropLabel.setAlignment(Align.center);
        dropTable.add(dropLabel).center().expand();

        mainTable.add(dropTable)
            .size(300, 100)   // bigger size here
            .padTop(20)
            .center()
            .row();


        // 3. Feedback label
        feedbackLabel = new Label("", skin);
        feedbackLabel.setAlignment(Align.center);
        mainTable.add(feedbackLabel).padTop(10).row();

        // 4. Start Processing button
        TextButton startButton = new TextButton("Start Processing", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String artisanName = craftItem.getName();
                String[] ingredientNames = droppedIngredients.toArray(new String[0]);
                System.out.println("artisanName: " + artisanName + ", ingredientNames: " + Arrays.toString(ingredientNames) );

                Result result = artisanController.use(artisanName, ingredientNames);
                feedbackLabel.setText(result.toString());

                ArtisanGoodType artisanGoodType = ArtisanGoodType.HONEY;
                for (ArtisanGoodType type : ArtisanGoodType.values()) {
                    if (type.getCraftMachine() == craftItem.getRecipe()) {
                        artisanGoodType = type;
                    }
                }

                if (result.isSuccessful()) {
                    processStartTime = System.currentTimeMillis();
                    processDurationMillis = artisanGoodType.getProcessingTimeHours() * 3600000f;
                    isProcessing = true;
                    processingBar.setVisible(true);
                    processingBar.setValue(0f);
                }

            }
        });
        mainTable.add(startButton).padTop(15).row();
// Action buttons
        Table actionButtons = new Table();

        TextButton cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isProcessing = false;
                processingBar.setVisible(false);
                feedbackLabel.setText("Processing canceled.");
            }
        });

        TextButton fastButton = new TextButton("Fast", skin);
        fastButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isProcessing) {
                    processingBar.setValue(1f);
                    processStartTime = System.currentTimeMillis() - (long) processDurationMillis;
                    feedbackLabel.setText("Processing completed instantly.");
                }
            }
        });

        TextButton getButton = new TextButton("Get Product", skin);
        getButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = artisanController.get(craftItem.getName());
                feedbackLabel.setText(result.toString());
                if (result.isSuccessful()) {
                    isProcessing = false;
                    processingBar.setVisible(false);
                }
            }
        });

        actionButtons.add(cancelButton).width(300).padRight(10);
        actionButtons.add(fastButton).width(300).padRight(10);
        actionButtons.add(getButton).width(300);

        mainTable.add(actionButtons).padTop(10).row();

        this.add(mainTable).expand().fill();

        // 5. Drag-and-drop target for the artisan drop table
        Map<Ingredient, Integer> finalRecipeIngredients = recipeIngredients;
        dragAndDrop.addTarget(new DragAndDrop.Target(this) { // 'this' is the whole ArtisanMenu window
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload,
                                float x, float y, int pointer) {
                return payload.getObject() instanceof Item;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload,
                             float x, float y, int pointer) {
                Item droppedItem = (Item) payload.getObject();

                boolean matches = finalRecipeIngredients.keySet().stream()
                    .anyMatch(ing -> ing.getName().equalsIgnoreCase(droppedItem.getName()));

                if (matches) {
                    feedbackLabel.setText("✅ " + droppedItem.getName() + " accepted!");
                    droppedIngredients.add(droppedItem.getName());
                    App.getActiveGame().getCurrentPlayer().getInventory().removeItem(droppedItem, 1);
                } else {
                    feedbackLabel.setText("❌ This item is not part of the recipe!");
                }
            }
        });

        // Window size & position
        this.setSize(1000, 650);
        this.setMovable(true);
        this.setPosition(
            (Gdx.graphics.getWidth() - getWidth()) / 2f,
            (Gdx.graphics.getHeight() - getHeight()) / 2f
        );

        stage.addActor(this);
    }


    public Texture getArtisanIngredientTexture(Ingredient craftingItem) {
        String name = craftingItem.getName().replace(" ", "_");
        if (!artisanIngredientTextures.containsKey(name)) {
            try {
                Texture texture = new Texture("Artisan_good/" + name + ".png");
                artisanIngredientTextures.put(name, texture);
            } catch (Exception e) {
                artisanIngredientTextures.put(name, new Texture("Crafting/Stone.png"));
            }
        }
        return artisanIngredientTextures.get(name);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isProcessing) {
            float elapsed = System.currentTimeMillis() - processStartTime;
            float progress = Math.min(elapsed / processDurationMillis, 1f);
            processingBar.setValue(progress);

            if (progress >= 1f) {
                feedbackLabel.setText("Processing complete! You can collect your product.");
            }
        }
    }

}
