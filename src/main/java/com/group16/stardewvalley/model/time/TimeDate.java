package com.group16.stardewvalley.model.time;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.user.Player;


//عملا ماه نداریم. هر فصل 28 روز است و 4 فصل داریم

public class TimeDate {
    private static TimeDate instance;
    private final Game game;  // اینم اضافه کردم
    private int hour;
    private int minute;
    private int day; // روز ماه
    private int year;
    private final int seasonLength = 28;
    private Season currentSeason;
    private DaysOfWeek currentDayOfWeek;

    private TimeDate(Game game) {
        this.game = game;
        //initialize
        this.hour = 9;
        this.minute = 0;
        this.day = 1; //first day of spring
        this.year = 1;
        this.currentSeason = Season.Spring;
        this.currentDayOfWeek = DaysOfWeek.Saturday;
    }

    public static TimeDate getInstance(Game game) {
        if (instance == null) {
            instance = new TimeDate(game);
        }
        return instance;
    }

    // افزایش یک ساعت
    public void advanceOneHour() {
        hour++;
        if (hour >= 24) {
            hour = 0;
            advanceOneDay();
        }
    }

    public int getHour() {
        return hour;
    }

    // افزایش یک روز
    private void advanceOneDay() {
        // برای سیتسم پیش بینی هوای فردا باید این ها تنظیم بشود
        game.setWeatherCondition(game.getTomorrowWeatherCondition());
        game.setTomorrowWeatherCondition(null);
        day++;

        // تغییر روز هفته
        int nextDayIndex = (currentDayOfWeek.getIndex() + 1) % 7;
        currentDayOfWeek = getDayOfWeekByIndex(nextDayIndex);

        // بررسی تغییر فصل
        if (day > seasonLength) {
            day = 1;
            int nextSeasonIndex = (currentSeason.getIndex() + 1) % 4;
            currentSeason = getSeasonByIndex(nextSeasonIndex);

            if (nextSeasonIndex == 0) { // اگر از زمستان به بهار برگشتیم
                year++;
            }
        }

        for (Player player : game.getPlayers()) {
            player.resetForNewDay();
        }
    }

    private DaysOfWeek getDayOfWeekByIndex(int index) {
        for (DaysOfWeek day : DaysOfWeek.values()) {
            if (day.getIndex() == index) {
                return day;
            }
        }
        throw new IllegalArgumentException("Invalid day of week index: " + index);
    }

    private Season getSeasonByIndex(int index) {
        for (Season season : Season.values()) {
            if (season.getIndex() == index) {
                return season;
            }
        }
        throw new IllegalArgumentException("Invalid season index: " + index);
    }

    // current state methods

    public String getTime() {
        return String.format("%02d:%02d", hour, minute);
    }

    public String getDate() {
        return "Day " + day + " of " + currentSeason + ", Year " + year;
    }

    public String getDateTime() {
        return getTime() + " - " + getDate();
    }

    public String getDayOfWeek() {
        return currentDayOfWeek.getName();
    }

    public String getSeason() {
        return currentSeason.getName();
    }




    //cheat codes

    // متد برای جلو بردن چند ساعت
    public Result advanceTimeCheat(int hours) {
        if (hours < 0) {
            return new Result(false,  "cant turn back time!");

        }
        // Advance time logic
        for (int i = 0; i < hours; i++) {
            advanceOneHour();
        }
        return new Result(true,  "cheated successfully!");
    }

    // متد برای جلو بردن چند روز
    public Result advanceDateCheat(int days) {
        if (days < 0) {
            return new Result(false,  "cant turn back date!");
        }
        // Advance date logic
        for (int i = 0; i < days; i++) {
            advanceOneDay();
        }
        return new Result(true,  "cheated successfully!");
    }

}