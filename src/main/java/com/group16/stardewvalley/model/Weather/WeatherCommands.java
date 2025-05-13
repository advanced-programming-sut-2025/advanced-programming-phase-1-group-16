package com.group16.stardewvalley.model.Weather;
import java.util.regex.*;

public enum WeatherCommands {
    SHOW_WEATHER("^\\s*weather\\s*$"),
    FORECAST("^\\s*weather\\s*forecast\\s*$") ,
    CHEAT_WEATHER_SET("^\\s*cheat\\s*weather\\s*set\\s*(?<Type>\\S+)\\s*$"),
    BUILD_GREENHOUSE("^\\s*greenhouse\\s*build\\s*$");


    private final String pattern;

    WeatherCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        return Pattern.compile(this.pattern).matcher(input);
    }
}
