package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.map.Farm;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Player {
    private User user;
    private Farm farm;
    private int energy;
    private Pos position;
    private Inventory inventory;
    private int energyCeiling;
    private Gadget currentEquipment;
    private Set<Food> knownRecipes = new HashSet<>();
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
    private int rejectionCooldown;
    private Map<Player, Integer> interactionsLevel;
    private Map<Player, Integer> interactionScore;
    private Map<Player, Boolean> interactionTodayStatus;
    private Map<NPC, Integer> friendshipNPCScore;
    private Map<NPC, Integer> friendshipNPCLevel;
    private final int[] relationshipRanks = {100, 200, 300, 400};
    private final int[] NPCRelationshipRanks = {200, 400, 600, 800};
    private String buffer;
    private boolean isBuffActive;
    private int hourPastForBuff;
    private int finalHourBuff;




    // مقدار های ماکسیمم هر توانایی رو هم در گیم ذخیره کردم سر جمع شه
    // تابعی برای بالا بردن لول شخص در این موارد نوشته نشده است
    public Player(User user) {
        this.user = user;
        farmingAbilityLevel = 0;
        miningAbilityLevel = 0;
        fishingAbilityLevel = 0;
        foragingAbilityLevel = 0;
        farmingAbilityScore = 0;
        miningAbilityScore = 0;
        foragingAbilityScore = 0;
        fishingAbilityScore = 0;
        inventory = new Inventory();
        energyCeiling = 200;// مقداردهی انرژی اولیه در ابتدای ساخت
        energy = 200;
        isFainted = false;
        this.rejectionCooldown = 0;
        this.interactionsLevel = new HashMap<>();
        this.interactionScore = new HashMap<>();
        this.interactionTodayStatus = new HashMap<>();
        this.friendshipNPCScore = new HashMap<>();
        this.friendshipNPCLevel = new HashMap<>();
        this.isBuffActive = false;
        hourPastForBuff = 0;
        finalHourBuff = 0;
    }

    public int getFinalHourBuff() {
        return finalHourBuff;
    }

    public void setFinalHourBuff(int finalHourBuff) {
        this.finalHourBuff = finalHourBuff;
    }

    public int getEnergyCeiling() {
        return energyCeiling;
    }

    public void setEnergyCeiling(int energyCeiling) {
        this.energyCeiling = energyCeiling;
    }

    public int getHourPastForBuff() {
        return hourPastForBuff;
    }

    public void setHourPastForBuff(int hourPastForBuff) {
        this.hourPastForBuff = hourPastForBuff;
    }

    public boolean isBuffActive() {
        return isBuffActive;
    }

    public void setBuffActive(boolean buffActive) {
        isBuffActive = buffActive;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void learnRecipe(Food food) {
        knownRecipes.add(food);
    }

    public Set<Food> getKnownRecipes() {
        return knownRecipes;
    }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
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

    public void increaseCoin(int amount) {
        coin -= amount;
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

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public boolean hasEnoughEnergy(int amount) {
        return energy > amount;
    }

    public void equip(Gadget gadget) {
        this.currentEquipment = gadget;
    }

    public Gadget getCurrentEquipment() {
        return currentEquipment;
    }


    public void decreaseEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
        }
        energy = 0;
    }

    public Tile getLocation() {
        // طبیعتا باید  x, y این یارو هم معتبر باشه
        if (this.getX() < App.getActiveGame().getMapHeight() && this.getY() < App.getActiveGame().getMapWidth()) {
            return App.getActiveGame().getMap()[this.getX()][this.getY()];
        }
        return null;
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


    public void setFaintStatus(boolean b) {
        this.isFainted = b;
    }

    public void setRejectionCooldown(int amount) {
        rejectionCooldown = amount;
    }
    //RESETFORNEWDAY
    public void resetForNewDay() {
        if (rejectionCooldown > 0) {
            energyCeiling = 150;
            rejectionCooldown--;
        }
        this.isFainted = false;
        energy = energyCeiling;
        interactionTodayStatus.replaceAll((player, status) -> false);

    }

    public void faint(){

    }

}
