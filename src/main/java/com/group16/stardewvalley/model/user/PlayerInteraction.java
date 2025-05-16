package com.group16.stardewvalley.model.user;

import java.util.ArrayList;
import java.util.List;

public class PlayerInteraction {
    private boolean talked;
    private boolean traded;
    private boolean gifted;
    private boolean hugged;
    private boolean flowered;
    private int friendshipLevel;
    private int friendshipScore;
    private final int[] relationshipRanks = {100, 200, 300, 400};
    private List<String> dialogueHistory;

    public PlayerInteraction() {
        talked = false;
        traded = false;
        gifted = false;
        hugged = false;
        flowered = false;
        friendshipLevel = 0;
        friendshipScore = 0;
        dialogueHistory = new ArrayList<>();
    }

    public boolean isFlowered() {
        return flowered;
    }

    public boolean isGifted() {
        return gifted;
    }

    public boolean isHugged() {
        return hugged;
    }

    public boolean isTalked() {
        return talked;
    }

    public boolean isTraded() {
        return traded;
    }

    public int getFriendshipLevel() {
        return friendshipLevel;
    }

    public int getFriendshipScore() {
        return friendshipScore;
    }

}
