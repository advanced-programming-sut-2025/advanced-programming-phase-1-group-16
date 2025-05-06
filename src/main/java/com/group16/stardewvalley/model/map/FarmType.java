package com.group16.stardewvalley.model.map;

public enum FarmType {
    small(45, 70),
    big(60, 75);

    private int width;
    private int height;
    private TileType[][] tiles;

    FarmType(int height, int width) {
        this.height = height;
        this.width = width;
        this.tiles = makeFarm(this);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private TileType[][] makeFarm(FarmType farmType) {
        tiles = new TileType[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = TileType.Ground;
            }
        }
        if (farmType == FarmType.small) {
            for (int i = 1; i < 6; i++) {
                for (int j = 1; j < 7; j++) {
                    tiles[i][j] = TileType.GreenHouse;
                }
            }
            for (int i = 1; i < 5; i++) {
                for (int j = 33; j < 37; j++) {
                    tiles[i][j] = TileType.Cottage;
                }
            }
            for (int i = 34; i < 43; i++) {
                for (int j = 1; j < 10; j++) {
                    tiles[i][j] = TileType.Quarry;
                }
            }
            for (int i = 25; i < 35; i++) {
                for (int j = 45; j < 62; j++) {
                    tiles[i][j] = TileType.Lake;
                }
            }
        }
        if (farmType == FarmType.big) {
            for (int i = height - 7; i < height - 2; i++) {
                for (int j = 3; j < 9; j++) {
                    tiles[i][j] = TileType.GreenHouse;
                }
            }
            for (int i = 1; i < 5; i++) {
                for (int j = 1; j < 5; j++) {
                    tiles[i][j] = TileType.Cottage;
                }
            }
            for (int i = 1; i < 7; i++) {
                for (int j = width - 7; j < width - 1; j++) {
                    tiles[i][j] = TileType.Quarry;
                }
            }
            for (int i = height - 10; i < height - 1; i++) {
                for (int j = width - 13; j < width - 1; j++) {
                    tiles[i][j] = TileType.Lake;
                }
            }
            for (int i = (height - 5)/2 ; i < (height - 5)/2 + 5; i++) {
                for (int j = 5; j < 10; j++) {
                    tiles[i][j] = TileType.Lake;
                }
            }
        }

        return tiles;
    }
}
