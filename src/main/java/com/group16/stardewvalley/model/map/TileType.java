package com.group16.stardewvalley.model.map;

public enum TileType {
    NPCHouse('⌂', "\033[37;41m"),     // خانه‌ی شخصیت‌ها: "⌂" نماد خانه (Unicode 8962)
    Shop('$', "\033[30;46m"),        // مغازه: "$" نماد پول و خرید
    Tree('♣', "\033[32;42m"),        // درخت: "♣" نماد برگ و طبیعت
    Stone('o', "\033[30;47m"),       // سنگ: "◼" نماد بلوک سنگی
    Forage('F', "\033[32;40m"),      // گیاه خودرو: "*" سبکی ساده و برجسته
    MineralForage('M', "\033[32;41m"),
    Ground('◼', "\033[33;40m"),      // خاک: "." همچنان مناسب و مینیمال
    Lake('≈', "\033[37;44m"),        // دریاچه: "≈" نماد موج آب
    GreenHouse('+', "\033[30;106m"), // گلخانه: "▣" قاب‌دار، متمایز و ساختمانی
    Cottage('⌂', "\033[37;45m"),     // کلبه: "⌂" مشابه NPCHouse ولی با رنگ متفاوت
    Quarry('▓', "\033[30;107m"),     // معدن: "▓" حالت سنگ‌ریزه‌ مانند
    Plowed('∷', "\033[33;43m");      // زمین شخم‌زده: "∷" نشانه ردیفی بودن


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
