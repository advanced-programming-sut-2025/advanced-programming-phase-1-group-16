package com.group16.stardewvalley.model.command;


public enum MainMenuCommands implements CommandsInterface {

    ChangeMenu("\\s*menu\\s+enter\\s+(?<MenuName>\\S+)\\s*"),

    Logout("\\s*user\\s+logout\\s*");

    private final String pattern;

    MainMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }


}
