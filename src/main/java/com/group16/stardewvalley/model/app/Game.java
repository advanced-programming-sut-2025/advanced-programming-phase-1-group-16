package com.stardewvalley;

public class Game {
    private static Player currentPlayer;
    private static TileType[][] map;

    public Game(TileType[][] map, Player player) {
        this.map = map;
        this.currentPlayer = player;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static TileType[][] getMap() {
        return map;
    }

    public void switchPlayer() {
        // اینجا باید سطح انرژی بازیکن چک بشود و اگر ۰ بود برویم سراغ نفر بعدی لیست
    }
}