package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.group16.stardewvalley.model.agriculture.Crop;
import com.group16.stardewvalley.model.agriculture.Tree;

import java.util.HashMap;
import java.util.Map;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;

    private final String character = "Character/1.png";
    private final String crop = "Foraging/Grape.png";
    private final String tree = "Trees/Pine_Stage_4.png";
    private final String item = "Crafting/Stone.png";
    private final String burn = "Flooring/Flooring_33.png";
    private final String water = "Flooring/Flooring_47.png";
    private final String fertalize = "Decor/Stepping_Stone_Path.png";

    private Texture characterTexture = new Texture(character);
    private Texture cropTexture = new Texture(crop);
    private Texture treeTexture = new Texture(tree);
    private Texture itemTexture = new Texture(item);
    private Texture burnTexture = new Texture(burn);
    private Texture waterTexture = new Texture(water);
    private Texture fertalizeTexture = new Texture(fertalize);

    private final Map<String, Texture> cropTextures = new HashMap<>();
    private final Map<String, Texture> treeTextures = new HashMap<>();

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

    public String getCharacter() {
        return character;
    }

    public Texture getCropTexture(Crop crop) {
        String name = crop.getCropType().getName().replace(" ", "_");
        if (!cropTextures.containsKey(name)) {
            try {
                Texture texture = new Texture("Crops/" + name + ".png");
                cropTextures.put(name, texture);
            } catch (Exception e) {
                cropTextures.put(name, cropTexture); // cropTexture = default one
            }
        }
        return cropTextures.get(name);
    }


    public Texture getTreeTexture(Tree tree) {
        String name = tree.getTreeType().getName().replace(" ", "_");
        if (!treeTextures.containsKey(name)) {
            try {
                Texture texture = new Texture("Trees/" + name + "_Stage_5_Spring.png");
                treeTextures.put(name, texture);
            } catch (Exception e) {
                treeTextures.put(name, treeTexture); // treeTexture = default fallback
            }
        }
        return treeTextures.get(name);
    }


    public Texture getItemTexture() {
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
        for (Texture t : cropTextures.values()) t.dispose();
        for (Texture t : treeTextures.values()) t.dispose();
        // سایر موارد...
    }

}


