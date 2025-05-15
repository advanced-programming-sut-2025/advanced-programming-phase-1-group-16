package com.group16.stardewvalley.controller.weather;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.weather.WeatherCondition;
import com.group16.stardewvalley.model.app.Game;

import java.security.SecureRandom;

public class WeatherController {

    private final Game game;

    public WeatherController(Game game) {
        this.game = game;
    }

    public Result showWeather() {
        return new Result(true, game.getWeatherCondition().name());
    }

    public Result weatherForecast() {
        SecureRandom secureRandom = new SecureRandom();
        int randomValue = secureRandom.nextInt(4) + 1;

        WeatherCondition weatherCondition = WeatherCondition.getByNumber(randomValue);
        game.setTomorrowWeatherCondition(weatherCondition);
        return new Result(true, "Tomorrow's weather : " + weatherCondition.getWeatherName());
    }

    //TODO mirshekar
    /*
    public Result buildGreenhouse() {

    }

     */
}