package com.group16.stardewvalley.view;

import com.group16.stardewvalley.controller.trade.TradeController;
import com.group16.stardewvalley.model.menu.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeView {

    private final TradeController controller = new TradeController();
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;

        if ((matcher = GameMenuCommands.Start_Trade.getMatcher(input)).matches()) {
            System.out.println(controller.startTrade());
        }
    }
}
