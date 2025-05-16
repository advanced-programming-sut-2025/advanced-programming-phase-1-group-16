package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.NPC.NPC;
import com.group16.stardewvalley.model.NPC.NPCInteraction;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.tools.Gadget;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.map.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private Inventory playerInventory;
    private Gadget currentEquipment;
    private Item currentThing;
    private int energyCeiling;
    private Pos position;
    private Farm farm;
    private User user;
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
    private int rejectionCooldown;
    private final Map<NPC, NPCInteraction> dailyNPCInteraction;
    private final Map<Player, PlayerInteraction> dailyPlayerInteraction;
    private final List<String> quests;

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
        playerInventory = new Inventory();
        energyCeiling = 200;// مقداردهی انرژی اولیه در ابتدای ساخت
        energy = 200;
        isFainted = false;
        this.rejectionCooldown = 0;
        this.dailyNPCInteraction = new HashMap<>();
        this.dailyPlayerInteraction = new HashMap<>();
        this.quests = new ArrayList<>();
        initializeInteractions();
    }


    public void initializeInteractions() {
        dailyNPCInteraction.put(App.getActiveGame().getNPCByName("Sebastian"), new NPCInteraction());
        dailyNPCInteraction.put(App.getActiveGame().getNPCByName("Abigail"), new NPCInteraction());
        dailyNPCInteraction.put(App.getActiveGame().getNPCByName("Harvey"), new NPCInteraction());
        dailyNPCInteraction.put(App.getActiveGame().getNPCByName("Leah"), new NPCInteraction());
        dailyNPCInteraction.put(App.getActiveGame().getNPCByName("Robin"), new NPCInteraction());
        for (Player player1 : App.getActiveGame().getPlayers()) {
            dailyPlayerInteraction.put(player1, new PlayerInteraction());
        }
    }

    public List<String> getQuests() {
        return quests;
    }

    public void addQuest(String line) {
        quests.add(line);
    }

    public void removeQuest(String line) {
        for (String target : quests) {
            if (target.equalsIgnoreCase(line)) {
                quests.remove(target);
            }
        }
    }

    public NPCInteraction getInteractionWith(NPC npc) {
        return dailyNPCInteraction.get(npc);
    }

    public PlayerInteraction getInteractionWith(Player player) {
        return dailyPlayerInteraction.get(player);
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getGender() {
        return user.getGender();
    }

    public String getName() {
        return user.getNickName();
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
        coin += amount;
    }

    public void decreaseCoin(int amount) {
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

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void faint() {
        this.isFainted = true;
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

    }

    public void recordNPCDialogue(NPC npc, String dialogueLine) {
        NPCInteraction interaction = dailyNPCInteraction.getOrDefault(npc, new NPCInteraction());
        interaction.addDialogue(dialogueLine);
        dailyNPCInteraction.put(npc, interaction);
    }

    public List<String> getDialogueWith(NPC npc) {
        if (dailyNPCInteraction.containsKey(npc)) {
            return dailyNPCInteraction.get(npc).getDialogueHistory();
        }
        return null;
    }

}
