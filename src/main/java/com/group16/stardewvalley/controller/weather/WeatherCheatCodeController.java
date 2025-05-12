package com.group16.stardewvalley.controller.weather;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.weather.WeatherCondition;
import com.group16.stardewvalley.model.app.Game;

import java.util.regex.Matcher;

public class WeatherCheatCodeController {
    private final Game game;

    public WeatherCheatCodeController(Game game1) {
        this.game = game1;
    }

    public Result applyFirelight() {
        // انتخاب رندوم یک لوکیشن و زدن رعد و برق بهش
        // فقط و فقط اگر جزئی از لوکیشن گلخانه بود نباید اثر کند

    }

    public Result changeWeather(Matcher matcher) {
        String weatherName = matcher.group("Type");
        WeatherCondition targetWeather = WeatherCondition.getByName(weatherName);
        game.setTomorrowWeatherCondition(targetWeather);
        return new Result(false, "Tomorrow's weather changed successfully  ^.^");
    }

}
