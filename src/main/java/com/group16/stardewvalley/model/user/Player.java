package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.NPC.NPCInteraction;
import com.group16.stardewvalley.model.Request;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.map.Farm;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;

import java.util.*;

import com.group16.stardewvalley.model.NPC.NPC;
import com.group16.stardewvalley.model.notification;
import com.group16.stardewvalley.model.shops.Shop;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.tools.Gadget;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.map.*;


public class Player {
    private User user;
    private Farm farm;
    private int energy;
    private boolean isEnergyUnlimited;
    private Pos position;
    private Inventory inventory;
    private int energyCeiling;
    private Gadget currentEquipment;
    private Item currentThing;
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
    private final Map<NPC, NPCInteraction> dailyNPCInteraction;
    private final Map<Player, PlayerInteraction> dailyPlayerInteraction;
    private final List<Request> quests;
    private final List<notification> notifications;
    private int todayIncome;
    private final List<Request> requestHistory;
    private Player spouse;
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
        isEnergyUnlimited = false;
        inventory = new Inventory();
        energyCeiling = 200;
        energy = 200;
        this.spouse = null;
        isFainted = false;
        this.dailyPlayerInteraction = new HashMap<>();
        this.dailyNPCInteraction = new HashMap<>();
        this.quests = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.requestHistory = new ArrayList<>();
        this.rejectionCooldown = 0;
        this.isBuffActive = false;
        hourPastForBuff = 0;
        finalHourBuff = 0;
        this.buffer = "";
    }

    public String getName() {
        return user.getNickName();
    }

    public boolean isEnergyUnlimited() {
        return isEnergyUnlimited;
    }

    public void setEnergyUnlimited(boolean energyUnlimited) {
        isEnergyUnlimited = energyUnlimited;
    }

    public int getBaseEnergyCeiling() {
        return energyCeiling;
    }

    public void learnFood(Food food) {
        this.knownRecipes.add(food);
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

    public Result showNotifications() {
        StringBuilder sb = new StringBuilder();
        if (!notifications.isEmpty()) {
        for (notification notification : notifications) {
            sb.append(notification.getMessage());
            sb.append("\n");
        }
        return new Result(true, sb.toString());
        }
        return new Result(false, "you don't have any notification");
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<NPC, NPCInteraction> getDailyNPCInteraction() {
        return dailyNPCInteraction;
    }

    public Map<Player, PlayerInteraction> getDailyPlayerInteraction() {
        return dailyPlayerInteraction;
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

    public NPCInteraction getInteractionWith(NPC npc) {
        return dailyNPCInteraction.get(npc);
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

    public int getCoin() {
        if (spouse != null) {
            return getTotalCoin();
        }
        return coin;
    }

    public void setCoin(int amount) {
        this.coin = amount;
    }

    public String getGender() {
        return user.getGender();
    }

    public void addNotification(notification notification) {
        notifications.add(notification);
    }

    public PlayerInteraction getInteractionWith(Player player) {
        return dailyPlayerInteraction.get(player);
    }

    public void increaseCoin(int amount) {
        if (spouse != null) {
            spouse.increaseCoin(amount / 2);
            this.increaseCoin(amount / 2);
        }
        coin += amount;
    }

    public int getTotalCoin() {
        return coin + spouse.getCoin();
    }

    public Player getSpouse() {
        return spouse;
    }

    public void setSpouse(Player spouse) {
        this.spouse = spouse;
    }

    public int getFishingAbilityLevel() {
        return fishingAbilityLevel;
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
        if (isEnergyUnlimited) {
            return true;
        }
        return energy > amount;
    }

    public void equip(Gadget gadget) {
        this.currentEquipment = gadget;
    }

    public Gadget getCurrentEquipment() {
        return currentEquipment;
    }


    public void decreaseEnergy(int amount) {
        energy = Math.max(0, energy - amount);
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
        energy = Math.min(energy + amount, energyCeiling);
    }

    private boolean isInBound(int x, int y, TileType[][] map) {
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length;
    }


    public void setFaintStatus(boolean b) {
        this.isFainted = b;
    }

    public void setFriendshipLevelWith(int amount, String username) {
        Player target = App.getActiveGame().getPlayerByUsername(username);
        this.getInteractionWith(target).setFriendshipLevel(amount);
    }

    public List<notification> getNotifications() {
        return notifications;
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
        for (Map.Entry<NPC, NPCInteraction> entry : dailyNPCInteraction.entrySet()) {
            NPCInteraction interaction = entry.getValue();
            interaction.setMetToday(false);
            interaction.setGiftedToday(false);
        }

        for (Map.Entry<Player, PlayerInteraction> entry : dailyPlayerInteraction.entrySet()) {
            PlayerInteraction interaction = entry.getValue();
            interaction.setTalked(false);
            interaction.setFlowered(false);
            interaction.setGifted(false);
            interaction.setHugged(false);
            interaction.setTraded(false);
        }
        increaseCoin(todayIncome);
        this.todayIncome = 0;

    }

    public void decreaseCoin(int amount) {
        this.coin -= amount;
    }

    public void faint(){
        this.isFainted = true;
        this.energy = 0;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public boolean isFainted() {
        return isFainted;
    }
}
