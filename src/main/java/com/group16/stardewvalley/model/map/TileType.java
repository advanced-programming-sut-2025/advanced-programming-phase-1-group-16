package com.group16.stardewvalley.model.map;

public enum TileType {
    Tree('T', "\033[32;42m"),        // سبز روی سبز: نماد درخت
    Stone('o', "\033[30;47m"),       // مشکی روی سفید: سنگ روشن
    Forage('f', "\033[32;40m"),      // سبز روی مشکی: گیاهان خودرو
    Ground('.', "\033[33;40m"),      // زرد روی مشکی: خاک
    Lake('~', "\033[37;44m"),        // سفید روی آبی: دریاچه (قبلی)
    GreenHouse('G', "\033[30;106m"), // مشکی روی سبز روشن: گلخانه
    Cottage('C', "\033[37;45m"),     // سفید روی بنفش: کلبه
    Quarry('Q', "\033[30;107m"),     // مشکی روی خاکستری روشن: معدن
    Plowed('o', "\033[33;43m");      // زرد روی زرد: زمین شخم‌زده

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
