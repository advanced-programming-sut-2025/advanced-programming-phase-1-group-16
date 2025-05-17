package com.group16.stardewvalley.model.menu;

import com.group16.stardewvalley.view.menu.CommandsInterface;

public enum GameMenuCheatCodeCommands implements CommandsInterface {
    AddSeed("cheat add seed (?<seed>.+?)"),
    AddTool("cheat add tool (?<tool>.+?)"),
    AddFertilizer("add fertilizer (?<fertilizer>.+?)"),
    ShowPosition("show position"),
    AddIngredient("add ingredient (?<ingredient>.+?)"),
    CheatAdd("^\\s*cheat\\s*add\\s*(?<count>\\d+)\\s*dollars\\s*$");


    private final String pattern;
    GameMenuCheatCodeCommands(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
