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

        if (tile.isPlowed()) {
            Texture plowedTile = TileTextureManager.getTileTextureManager().getTexture(TileType.Plowed);
            batch.draw(plowedTile, drawX, drawY, TILE_SIZE, TILE_SIZE);
        }

        if (tile.getCrop() != null) {
            TextureRegion cropTexture = textureManager.getCropRegion(tile.getCrop());
            drawPlant(batch, TILE_SIZE, drawX, drawY, cropTexture);
        }

        if (tile.getItem() != null) {
            Texture itemTexture = textureManager.getItemTexture(tile.getItem());
            int realWidth = Math.min(TILE_SIZE, itemTexture.getWidth());
            int realHeight = Math.min(TILE_SIZE, itemTexture.getHeight());

            batch.draw(itemTexture, drawX, drawY, realWidth, realHeight);
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
