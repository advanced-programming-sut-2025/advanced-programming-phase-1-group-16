package com.group16.stardewvalley.model.Weather;

import com.group16.stardewvalley.model.app.Game;

public class Greenhouse {

    private final int requiredCoin = 1000;
    private final int requiredWood = 500;
    private final int length = 6;
    private final int width = 5;
    // این دو عدد بدون در نظر گرفتن دیوار وارد شده اند
    private final Game game;

    public Greenhouse(Game game1) {
        this.game = game1;
    }
    // رشد بدون محدودیت + عدم نیاز به مترسک + مخزن اب داخلی
    // برای کاشت هم عول پیکر نباید باشند محصولات
}
