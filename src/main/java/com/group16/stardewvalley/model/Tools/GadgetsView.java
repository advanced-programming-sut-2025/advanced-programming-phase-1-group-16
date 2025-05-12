package com.group16.stardewvalley.model.Tools;

import com.group16.stardewvalley.controller.Tools.GadgetController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GadgetsView {

    private final GadgetController controller = new GadgetController();

    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;

        if ((matcher = GadgetsCommands.EQUIP.getMatcher(input)).matches()) {
            System.out.println(controller.equip(matcher));
        } else if ((matcher = GadgetsCommands.AVAILABLE_TOOLS.getMatcher(input)).matches()) {
            System.out.println(controller.showAvailableTools());
        } else if ((matcher = GadgetsCommands.UPGRADE_TOOLS.getMatcher(input)).matches()) {
            System.out.println(controller.upgradeTool(matcher));
        } else if ((matcher = GadgetsCommands.USE_TOOL.getMatcher(input)).matches()) {
            System.out.println(controller.useTool(matcher));
        } else {
            System.out.println("chert nanvis");
        }
    }

}
