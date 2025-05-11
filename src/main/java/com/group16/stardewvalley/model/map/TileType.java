package com.group16.stardewvalley.model.map;

public enum TileType {
    Tree('T', "\u001B[32m"),
    Stone('o', "\u001B[37m"),
    Forage('f', "\u001B[36m"),
    Ground('.', "\u001B[33m"),
    Lake('~', "\u001B[33m"),
    GreenHouse('G', "\u001B[35m"),
    Cottage('C', "\u001B[31m"),
    Quarry('Q', "\u001B[30m"),
    Plowed('o', "");

    private final char symbol;
    private final String colorCode;

    TileType(char symbol, String colorCode) {
        this.symbol = symbol;
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public char getSymbol() {
        return symbol;
    }
}
