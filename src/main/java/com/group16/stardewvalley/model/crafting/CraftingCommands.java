package com.group16.stardewvalley.model.crafting;

import com.group16.stardewvalley.model.menu.CommandsInterface;

import java.util.regex.Matcher;

public enum CraftingCommands implements CommandsInterface {
    ShowRecipes("\\s*crafting\\s+show\\s+recipes\\s*"),
    Craft("\\s*crafting\\s+craft\\s+(?<itemName>\\S+)\\s*"),
    PlaceItem("\\s*place\\s+item\\s+-n\\s+(?<itemName>\\S+)\\s+-d\\s+(?<direction>\\S+)\\s*"),
    CheatAddItem("\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>\\S+)\\s+-c\\s+(?<count>\\d+)\\s*");
    private final String pattern;


    CraftingCommands(String pattern) {
        this.pattern = pattern;
    }
    @Override
    public String getPattern() {
        return pattern;
    }
}
