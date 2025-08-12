package com.group16.stardewvalley.app;

import com.group16.stardewvalley.model.map.Pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameDelta {
    public List<PlayerUpdate> playerUpdates = new ArrayList<>();
    public List<TileUpdate> tileUpdates = new ArrayList<>();
    public List<InventoryUpdate> inventoryUpdates = new ArrayList<>();
    public long serverTime;

    public static class PlayerUpdate {
        public String username;
        public Pos position;
        public int health;
    }

    public static class TileUpdate {
        public int tileX, tileY;

        // Crop
        public String cropType;      // null if no crop
        public int growthStage;

        // Tree
        public String treeType;      // null if no tree
        public int treeStage;

        // Item
        public String itemName;      // null if no item

        // Base tile type
        public String tileType;

        // Other states
        public boolean isFertilized;
        public String fertilizerType; // null if not fertilized
        public boolean hasWater;
        public boolean isBurned;
        public boolean isPlowed;
    }

    public static class InventoryUpdate {
        public String username;
        public Map<String, Integer> tools;      // tool name → count
        public Map<String, Integer> items;      // item name → count
        public Map<String, Integer> crops;      // crop name → count
        public List<String> craftingRecipes;    // names of crafting recipes
        public String backPackType;             // name of backpack type
        public int capacity;
    }
}
