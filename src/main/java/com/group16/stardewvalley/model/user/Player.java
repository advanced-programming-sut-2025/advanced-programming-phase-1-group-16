package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.Inventory;
//import com.group16.stardewvalley.model.Shops.Shop;
import com.group16.stardewvalley.model.Items.Item;
import com.group16.stardewvalley.model.Tools.Gadget;
import com.group16.stardewvalley.model.map.Farm;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.TileType;

public class Player {
    private User user;
    private Farm farm;
    private int energy;
    private Pos position;


    private Inventory playerInventory;
    private Gadget currentEquipment;
    private Item currentThing;
    private int energyCeiling;
    private int level;
    private int coin;
    private int farmingAbilityLevel;
    private int miningAbilityLevel;
    private int natureTourismAbilityLevel;
    private Inventory inventory;
    private int fishingAbilityLevel;


    public Player(User user) {
        this.user = user;
        farmingAbilityLevel = 0;
        miningAbilityLevel = 0;
        fishingAbilityLevel = 0;
        natureTourismAbilityLevel = 0;
        playerInventory = new Inventory();
        energy = 200; // مقداردهی انرژی اولیه در ابتدای ساخت
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;    // این باید بخش زمان و در شروع روز جدید کامل بشه و عوامل دیگه ای که روی سقف این تاثیر داشتن لحاظ بشن
        // یا اگه غش کرده بشه ۷۵ درصد
    }

    public void increaseEnergy(int amount) {
        this.energy += amount;
    }

    public void decreaseEnergy(int amount) {
        this.energy -= amount;
    }

    public Pos getPosition() {
        return position;
    }

    public void setPosition(Pos position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Farm getFarm() {
        return farm;
    }

    public void faint(){

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


//TODO
//    public boolean move(int dx, int dy, Tile[][] map) {
//        int newX = this.position.getX() + dx;
//        int newY = this.position.getY() + dy;
//
//        if (isValidPosition(newX, newY, map)) {
//            Tile targetTile = map[newX][newY];
//            if (targetTile.isWalkable()) {
//                x = newX;
//                y = newY;
//                position = targetTile;
//                return true;
//                // بعد از این باید تکون بخوره کاراکترش اگه درسته
//            }
//        }
//        return false;
//    }

    private boolean isValidPosition(int x, int y, TileType[][] map) {
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length;
    }



//    public Shop whereAmI() {
//        // بر اساس مپ باید بده که الان در کدوم فروشگاه وایستادم
//    }
}
