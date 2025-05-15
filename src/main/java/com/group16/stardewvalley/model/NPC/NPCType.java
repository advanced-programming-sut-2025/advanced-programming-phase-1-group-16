package com.group16.stardewvalley.model.NPC;

import com.group16.stardewvalley.model.map.Farm;
import com.group16.stardewvalley.model.map.FarmType;

import java.util.List;

public enum NPCType {

    //(●’◡’●) ಠ_ಠ (ᗒᗣᗕ) ( ˘ ³˘) (•̀ᴗ•́) ʕ•ᴥ•ʔ 𓆩♡𓆪   ﮩ٨ـﮩﮩ٨ـ♡ﮩ٨ـﮩﮩ٨ـ   ﮩ٨ـﮩﮩ٨ـ🖤ﮩ٨ـﮩﮩ٨ـ  (≧◡≦) (◡‿◡✿) (✿◠‿◠) (”__”)
    // (*^ -^*) (⊙▂⊙) (∪ ◡ ∪)  (✿ ♥‿♥)

    Sebastian("Sebastian",
            List.of("wool", "pumpkin pie", "pizza"),
            List.of("Delivery of 50 units of iron", "Delivery pumpkin pie", "Delivery 150 units of stone"),
            List.of("2 diamond", "5000 coin", "50 quartz"),
            List.of("You're still here.Weird how fast I got used that! ^^",   //morning / any season / sunny
                    "The snow's getting heavy.want to skip everything and stay in together",  // afternoon / winter / snowy
                    "I like walking with you in weather like this.. wind our ears no one else around...", // evening / fall /windy
                    "Let’s ride out to the ridge. Just us, the stars, and a few too many mosquitoes."),  // night / fall / windy
            new Farm(FarmType.small, "SebastianFarm")),
    Abigail("Abigail",
            List.of("stone", "iron ore", "coffee"),
            List.of("Delivery of a gold bar", "Delivery a pumpkin pie", "Delivery of 50 pieces of wheat"),
            List.of("1 level friendship", "500 gold coin", "Automatic iridium sprinkler"),
            List.of(""),
            new Farm(FarmType.small, "AbigailFarm")),
    Harvey("Harvey",
            List.of("coffee", "pickle", "liquor"),
            List.of("Delivering 12 of a desired plant", "Delivery of a salmon", "Delivery of a bottle of liquor"),
            List.of("750 coin", "1 level friendship", "5 salad"),
            List.of(""),
            new Farm(FarmType.small, "HarveyFarm")),
    Leah("Leah",
            List.of("salad", "grape", "liquor"),
            List.of("Delivery of 10 hardwoods", "Delivery of a salmon", "Delivery of a bottle of liquor"),
            List.of("500 coin", "dinner salmoon recipe", "3 deluxe scarecrow"),
            List.of(""),
            new Farm(FarmType.small, "LeahFarm")),
    Robin("Robin",
            List.of("spaghetti", "wood", "iron ingot"),
            List.of("Delivery 80 wood", "Delivery 10 iron ingot", "Delivery 1000 wood"),
            List.of("1000 coin", "3 beeHouse", "25000 coin"),
            List.of(""),
            new Farm(FarmType.small, "RobinFarm"));



    private String name;
    private final List<String> favoriteItems;
    private final List<String> requests;
    private final List<String> rewards;
    private final List<String> dialogs;
    private final Farm farm;

    NPCType(String name,
            List<String> favoriteItems,
            List<String> requests,
            List<String> rewards,
            List<String> dialogs,
            Farm farm) {
        this.name = name;
        this.favoriteItems = favoriteItems;
        this.requests = requests;
        this.rewards = rewards;
        this.dialogs = dialogs;
        this.farm = farm;
    }

    public String getName() {
        return name;
    }

    public

}
