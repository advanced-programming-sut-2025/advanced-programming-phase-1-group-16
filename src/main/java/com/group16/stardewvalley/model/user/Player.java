package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Shops.Shop;
import com.group16.stardewvalley.model.Items.Item;
import com.group16.stardewvalley.model.Tools.Gadget;
import com.group16.stardewvalley.model.map.*;

import java.util.Map;

public class Player {
    private Inventory playerInventory;
    private Gadget currentEquipment;
    private Item currentThing;
    private int energyCeiling;
    private int level;
    private Pos position;
    private int energy;
    private int coin;
    private final int[] levelRanks = {450, 350, 250, 150};
    private int farmingAbilityLevel;
    private int farmingAbilityScore;
    private int miningAbilityLevel;
    private int miningAbilityScore;
    private int foragingAbilityLevel;
    private int foragingAbilityScore;
    private int fishingAbilityLevel;
    private int fishingAbilityScore;
    private boolean isFainted;
    private Map<Player, Integer> interactionsLevel;
    private Map<Player, Integer> interactionScore;

    // مقدار های ماکسیمم هر توانایی رو هم در گیم ذخیره کردم سر جمع شه
    // تابعی برای بالا بردن لول شخص در این موارد نوشته نشده است
    public Player() {
        farmingAbilityLevel = 0;
        miningAbilityLevel = 0;
        fishingAbilityLevel = 0;
        foragingAbilityLevel = 0;
        farmingAbilityScore = 0;
        miningAbilityScore = 0;
        foragingAbilityScore = 0;
        fishingAbilityScore = 0;
        playerInventory = new Inventory();
        energy = 200;// مقداردهی انرژی اولیه در ابتدای ساخت
        isFainted = false;
    }

    public int getFarmingAbilityLevel() {
        return farmingAbilityLevel;
    }

    public int getMiningAbilityLevel() {
        return miningAbilityLevel;
    }

    public int getForagingAbilityLevel() {
        return foragingAbilityLevel;
    }

    public int getFishingAbilityLevel() {
        return fishingAbilityLevel;
    }

    public int getCoin() {
        return coin;
    }

    public void addFarmingAbilityScore(int amount) {
        farmingAbilityScore += amount;
        if (farmingAbilityScore >= levelRanks[0]) {
            farmingAbilityLevel = 4;
        }

        if (farmingAbilityScore >= levelRanks[1]) {
            farmingAbilityLevel = 3;
        }

        if (farmingAbilityScore >= levelRanks[2]) {
            farmingAbilityLevel = 2;
        }

        if (farmingAbilityScore >= levelRanks[3]) {
            farmingAbilityLevel = 1;
        }
    }

    public void addMiningAbilityScore(int amount) {
        miningAbilityScore += amount;
        if (miningAbilityScore >= levelRanks[0]) {
            miningAbilityLevel = 4;
        }

        if (miningAbilityScore >= levelRanks[1]) {
            miningAbilityLevel = 3;
        }

        if (miningAbilityScore >= levelRanks[2]) {
            miningAbilityLevel = 2;
        }

        if (miningAbilityScore >= levelRanks[3]) {
            miningAbilityLevel = 1;
        }
    }

    public void addNatureTourismAbilityScore(int amount) {
        foragingAbilityScore += amount;
        if (foragingAbilityScore >= levelRanks[0]) {
            foragingAbilityLevel = 4;
        }

        if (foragingAbilityScore >= levelRanks[1]) {
            foragingAbilityLevel = 3;
        }

        if (foragingAbilityScore >= levelRanks[2]) {
            foragingAbilityLevel = 2;
        }

        if (foragingAbilityScore >= levelRanks[3]) {
            foragingAbilityLevel = 1;
        }
    }

    public void addFishingAbilityScore(int amount) {
        fishingAbilityScore += amount;
        if (fishingAbilityScore >= levelRanks[0]) {
            fishingAbilityLevel = 4;
        }

        if (fishingAbilityScore >= levelRanks[1]) {
            fishingAbilityLevel = 3;
        }

        if (fishingAbilityScore >= levelRanks[2]) {
            fishingAbilityLevel = 2;
        }

        if (fishingAbilityScore >= levelRanks[3]) {
            fishingAbilityLevel = 1;
        }
    }

    public int getLevel() {
        return level;
    }

    public int getEnergy() {
        return energy;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public boolean hasEnoughEnergy(int amount) {
        return energy > amount;
    }

    public Inventory getInventory() {
        return playerInventory;
    }

    public void equip(Gadget gadget) {
        this.currentEquipment = gadget;
    }

    public Gadget getCurrentEquipment() {
        return currentEquipment;
    }

    public Pos getPosition() {
        return position;
    }

    public void decreaseEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
        }
        energy = 0;
    }

    public void increaseEnergy(int amount) {
        if (amount <= 0) {
            amount = 0;
        }
        if (energy + amount >= energyCeiling) {
            energy = energyCeiling;
        }
        energy += amount;
    }

    private boolean isInBound(int x, int y, TileType[][] map) {
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length;
    }

    public void setEnergy(int energy) {
        this.energy = energy;    // این باید بخش زمان و در شروع روز جدید کامل بشه و عوامل دیگه ای که روی سقف این تاثیر داشتن لحاظ بشن
        // یا اگه غش کرده بشه ۷۵ درصد
    }

    public Shop whereAmI() {
        // بر اساس مپ باید بده که الان در کدوم فروشگاه وایستادم
    }

    public void faint() {
        this.isFainted = true;
    }

    // اول هر روز از غش کردگی با موفقیت در میاد
    public void setFaintStatus(boolean b) {
        this.isFainted = b;
    }

}
