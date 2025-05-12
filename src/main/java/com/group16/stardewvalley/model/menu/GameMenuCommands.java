package com.group16.stardewvalley.model.menu;

public enum GameMenuCommands implements CommandsInterface{
    NewGame("\\s*game\\s+new\\s+-u\\s*(?<usernames>.+?)"),
    Username( "\\s*[a-zA-Z0-9-]+\\s*"),
    ChooseMap("\\s*game\\s+map\\s+(?<mapNumber>\\d+)\\s*"),
    LoadGame("\\s*load\\s+game\\s*" ),
    Exit("\\s*exit\\s+game\\s*" ),
    NextTurn("\\s*next\\s+turn\\s*" ),
    ForceTerminateVote("\\s*force-terminate\\s+vote\\s*"),
    ShowCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),
    ExitMenu("\\s*menu\\s+exit\\s*"),

    Walk("\\s*walk\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*"),
    PrintMap("\\s*print\\s+map\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s+-s\\s+(?<size>\\d+)\\s*"),
    HelpReadingMap("\\s*help\\s+reading\\s+map\\s*"),

    //Main menu commands
    ChangeMenu("\\s*menu\\s+enter\\s+(?<MenuName>.+)\\s*"),
    Logout("\\s*user\\s+logout\\s*"),

    //timeDate commands
    Time("time"),
    Date("date"),
    DateTime("datetime"),
    DayOfWeek("day of the week"),
    CheatAdvanceTime("cheat advance time\\s*(?<amount>\\d+)\\s*h"),
    CheatAdvanceDate("cheat advance date\\s*(?<amount>\\d+)\\s*d"),

    //shop commands
    ShopBuildCoopBarn("\\S*build\\s+-a\\s+(?<buildingName>\\S+)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*");


    //


    private final String pattern;

    GameMenuCommands(String pattern) {
        this.pattern = pattern;
    }
    @Override
    public String getPattern() {
        return pattern;
    }
}

