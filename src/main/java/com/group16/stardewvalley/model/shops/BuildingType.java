package com.group16.stardewvalley.model.shops;

public enum BuildingType {

    Barn(6000, 350, 150, 7, 4, 1),
    Big_Barn(12000, 450, 200, 7, 4, 1),
    Deluxe_Barn(25000, 550, 300, 7, 4, 1),
    Coop(4000, 300, 100, 6, 3, 1),
    Gig_Coop(10000, 400, 150, 6, 3, 1),
    Deluxe_Coop(20000, 500, 200, 6, 3, 1),
    Well(1000, 0, 75, 3, 3, 1),
    Shipping_Bin(250, 150, 0, 1, 1, Integer.MAX_VALUE),
    Big_Coop();
    private final int cost;
    private final int woodCost;
    private final int stoneCost;
    private final int length;
    private final int width;
    private final int dailyLimit;

    BuildingType(int cost, int woodCost, int stoneCost, int length, int width, int dailyLimit) {
        this.cost = cost;
        this.woodCost = woodCost;
        this.stoneCost = stoneCost;
        this.length = length;
        this.width = width;
        this.dailyLimit = dailyLimit;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
}
