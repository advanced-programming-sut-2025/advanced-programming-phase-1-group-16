package com.group16.stardewvalley.view.menu;

public enum GameMenuCommands implements CommandsInterface{
    // این اولی هرچی میگیره تهش باید تریم بخوره چون داره توی اخرین گروه همه ی اسپیس های بعدی رو هم میگیره
    NewGame("^\\s*game\\s+new\\s+-u\\s*(?<usernames>.+?)$"),
    Username( "^\\s*[a-zA-Z0-9-]+\\s*$"),
    ChooseMap("^\\s*game\\s+map\\s+(?<mapNumber>\\d+)\\s*$"),
    LoadGame("^\\s*load\\s+game\\s*$" ),
    Exit("^\\s*exit\\s+game\\s*$" ),
    NextTurn("^\\s*next\\s+turn\\s*$" ),
    ForceTerminateVote("^\\s*force-terminate\\s+vote\\s*$"),
    ShowCurrentMenu("^\\s*show\\s+current\\s+menu\\s*$"),
    ExitMenu("^\\s*menu\\s+exit\\s*$"),

    Walk("^\\s*walk\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*$"),
    PrintMap("^\\s*print\\s+map\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s+-s\\s+(?<size>\\d+)\\s*$"),
    HelpReadingMap("^\\s*help\\s+reading\\s+map\\s*$"),


    //shop commands
    ShopBuildCoopBarn("^\\S*build\\s+-a\\s+(?<buildingName>\\S+)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*$"),
    ShowAllProducts("^\\s*show\\s*all\\s*products\\s*$"),
    ShowAllAvailableProducts("^\\s*show\\s*all*available\\s*products\\s*$"),
    Purchase("^\\s*purchase\\s*(?<productName>\\S+)(?:\\s+-n\\s+(?<count>\\d+))?\\s*$"),
    // relation Ship
    Meet("^\\s*meet\\s*NPC\\s*(?<NPCName>\\S+)\\s*$"),
    Gift("^\\s*gift\\s*NPC\\s*(?<NPCName>\\S+)\\s*-i\\s*(?<giftName>\\S+)\\s*$"),
    Friendship("^\\s*friendship\\s*$"),
    Talk("^\\s*talk\\s*-u\\s*(?<username>\\S+)\\s*-m\\s*(?<message>.+?)$"),
    TalkHistory("^\\s*talk\\s*$"),
    Gift("^\\s*gift\\s*-u\\s*(?<username>\\S+)\\s*-i\\s*(?<itemName>\\S+)\\s*-a\\s*(?<amount>\\d+)\\s*$"),
    GiftList("^\\s*gift\\s*list\\s*$"),
    GiftRate("^\\s*gift\\s*rate\\s*-i\\s*(?<giftNumber>\\d+)\\s*\\s*$"),
    GiftHistory("^\\s*gift\\s*history\\s*-u\\s*(?<username>\\S+)\\s*$"),
    Hug("^\\s*hug\\s*-u\\s*(?<username>\\S+)\\s*$"),
    Flower("^\\s*flower\\s*-u\\s*(?<username>\\S+)\\s*$"),
    AskMarriage("^\\s*ask\\s*marriage\\s*-u\\s*(?<username>\\S+)\\s*-r\\s*(?<ring>\\S+)\\s*$");






    private final String pattern;

    GameMenuCommands(String pattern) {
        this.pattern = pattern;
    }
    @Override
    public String getPattern() {
        return pattern;
    }
}
