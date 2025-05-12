package com.group16.stardewvalley.model.Items;

public abstract class Item {
    private final String name;
    private int price;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
    // این ها همان سنگ و چوبی هستند که در قسمت معامله هستند
    // هر پارت باید یا سنگ و چوب باشد یا اینکه مثلا چیزی از تایل تایپ باشد
    // ساخت تویله سنگ و چوب شک به تایل تایپ
    // بقیه جز ابزار ها نیستند
    //وسایل استخراج+ ارتقا کشاورزی و دانه ها
    // این ادم رو به ابسترکت کلاس thing سوق میده این دانه


    //====> الان اینطوری شد که هر چیزی که میشه کاشت میشه خرید میشه توی inventory گذاشت و کلا هرچی میشه بهش دست زد برو بذاریم زیر مجموعه ی لیست که سر ذخیره شون توی جاهای مختلف راحت باشه
}
