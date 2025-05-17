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
    Season("season"),

    //shop commands
    ShopBuildCoopBarn("\\S*build\\s+-a\\s+(?<buildingName>\\S+)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*"),
    BuyAnimal("^buy\\s+animal\\s+-a\\s+(?<animal>\\S+)\\s+-n\\s+(?<name>\\S+)\\s*$ "),
    ShowAllProducts("^\\s*show\\s*all\\s*products\\s*$"),
    ShowAllAvailableProducts("^\\s*show\\s*all*available\\s*products\\s*$"),
    Purchase("^\\s*purchase\\s*(?<productName>\\S+)(?:\\s+-n\\s+(?<count>\\d+))?\\s*$"),

    CraftInfo("\\s*craft info -n (?<name>.+?)\\s*"),
    TreeInfo("tree info -n (?<name>.+?)\\s*"),
    ForagingInfo("foraging info -n (?<name>.+?)\\s*"),
    PlantSeed("plant -s (?<seed>.+?) -d (?<dir>.+?)"),
    ShowPlant("show plant -l (?<x>\\d+),\\s*(?<y>\\d+)"),
    Fertilize("fertilize -f (?<fertilizer>.+?) -d (?<dir>.+?)"),
    HowMuchWater("how much water"),
    PutFood("cooking refrigerator put (?<food>.+?)"),
    PickFood("cooking refrigerator pick (?<food>.+?)"),
    CookingRecipes("cooking show recipes"),
    PrepareFood("cooking prepare (?<food>.+?)"),
    EatFood("eat (?<food>.+?)"),

    //Animals
    Pet("\\s*pet\\s+-n\\s+(?<name>\\S+)\\s*"),
    CheatSetAnimalFriendship("\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<name>\\S+)\\s+-c\\s+(?<count>\\d+)\\s*"),
    ShowAnimalInfo("\\s*animals\\s*"),
    ShepherdAnimals("^\\s*shepherd\\s+animals\\s+-n\\s+(?<name>\\S+)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*$"),
    FeedHay("\\s*feed\\s+hay\\s+-n\\s+(?<name>\\S+)\\s*"),
    AnimalProduces("\\s*produces\\s*"),
    CollectProduct("\\s*collect\\s+produce\\s+-n\\s+(?<name>\\S+)\\s*"),
    SellAnimal("\\s*sell\\s+animal\\s+-n\\s+(?<name>\\S+)\\s*"),
    Fishing("fishing -p (?<fishingPole>.+?)"),

    // relationship commands
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

