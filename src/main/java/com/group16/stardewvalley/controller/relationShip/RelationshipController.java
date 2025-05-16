package com.group16.stardewvalley.controller.relationShip;

import com.group16.stardewvalley.model.NPC.NPC;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.user.Player;

import java.util.Map;
import java.util.regex.Matcher;

public class RelationshipController {

    private final Game game = App.getActiveGame();

    public Result showFriendship() {
        Player currentPlayer = game.getCurrentPlayer();
        Map<Player, Integer> levels = currentPlayer.getInteractionsLevel();
        Map<Player, Integer> scores = currentPlayer.getInteractionScore();
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Player, Integer> entry : levels.entrySet()) {
            Player player = entry.getKey();
            Integer level = entry.getValue();
            Integer score = scores.get(player);

            sb.append("player name: ");
            sb.append(player.getName());
            sb.append(" friendship score: ");
            sb.append(score);
            sb.append(" friendship level: ");
            sb.append(level);

        }
        return new Result(true ,sb.toString());
    }
    public Result meet(Matcher matcher) {
        String username = matcher.group("username");
        String message = matcher.group("message");
        if ()
    }

    public Result meetNPC(Matcher matcher) {
        String NPCName = matcher.group("NPCName");
        Player currentPlayer = game.getCurrentPlayer();
        NPC targetNPC = game.getNPCByName(NPCName);
        TimeDate currentDate = App.getActiveGame().getTimeDate();

        // این NPC اصلا وجود نداشته باشد
        if (targetNPC == null) {
            return new Result(false, "This person seems to exist only in legends... " +
                    "or maybe your imagination?");
        }

        // در نزدیک ۸ خانه ی مجاور NPC باشد
        if (!game.isAdjacent(currentPlayer.getPosition(), targetNPC.getNPCPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        // بتواند با NPC ارتباط بگیرد
        boolean hadInteractionToday;
        hadInteractionToday = currentPlayer.getInteractionWith(targetNPC).isMetToday();
        if (!hadInteractionToday) {
            currentPlayer.getInteractionWith(targetNPC).increaseFriendshipLevelScore(20);
            currentPlayer.getInteractionWith(targetNPC).setMetToday(true);
        }

        String answer;
        if (targetNPC.getName().equalsIgnoreCase("Sebastian")) {
            answer = targetNPC.getNpcType().getDialogueForSebastian(currentPlayer.
                            getInteractionWith(targetNPC).getFriendshipNPCLevel(),
                    currentDate.getSeason(), App.getActiveGame().getWeatherCondition(),
                    currentDate.getHour());
        } else if (targetNPC.getName().equalsIgnoreCase("Abigail")) {
            answer = targetNPC.getNpcType().getDialogueForAbigail(currentPlayer.
                            getInteractionWith(targetNPC).getFriendshipNPCLevel(),
                    currentDate.getSeason(), App.getActiveGame().getWeatherCondition(),
                    currentDate.getHour());
        } else if (targetNPC.getName().equalsIgnoreCase("Harvey")) {
            answer = targetNPC.getNpcType().getDialogueForHarvey(currentPlayer.
                            getInteractionWith(targetNPC).getFriendshipNPCLevel(),
                    currentDate.getSeason(), App.getActiveGame().getWeatherCondition(),
                    currentDate.getHour());
        } else if (targetNPC.getName().equalsIgnoreCase("Leah")) {
            answer = targetNPC.getNpcType().getDialogueForLeah(currentPlayer.
                            getInteractionWith(targetNPC).getFriendshipNPCLevel(),
                    currentDate.getSeason(), App.getActiveGame().getWeatherCondition(),
                    currentDate.getHour());
        } else if (targetNPC.getName().equalsIgnoreCase("Robin")) {
            answer = targetNPC.getNpcType().getDialogueForRobin(currentPlayer.
                            getInteractionWith(targetNPC).getFriendshipNPCLevel(),
                    currentDate.getSeason(), App.getActiveGame().getWeatherCondition(),
                    currentDate.getHour());
        } else {
            answer = "HAHAH";
        }

        return new Result(true, answer);

    }

    public Result giftNPC(Matcher matcher) {
        String NPCName = matcher.group("NPCName");
        String giftName = matcher.group("giftName");
        Player currentPlayer = game.getCurrentPlayer();
        NPC targetNPC = game.getNPCByName(NPCName);
        TimeDate currentDate = App.getActiveGame().getTimeDate();

        // این NPC اصلا وجود نداشته باشد
        if (targetNPC == null) {
            return new Result(false, "This person seems to exist only in legends... " +
                    "or maybe your imagination?");
        }

        // در نزدیک ۸ خانه ی مجاور NPC باشد
        if (!game.isAdjacent(currentPlayer.getPosition(), targetNPC.getNPCPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        // اگر ابزار الات باشد
        if (isTool(giftName)) {
            return new Result(false, "You can't gift tools to people. Try something else!");
        }

        //بتواند با ان ارتباط بگیرد
        boolean hadInteractionToday;
        hadInteractionToday = currentPlayer.getInteractionWith(targetNPC).isGiftedToday();
        if (!hadInteractionToday) {
            currentPlayer.getInteractionWith(targetNPC).increaseFriendshipLevelScore(50);
            currentPlayer.getInteractionWith(targetNPC).setMetToday(true);
            // اگر هدیه مورد علاقه ی فرد باشد :
            if (targetNPC.getNpcType().isFavorite(giftName)) {
                currentPlayer.getInteractionWith(targetNPC).increaseFriendshipLevelScore(200);
            }
        }

        return new Result(false, "Gift given successfully! They seem pleased!");
    }


    private boolean isTool(String name) {
        if (name.toLowerCase().contains("axe") || name.toLowerCase().contains("hoe") ||
        name.toLowerCase().contains("milk pail") || name.toLowerCase().contains("pickaxe") ||
        name.toLowerCase().contains("scythe") || name.toLowerCase().contains("shear")) {
            return true;
        }
        return false;
    }
}
