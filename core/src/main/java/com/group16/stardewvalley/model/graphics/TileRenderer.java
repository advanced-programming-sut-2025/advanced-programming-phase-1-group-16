package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileTextureManager;
import com.group16.stardewvalley.model.map.TileType;

public class TileRenderer {
    private final GameAssetManager textureManager;
    private final int TILE_SIZE = 15;


    public TileRenderer() {
        this.textureManager = GameAssetManager.getGameAssetManager();
    }

    public void renderTile(SpriteBatch batch, Tile tile, int x, int y) {
        int drawX = x * TILE_SIZE;
        int drawY = y * TILE_SIZE;


        // 1. رسم پس‌زمینه‌ی اصلی تایل (مثلاً grass, soil)
        if (tile.getType().equals(TileType.CottageStartPos)) {
            Texture house = GameAssetManager.getGameAssetManager().getHouseTexture();
            int realWidth = TILE_SIZE * 10;
            int realHeight = TILE_SIZE * 10;

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(house, offsetX, offsetY, realWidth, realHeight);
        }
        // 2. اگر crop وجود داشت، رسم کن
        if (tile.getCrop() != null) {
            Texture cropTexture = textureManager.getCropTexture(tile.getCrop());
            int realWidth = cropTexture.getWidth();
            int realHeight = cropTexture.getHeight();

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(cropTexture, offsetX, offsetY, realWidth, realHeight);
        }

        // 4. اگر آیتم روی زمین افتاده بود
        if (tile.getItem() != null) {
            Texture itemTexture = textureManager.getItemTexture();
            int realWidth = itemTexture.getWidth();
            int realHeight = itemTexture.getHeight();

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(itemTexture, drawX, drawY, offsetX, offsetY, realWidth, realHeight);
        }

        // 3. اگر tree وجود داشت
        if (tile.getTree() != null) {
            Texture treeTexture = textureManager.getTreeTexture(tile.getTree());
            int realWidth = 30;
            int realHeight = 60;

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

        // 6. اگر کود ریخته شده بود
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
