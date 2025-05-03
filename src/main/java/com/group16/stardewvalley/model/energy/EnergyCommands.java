package com.group16.stardewvalley.model.energy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EnergyCommands {
    SHOW_ENERGY("^\\s*energy\\s*show\\s*$"),
    SET_ENERGY("^\\s*energy\\s*set\\s*-v\\s*(?<value>-?\\d+)\\s*$"),
    ENERGY_UNLIMITED("^\\s*energy\\s*unlimited\\s*$"),
    INVENTORY_SHOW("^\\s*inventory\\s*show\\s*$"),
    INVENTORY_TRASH("^\\s*inventory\\s*trash\\s*-i\\s*(?<itemName>\\S+)" +
            "(\\s*-n\\s*(?<number>\\d+))?\\s*$"),



    private final String pattern;

    EnergyCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMAtcher(String input) {
        return Pattern.compile(this.pattern).matcher(input);
    }
}
