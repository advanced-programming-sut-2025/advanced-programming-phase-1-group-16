package com.group16.stardewvalley.model.time;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Crop;
import com.group16.stardewvalley.model.agriculture.Tree;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.weather.WeatherCondition;

import static com.group16.stardewvalley.controller.agriculture.AgricultureController.attackOfCrow;


//عملا ماه نداریم. هر فصل 28 روز است و 4 فصل داریم

public class TimeDate {
    private static TimeDate instance;
    private int hour;
    private int minute;
    private int day; // روز ماه
    private int year;
    private final int seasonLength = 28;
    private Season currentSeason;
    private DaysOfWeek currentDayOfWeek;

    public TimeDate() {
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
            instance = new TimeDate();
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
    public void advanceOneDay() {
        // برای سیتسم پیش بینی هوای فردا باید این ها تنظیم بشود
        App.getActiveGame().setWeatherCondition(App.getActiveGame().getTomorrowWeatherCondition());
        App.getActiveGame().setTomorrowWeatherCondition(null);
        day++;

        // تغییر روز هفته
        int nextDayIndex = (currentDayOfWeek.getIndex() + 1) % 7;
        if (getDayOfWeekByIndex(nextDayIndex) != null) {
            currentDayOfWeek = getDayOfWeekByIndex(nextDayIndex);
        }

        // بررسی تغییر فصل
        if (day > seasonLength) {
            day = 1;
            int nextSeasonIndex = (currentSeason.getIndex() + 1) % 4;
            if (getSeasonByIndex(nextSeasonIndex) != null) {
                currentSeason = getSeasonByIndex(nextSeasonIndex);
            }

            if (nextSeasonIndex == 0) { // اگر از زمستان به بهار برگشتیم
                year++;
            }
        }

        //رشد دادن گیاهان کاشته شده
        for (int i = 0; i < App.getActiveGame().getMapHeight(); i++) {
            for (int j = 0; j < App.getActiveGame().getMapWidth(); j++) {
                Tile tile = App.getActiveGame().getMap()[i][j];
                if (tile.getCrop() != null && !tile.getCrop().isMature()) {
                    if (App.getActiveGame().getWeatherCondition() == WeatherCondition.RAINY && tile.getType() != TileType.GreenHouse) {
                        tile.getCrop().setWatered(true);
                    }
                    tile.getCrop().advanceStage();
                    attackOfCrow();
                    if (tile.getCrop().isWatered()) {
                        tile.getCrop().setWateredYesterday(true);
                        tile.getCrop().setWatered(false);
                    } else {
                        tile.getCrop().setWateredYesterday(false);
                    }


                    if (!tile.getCrop().isWateredYesterday() && !tile.getCrop().isWatered()) {
                        tile.setCrop(null);
                    }
                }

                if (tile.getTree() != null && !tile.getTree().isMature()) {
                    tile.getTree().advanceStage();
                    if (App.getActiveGame().getWeatherCondition() == WeatherCondition.RAINY && tile.getType() != TileType.GreenHouse) {
                        tile.getTree().setWatered(true);
                    }
                    if (tile.getTree().isWatered()) {
                        tile.getTree().setWateredYesterday(true);
                        tile.getTree().setWatered(false);
                    } else {
                        tile.getTree().setWateredYesterday(false);
                    }
                    if (!tile.getTree().isWateredYesterday() && !tile.getTree().isWatered()) {
                        tile.setTree(null);
                    }
                }
            }
        }

        //  همه ی فروشگاه ها هم داشته باشند این تابع رو باید =)
        for (Player player : App.getActiveGame().getPlayers()) {
            player.resetForNewDay();
        }
    }

    private DaysOfWeek getDayOfWeekByIndex(int index) {
        for (DaysOfWeek day : DaysOfWeek.values()) {
            if (day.getIndex() == index) {
                return day;
            }
        }
        return null;
    }

    private Season getSeasonByIndex(int index) {
        for (Season season : Season.values()) {
            if (season.getIndex() == index) {
                return season;
            }
        }
        return null;
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
    public Season getCurrentSeason() {
        return this.currentSeason;
    }

}