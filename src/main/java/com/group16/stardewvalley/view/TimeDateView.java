package com.group16.stardewvalley.view;

import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.menu.GameMenuCommands;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.view.menu.MenuInterface;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TimeDateView implements MenuInterface {
    private final TimeDate timeDate = new TimeDate(App.getActiveGame());

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;

        if ((matcher = GameMenuCommands.Time.getMatcher(input)) != null) {
            System.out.println(timeDate.getTime());

        }else if ((matcher = GameMenuCommands.Date.getMatcher(input)) != null) {
            System.out.println(timeDate.getDate());

        }else if((matcher = GameMenuCommands.DateTime.getMatcher(input)) != null ){
            System.out.println(timeDate.getDateTime());

        }else if((matcher = GameMenuCommands.DayOfWeek.getMatcher(input)) != null ){
            System.out.println(timeDate.getDayOfWeek());
        
        } else if ((matcher = GameMenuCommands.CheatAdvanceTime.getMatcher(input)) != null ) {
            System.out.println(timeDate.advanceTimeCheat(Integer.parseInt(matcher.group("amount"))));

        } else if ((matcher = GameMenuCommands.CheatAdvanceDate.getMatcher(input)) != null ) {
            System.out.println(timeDate.advanceDateCheat(Integer.parseInt(matcher.group("amount"))));

        } else{
            System.out.println("invalid command!");
        }
    }
}
