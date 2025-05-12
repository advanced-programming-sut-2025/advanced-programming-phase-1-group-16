package com.group16.stardewvalley.view.crafting;

import com.group16.stardewvalley.model.crafting.Crafting;
import com.group16.stardewvalley.model.crafting.CraftingCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CraftingView {
    private final Crafting controller = new Crafting();


    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;

        if ((matcher = CraftingCommands.ShowRecipes.getMatcher(input)) != null) {
            System.out.println(controller.showRecipes());

        } else if ((matcher = CraftingCommands.Craft.getMatcher(input)) != null) {
            System.out.println("your available recipes:\n" + controller.showRecipes());
            System.out.println(controller.craft(matcher.group("itemName")));

        }else if((matcher = CraftingCommands.PlaceItem.getMatcher(input)) != null) {
            System.out.println(controller.placeItems(matcher.group("itemName"), matcher.group("direction")));

        }else if((matcher = CraftingCommands.CheatAddItem.getMatcher(input)) != null) {
            System.out.println(controller.cheatAddItem(matcher.group("itemName"), Integer.parseInt(matcher.group("count"))));

        }else {
            System.out.println("invalid command!");
        }
    }}

