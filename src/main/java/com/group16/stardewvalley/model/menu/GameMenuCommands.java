package com.group16.stardewvalley.model.menu;

public enum GameMenuCommands implements CommandsInterface{
    NewGame("\\s*game\\s+new\\s+-u\\s*(?<usernames>.+?)"),
    Username( "\\s*[a-zA-Z0-9-]+\\s*"),
    ChooseMap("\\s*game\\s+map\\s+(?<map_number>\\S+)\\s*"),
    LoadGame("\\s*load\\s+game\\s*" ),
    Exit("\\s*exit\\s+game\\s*" ),
    NextTurn("\\s*next\\s+turn\\s*" ),
    ForceTerminateVote("\\s*force-terminate\\s+vote\\s*"),

    Walk("\\s*walk\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*"),
    PrintMap("\\s*print\\s+map\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s+(?<size>\\d+)\\s*"),
    HelpReadingMap("\\s+help\\s+reading\\s+map\\s*"),
    CraftInfo("\\s*craftinfo -n (?<name>.+?)\\s*"),
    PlantSeed("plant -s (?<seed>.+?) -d (?<dir>.+?)"),
    ShowPlant("show plant -l (?<x>\\d+),\\s*(?<y>\\d+)");


    private final String pattern;

    GameMenuCommands(String pattern) {
        this.pattern = pattern;
    }
    @Override
    public String getPattern() {
        return pattern;
    }
}