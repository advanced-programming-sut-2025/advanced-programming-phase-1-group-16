package com.group16.stardewvalley.model.map;

public enum PlaceType {
    Blacksmith(10, 20, new Pos(230, 110), "shop"),
    CarpentersShop(15, 25, new Pos(130, 10), "shop"),
    JojaMart(15, 30, new Pos(250, 70), "shop"),
    FishShop(10, 20, new Pos(170, 170), "shop"),
    MarniesRanch(10, 20, new Pos(25, 90), "shop"),
    PierresGeneralStore(10, 15, new Pos(170, 50), "shop"),
    TheStardropSaloon(10, 20, new Pos(110, 135), "shop"),

    Sebastian(10, 10, new Pos(100, 67), "npc"),
    Abigail(10, 10, new Pos(140, 70), "npc"),
    Harvey(10, 10, new Pos(100, 97), "npc"),
    Leah(10, 10, new Pos(180, 67), "npc"),
    Robin(10, 10, new Pos(180, 97), "npc");

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
