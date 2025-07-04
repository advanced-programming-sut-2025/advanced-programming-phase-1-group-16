package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileTextureManager;

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

        // 2. اگر crop وجود داشت، رسم کن
        if (tile.getCrop() != null) {
            Texture cropTexture = textureManager.getCropTexture(tile.getCrop());
            int realWidth = cropTexture.getWidth();     // مثلا 64
            int realHeight = cropTexture.getHeight();   // مثلا 96

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(cropTexture, offsetX, offsetY, realWidth, realHeight);
        }

        // 4. اگر آیتم روی زمین افتاده بود
        if (tile.getItem() != null) {
            Texture itemTexture = textureManager.getItemTexture();
            int realWidth = itemTexture.getWidth();     // مثلا 64
            int realHeight = itemTexture.getHeight();   // مثلا 96

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(itemTexture, drawX, drawY, offsetX, offsetY, realWidth, realHeight);
        }

        // 3. اگر tree وجود داشت
        if (tile.getTree() != null) {
            Texture treeTexture = textureManager.getTreeTexture(tile.getTree());
            int realWidth = 30;     // مثلا 64
            int realHeight = 60;   // مثلا 96

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(treeTexture, offsetX, offsetY, realWidth, realHeight);
        }

        if (tile.isBurned()) {
            Texture fireOverlay = textureManager.getBurnTexture();
            int realWidth = fireOverlay.getWidth();     // مثلا 64
            int realHeight = fireOverlay.getHeight();   // مثلا 96

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(fireOverlay, offsetX, offsetY, realWidth, realHeight);
        }

        // 6. اگر کود ریخته شده بود
        if (tile.isFertilized()) {
            Texture fertOverlay = textureManager.getFertalizeTexture();
            int realWidth = fertOverlay.getWidth();     // مثلا 64
            int realHeight = fertOverlay.getHeight();   // مثلا 96

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(fertOverlay, offsetX, offsetY, realWidth, realHeight);
        }
    }
}
