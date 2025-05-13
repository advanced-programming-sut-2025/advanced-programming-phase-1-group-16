package com.group16.stardewvalley.model.NPC;

import com.group16.stardewvalley.model.user.Player;

import java.util.HashMap;
import java.util.Map;

public class NPC {
    private String name;
    private final Map<String, Integer> favoriteItems;
    private final Map<Player, Integer> friendshipNPCLevel;
    private final Map<Player, Integer> friendshipNPCScore;
    private final int[] relationshipRanks = {200, 400, 600, 800};

    public NPC(String name) {
        this.friendshipNPCLevel = new HashMap<>();
        this.favoriteItems = new HashMap<>();
        this.friendshipNPCScore = new HashMap<>();
        this.name = name;

    }


    public String getName() {
        return name;
    }

    public Map<Player, Integer> getFriendshipNPCLevel() {
        return friendshipNPCLevel;
    }

    public Map<Player, Integer> getFriendshipNPCScore() {
        return friendshipNPCScore;
    }
}
