package com.group16.stardewvalley.model.map;

public enum PlaceType {
    Blacksmith(25, 30, new Pos(95, 17), "shop"),
    CarpentersShop(25, 30, new Pos(130, 17), "shop"),
    JojaMart(25, 30, new Pos(165, 17), "shop"),
    FishShop(20, 40, new Pos(25, 90), "shop"),
    MarniesRanch(25, 30, new Pos(140, 80), "shop"),
    PierresGeneralStore(25, 30, new Pos(180, 80), "shop"),
    TheStardropSaloon(30, 30, new Pos(130, 140), "shop"),
    Sebastian(25, 25, new Pos(5, 130), "npc"),
    Abigail(25, 35, new Pos(130, 170), "npc"),
    Harvey(25, 35, new Pos(175, 170), "npc"),
    Leah(25, 25, new Pos(240, 95), "npc"),
    Robin(30, 40, new Pos(75, 90), "npc");

    private final int width;
    private final int height;
    private final Pos startPosition;
    private TileType[][] tiles;

    PlaceType(int height, int width, Pos startPosition, String type) {
        this.height = height;
        this.width = width;
        this.startPosition = startPosition;
        this.tiles = makePlace(type);
    }
    private TileType[][] makePlace(String type) {
        TileType[][] tiles = new TileType[height][width];
        switch (type) {
            case "shop":
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        tiles[y][x] = TileType.Shop;
                    }
                }
                break;
            case "npc" :
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        tiles[y][x] = TileType.NPCHouse;
                    }
                }
                break;
        }
        return tiles;
    }

    public int getWidth() {
        return width;
    }

    public Pos getStartPosition() {
        return startPosition;
    }

    public int getHeight() {
        return height;
    }

    public TileType[][] getTiles() {
        return tiles;
    }

    public void setTiles(TileType[][] tiles) {
        this.tiles = tiles;
    }
}
