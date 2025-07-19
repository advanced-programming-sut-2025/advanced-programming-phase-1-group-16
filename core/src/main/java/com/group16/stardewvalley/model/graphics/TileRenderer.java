package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileTextureManager;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.view.graphics.GameScreen;

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

        if (tile.getCrop() != null) {
            Texture cropTexture = textureManager.getCropTexture(tile.getCrop());
            int realWidth = cropTexture.getWidth();
            int realHeight = cropTexture.getHeight();

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(cropTexture, offsetX, offsetY, realWidth, realHeight);
        }

        if (tile.getItem() != null) {
            Texture itemTexture = textureManager.getItemTexture();
            int realWidth = itemTexture.getWidth();
            int realHeight = itemTexture.getHeight();

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(itemTexture, drawX, drawY, offsetX, offsetY, realWidth, realHeight);
        }

        if (tile.getTree() != null) {
            TextureRegion treeTexture = textureManager.getTreeRegion(tile.getTree());
            int realWidth = treeTexture.getRegionWidth() / 3;
            int realHeight = treeTexture.getRegionHeight() / 3;

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(treeTexture, offsetX, offsetY, realWidth, realHeight);
        }

        if (tile.isBurned()) {
            Texture fireOverlay = textureManager.getBurnTexture();
            int realWidth = fireOverlay.getWidth();
            int realHeight = fireOverlay.getHeight();

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(fireOverlay, offsetX, offsetY, realWidth, realHeight);
        }

        if (tile.isFertilized()) {
            Texture fertOverlay = textureManager.getFertalizeTexture();
            int realWidth = fertOverlay.getWidth();
            int realHeight = fertOverlay.getHeight();

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(fertOverlay, offsetX, offsetY, realWidth, realHeight);
        }
    }
}
