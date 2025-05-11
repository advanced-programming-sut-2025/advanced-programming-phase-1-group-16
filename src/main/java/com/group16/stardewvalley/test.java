package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Shops.Shop;
import com.group16.stardewvalley.model.Things.Item;
import com.group16.stardewvalley.model.Tools.Gadget;
import com.group16.stardewvalley.model.map.*;

public class Player {
    private Inventory playerInventory;
    private Gadget currentEquipment;
    private Item currentThing;
    private int energyCeiling;
    private int level;
    private TileType position;
    int x, y; // ذخیره ی موقعیت یارو روی نقشه
    private int energy;
    private int coin;
    private int farmingAbilityLevel;
    private int miningAbilityLevel;
    private int natureTourismAbilityLevel;
    private int fishingAbilityLevel;

    public Player() {
        farmingAbilityLevel = 0;
        miningAbilityLevel = 0;
        fishingAbilityLevel = 0;
        natureTourismAbilityLevel = 0;
        playerInventory = new Inventory();
        energy = 200; // مقداردهی انرژی اولیه در ابتدای ساخت
    }

    public int getFarmingAbilityLevel() {
        return farmingAbilityLevel;
    }

    private int getMiningAbilityLevel() {
        return miningAbilityLevel;
    }

    public int getNatureTourismAbilityLevel() {
        return natureTourismAbilityLevel;
    }

    public int getFishingAbilityLevel() {
        return fishingAbilityLevel;
    }
    // thing برای ابزارالاتی است که در زیر مجموعه ی tools قرار نمی گیرد که هنوز نمیدونم چیه

    public int getCoin() {
        return coin;
    }

    public int getLevel() {
        return level;
    }

    public int getEnergy() {
        return energy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean hasEnoughEnergy(int amount) {
        return energy > amount;
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    public void equip(Gadget gadget) {
        this.currentEquipment = gadget;
    }

    public Gadget getCurrentEquipment() {
        return currentEquipment;
    }

    public String getPositionName() {
        return position.getLocationName();
    }

    public TileType getPosition() {
        return position;
    }

    public boolean move(int dx, int dy, TileType[][] map) {
        int newX = x + dx;
        int newY = y + dy;

        if (isValidPosition(newX, newY, map)) {
            TileType targetTile = map[newX][newY];
            if (targetTile.isWalkable()) {
                x = newX;
                y = newY;
                position = targetTile;
                return true;
                // بعد از این باید تکون بخوره کاراکترش اگه درسته
            }
        }
        return false;
    }

    private boolean isValidPosition(int x, int y, TileType[][] map) {
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length;
    }

    public void setEnergy(int energy) {
        this.energy = energy;    // این باید بخش زمان و در شروع روز جدید کامل بشه و عوامل دیگه ای که روی سقف این تاثیر داشتن لحاظ بشن
        // یا اگه غش کرده بشه ۷۵ درصد
    }

    public Shop whereAmI() {
        // بر اساس مپ باید بده که الان در کدوم فروشگاه وایستادم
    }
}
