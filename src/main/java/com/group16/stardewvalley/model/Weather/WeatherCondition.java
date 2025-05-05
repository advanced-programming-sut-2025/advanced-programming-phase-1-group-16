package com.group16.stardewvalley.model.Weather;

import Weather.Season;

public class WeatherCondition {
    SUNNY("Sunny", new Season[]{Season.SPRING, Season.SUMMER, Season.FALL, Season.WINTER}),
    RAINY("Rainy", new Season[]{Season.SPRING, Season.SUMMER, Season.FALL}),
    STORM("Storm", new Season[]{Season.SPRING, Season.SUMMER, Season.FALL}),
    SNOWY("Snowy", new Season[]{Season.WINTER});


}
