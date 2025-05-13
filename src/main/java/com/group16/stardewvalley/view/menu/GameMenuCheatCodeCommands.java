package com.group16.stardewvalley.view.menu;

public enum GameMenuCheatCodeCommands implements CommandsInterface{
    CheatAdd("^\\s*cheat\\s*add\\s*(?<count>\\d+)\\s*dollars\\s*$");


    private final String pattern;
    GameMenuCheatCodeCommands(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
