package com.group16.stardewvalley.model.command;

public enum GameMenuCommands implements CommandsInterface{
    NewGame("\\s*game\\s+new\\s+-u\\s*(?<usernames>.+?)"),
    Username( "\\s*[a-zA-Z0-9-]+\\s*"),
    ChooseMap("\\s*game\\s+map\\s+(?<map_number>\\S+)\\s*"),
    LoadGame(""),
    Exit(""),
    NextTurn(""),

    Walk(""),
    PrintMap(""),
    HelpReadingMap("");

    private final String pattern;

    GameMenuCommands(String pattern) {
        this.pattern = pattern;
    }
    @Override
    public String getPattern() {
        return pattern;
    }
}
