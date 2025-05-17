package com.group16.stardewvalley.view.menu;

import com.group16.stardewvalley.controller.menu.HomeMenuController;
import com.group16.stardewvalley.model.crafting.Crafting;
import com.group16.stardewvalley.model.crafting.CraftingCommands;
import com.group16.stardewvalley.model.menu.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HomeMenu implements MenuInterface{
    private final HomeMenuController controller = new HomeMenuController();
    private final Crafting crafting = new Crafting();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;

        if ((matcher = GameMenuCommands.Logout.getMatcher(input)) != null) {
        }

        //crafting commands

        else if ((matcher = CraftingCommands.ShowRecipes.getMatcher(input)) != null) {
            System.out.println(crafting.showRecipes());

        } else if ((matcher = CraftingCommands.Craft.getMatcher(input)) != null) {
            System.out.println("your available recipes:\n" + crafting.showRecipes());
            System.out.println(crafting.craft(matcher.group("itemName")));

        } else if ((matcher = CraftingCommands.PlaceItem.getMatcher(input)) != null) {
            System.out.println(crafting.placeItems(matcher.group("itemName"), matcher.group("direction")));

        } else if ((matcher = CraftingCommands.CheatAddItem.getMatcher(input)) != null) {
            System.out.println(crafting.cheatAddItem(matcher.group("itemName"), Integer.parseInt(matcher.group("count"))));


        } else if ((matcher = GameMenuCommands.ChangeMenu.getMatcher(input)) != null) {

        } else if ((matcher = GameMenuCommands.ShowCurrentMenu.getMatcher(input)) != null) {


        } else if ((matcher = GameMenuCommands.Exit.getMatcher(input)) != null) {
            System.out.println(controller.exitMenu());

        } else {
            System.out.println("invalid command!");
        }
    }

}
