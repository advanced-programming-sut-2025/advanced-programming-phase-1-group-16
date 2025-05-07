package com.group16.stardewvalley.model.map;


import com.group16.stardewvalley.model.app.Game;

public enum Direction {
    N("n", 0, -1),
    NE("ne", 1, -1),
    E("e", 1, 0),
    SE("se", 1, 1),
    S("s", 0, 1),
    SW("sw", -1, 1),
    W("w", -1, 0),
    NW("nw", -1, -1);

    private final String input;
    private final int xDelta;
    private final int yDelta;

    Direction(String input, int xDelta, int yDelta) {
        this.input = input;
        this.xDelta = xDelta;
        this.yDelta = yDelta;
    }

    public static Direction fromString(String input) {
        String normalized = input.toLowerCase().trim();
        for (Direction dir : values()) {
            if (dir.input.equalsIgnoreCase(normalized)) {
                return dir;
            }
        }
        throw new IllegalArgumentException("invalid direction");
    }


}