package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.group16.stardewvalley.controller.agriculture.AgricultureController;
import com.group16.stardewvalley.model.agriculture.Crop;
import com.group16.stardewvalley.model.agriculture.Mineral;
import com.group16.stardewvalley.model.agriculture.Tree;
import com.group16.stardewvalley.model.agriculture.TreeType;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.items.Stone;

import java.util.HashMap;
import java.util.Map;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;

    private final String crop = "Foraging/Grape.png";
    private final String tree = "Trees/Pine_Stage_4.png";
    private final String item = "Crafting/Stone.png";
    private final String burn = "Flooring/Flooring_33.png";
    private final String water = "Flooring/Flooring_47.png";
    private final String fertalize = "Fertilizer/Stardew-texture_Basic-Fertilizer.png";

    private Texture cropTexture = new Texture(crop);
    private Texture treeTexture = new Texture(tree);
    private Texture itemTexture = new Texture(item);
    private Texture burnTexture = new Texture(burn);
    private Texture waterTexture = new Texture(water);
    private Texture fertalizeTexture = new Texture(fertalize);

    private final Map<String, TextureRegion> treeRegions = new HashMap<>();
    private final Map<String, TextureRegion> cropRegions = new HashMap<>();
    private final Map<String, Texture> mineralTextures = new HashMap<>();
    private final Map<String, Texture> stoneTextures = new HashMap<>();

    private final Texture houseTexture = new Texture("House/House_1.png");



    //private final Music backgroundMusic;
    //private final Animation<Texture> character1_idle_frames = new Animation<>(0.1f, character1_idle0_tex);


    private GameAssetManager(){
        /*
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("musics/alex-productions-epic-cinematic-gaming-cyberpunk-reset(chosic.com).mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
         */
    }

    public static GameAssetManager getGameAssetManager(){
        if (gameAssetManager == null){
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public TextureRegion getCropRegion(Crop crop) {
        String name = crop.getCropType().getName().replace(" ", "_");
        int stage = crop.getStage();
        String key = name + "_stage_" + stage;

        if (!cropRegions.containsKey(key)) {
            try {
                Texture texture = new Texture("Crops/" + name + "_Stage_" + stage + ".png");
                TextureRegion region = new TextureRegion(texture);
                cropRegions.put(key, region);
            } catch (Exception e) {
                cropRegions.put(key, new TextureRegion(cropTexture));
            }
        }
        return cropRegions.get(key);
    }


    public TextureRegion getTreeRegion(Tree tree) {
        String name = tree.getTreeType().getName().replace(" ", "_");
        int stage = tree.getStage();
        String season = App.getActiveGame().getTimeDate().getSeason().getName().toLowerCase();
        String fruitState = tree.HasFruit() ? "Fruit" : "";
        String key = name + "_stage_" + stage + "_season_" + season + fruitState;

        if (!treeRegions.containsKey(key)) {
            try {
                if (stage != 5) {
                    Texture texture = new Texture("Trees/" + name + "_Stage_" + stage + ".png");
                    TextureRegion region = new TextureRegion(texture);
                    treeRegions.put(key, region);
                } else {
                    if (tree.HasFruit()) {
                        try {
                            Texture texture = new Texture("Trees/" + name + "_Stage_5_Fruit.png");
                            treeRegions.put(key, new TextureRegion(texture));
                        } catch (Exception e) {
                            TextureRegion region = getTextureRegion(name, season);
                            treeRegions.put(key, region);
                        }
                    }
                    else if (AgricultureController.isTreeNotSeasonal(tree.getTreeType())) {
                        Texture texture = new Texture("Trees/" + name + "_Stage_5.png");
                        treeRegions.put(key, new TextureRegion(texture));
                    } else {
                        TextureRegion region = getTextureRegion(name, season);
                        treeRegions.put(key, region);
                    }
                }
            } catch (Exception e) {
                treeRegions.put(key, new TextureRegion(treeTexture));
            }
        }

        return treeRegions.get(key);
    }

    private static TextureRegion getTextureRegion(String name, String season) {
        Texture fullTexture = new Texture("Trees/" + name + "_Stage_5.png");

        int treeWidth, treeHeight;

        treeWidth = fullTexture.getWidth() / 4;
        treeHeight = fullTexture.getHeight();

        int seasonIndex = switch (season) {
            case "summer" -> 1;
            case "fall" -> 2;
            case "winter" -> 3;
            default -> 0;
        };

        return new TextureRegion(
            fullTexture,
            seasonIndex * (treeWidth + 2),
            0,
            treeWidth,
            treeHeight
        );
    }


    public Texture getItemTexture(Item item) {
        if (item instanceof Mineral mineral) {
            String name = mineral.getType().getName().replace(" ", "_");
            if (!mineralTextures.containsKey(name)) {
                try {
                    Texture texture = new Texture("Mineral/" + name + ".png");
                    mineralTextures.put(name, texture);
                } catch (Exception e) {
                    mineralTextures.put(name, itemTexture);
                }
            }
            return mineralTextures.get(name);
        }
        else if (item instanceof Stone stone) {
            String name = stone.getName();
            if (name.equals("Farm_Boulder.png")) {
                if (!stoneTextures.containsKey(name)) {
                    stoneTextures.put(name, new Texture("Rock/Farm_Boulder.png"));
                }
                return stoneTextures.get(name);
            }
            if (!stoneTextures.containsKey(name)) {
                try {
                    Texture texture = new Texture("Rock/Stone_Index" + name + ".png");
                    stoneTextures.put(name, texture);
                } catch (Exception e) {
                    stoneTextures.put(name, itemTexture);
                }
            }
            return stoneTextures.get(name);
        }
        return itemTexture;
    }


    public Texture getBurnTexture() {
        return burnTexture;
    }

    public Texture getWaterTexture() {
        return waterTexture;
    }

    public Texture getFertalizeTexture() {
        return fertalizeTexture;
    }

    public Texture getHouseTexture() {
        return houseTexture;
    }

    public void dispose() {

    }

}


