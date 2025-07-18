package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class ShopTextureManager {
    private Map<String, TextureRegion> shops = new HashMap<>();
    private Texture spriteSheet;

    public ShopTextureManager(String season) {
        spriteSheet = new Texture("assets/shops_" + season + ".png");
        loadShops(spriteSheet);
    }

    private void loadShops(Texture sheet) {
        shops.put("pierre", new TextureRegion(sheet, 96, 144, 80, 96));
        shops.put("saloon", new TextureRegion(sheet, 176, 144, 80, 96));
        shops.put("clinic", new TextureRegion(sheet, 0, 240, 80, 96));
        // ادامه بقیه شاپ‌ها با مختصات دقیق
    }

    public TextureRegion getShopTexture(String shopName) {
        return shops.get(shopName);
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}

