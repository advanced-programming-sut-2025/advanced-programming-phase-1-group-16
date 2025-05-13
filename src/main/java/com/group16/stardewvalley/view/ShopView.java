/*
package com.group16.stardewvalley.view;

import com.group16.stardewvalley.model.Shops.CarpentersShop;
import com.group16.stardewvalley.model.crafting.Crafting;
import com.group16.stardewvalley.model.crafting.CraftingCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopView {
    private final CarpentersShop carpentersShop = new CarpentersShop();


    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;

        if ((matcher = CraftingCommands.ShowRecipes.getMatcher(input)) != null) {
            System.out.println(carpentersShop.buildCoopAndBarn(matcher.group("buildingName"), Integer.parseInt(matcher.group("x")),
                    Integer.parseInt(matcher.group("y"))));

        }else if((matcher = CraftingCommands.CheatAddItem.getMatcher(input)) != null) {
            System.out.println((matcher.group("itemName"), Integer.parseInt(matcher.group("count"))));

        }else {
            System.out.println("invalid command!");
        }
    }


}
 */
