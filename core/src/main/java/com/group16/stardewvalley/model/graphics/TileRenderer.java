package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileTextureManager;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.shops.Building;
import com.group16.stardewvalley.view.graphics.GameScreen;

import java.util.ArrayList;

public class TileRenderer {
    private final GameAssetManager textureManager;
    private static TileRenderer tileRenderer;


    public TileRenderer() {
        this.textureManager = GameAssetManager.getGameAssetManager();
    }

    public static TileRenderer getTileRenderer() {
        if (tileRenderer == null) {
            tileRenderer = new TileRenderer();
        }
        return tileRenderer;
    }




    public void renderTile(SpriteBatch batch, Tile tile, int x, int y) {
        int TILE_SIZE = GameScreen.TILE_SIZE;
        int drawX = x * TILE_SIZE;
        int drawY = y * TILE_SIZE;


        if (tile.getType().equals(TileType.CottageStartPos)) {
            Texture house = GameAssetManager.getGameAssetManager().getHouseTexture();
            int realWidth = TILE_SIZE * 10;
            int realHeight = TILE_SIZE * 10;

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(house, offsetX, offsetY, realWidth, realHeight);
        }

        if (tile.isPlowed()) {
            Texture plowedTile = TileTextureManager.getTileTextureManager().getTexture(TileType.Plowed);
            batch.draw(plowedTile, drawX, drawY, TILE_SIZE, TILE_SIZE);
        }

        if (tile.getCrop() != null) {
            if (tile.getCrop().isColossal()) {
                if (tile.getCrop().isMature()) {
                    Texture cropTexture = textureManager.getGiantCropTexture(tile.getCrop());
                    batch.draw(cropTexture, drawX, drawY, TILE_SIZE * 2 , TILE_SIZE * 2);
                } else {
                    TextureRegion cropTexture = textureManager.getCropRegion(tile.getCrop());
                    drawPlant(batch, TILE_SIZE * 2, drawX, drawY, cropTexture);
                }
            } else if (!tile.getCrop().isPartOfColossal()) {
                TextureRegion cropTexture = textureManager.getCropRegion(tile.getCrop());
                drawPlant(batch, TILE_SIZE, drawX, drawY, cropTexture);
            }
        }

//        if (tile.getItem() instanceof Building building) {
//            if (tile.isBuildingOrigin()) {
//                Texture texture = textureManager.getItemTexture(building);
//                float scale = 0.25f;
//                int drawWidth = (int)(texture.getWidth() * scale);
//                int drawHeight = (int)(texture.getHeight() * scale);
//                batch.draw(texture, drawX, drawY, drawWidth, drawHeight);
//            }
//        }
        if (tile.getItem() instanceof Building building) {
            if (tile.isBuildingOrigin()) {
                // Draw building texture
                Texture buildingTexture = textureManager.getItemTexture(building);
                float scale = 0.25f;
                int drawWidth = (int) (buildingTexture.getWidth() * scale);
                int drawHeight = (int) (buildingTexture.getHeight() * scale);
                batch.draw(buildingTexture, drawX, drawY, drawWidth, drawHeight);

                // Draw animals inside this building
                ArrayList<Animal> animals = building.getBuildingAnimals();
                if (!animals.isEmpty()) {
                    int buildingPixelWidth = building.getBuildingType().getLength() * TILE_SIZE;
                    int buildingPixelHeight = building.getBuildingType().getWidth() * TILE_SIZE;

                    int animalsPerRow = Math.max(1, building.getBuildingType().getLength() - 1);
                    int animalSize = TILE_SIZE / 2; // make animals smaller than tiles

                    for (int i = 0; i < animals.size(); i++) {
                        Animal animal = animals.get(i);
                        Texture animalTexture = textureManager.getAnimalTexture(animal);

                        // Simple fixed positioning (grid inside the building)
                        int row = i / animalsPerRow;
                        int col = i % animalsPerRow;

                        int animalX = drawX + col * animalSize + TILE_SIZE / 4;
                        int animalY = drawY + row * animalSize + TILE_SIZE / 4;

                        batch.draw(animalTexture, animalX, animalY, animalSize, animalSize);
                    }
                }
            }
        }

        else if (tile.getItem() != null) {
            Texture texture = textureManager.getItemTexture(tile.getItem());
            int realWidth = Math.min(TILE_SIZE, texture.getWidth());
            int realHeight = Math.min(TILE_SIZE, texture.getHeight());
            batch.draw(texture, drawX, drawY, realWidth, realHeight);
        }



        if (tile.getTree() != null) {
            TextureRegion treeTexture = textureManager.getTreeRegion(tile.getTree());
            drawPlant(batch, TILE_SIZE, drawX, drawY, treeTexture);
        }

        if (tile.isBurned()) {
            Texture fireOverlay = textureManager.getBurnTexture();
            int realWidth = fireOverlay.getWidth();
            int realHeight = fireOverlay.getHeight();

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(fireOverlay, offsetX, offsetY, realWidth, realHeight);
        }


    }

    private void drawPlant(SpriteBatch batch, int TILE_SIZE, int drawX, int drawY, TextureRegion cropTexture) {
        int realWidth = cropTexture.getRegionWidth() / 3;
        int realHeight = cropTexture.getRegionHeight() / 3;

        int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
        int offsetY = drawY;
        batch.draw(cropTexture, offsetX, offsetY, realWidth, realHeight);
    }
}
