package com.group16.stardewvalley.model.map;

public enum TileType {
    BLACKSMITH_SHOP(true, "blacksmith"),
    FARM(true, "Farm"),
    HOUSE(true, "Home"),
    RIVER(false, "River"),
    MOUNTAIN(false, "barroom"),
    WALL(false, "wall");

    // سوال پیش میاد بخش هایی که داخل این ها هستند اما باز هم نمیشه روش راه رفت رو چطور بایدهندل کرد؟

    private final boolean walkable;
    private final String locationName;

    TileType(boolean walkable, String locationName) {
        this.walkable = walkable;
        this.locationName = locationName;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public String getLocationName() {
        return locationName;
    }

    // ارگومانش از جیسونه میاد
    public boolean canDoOnThis() {

    }
}