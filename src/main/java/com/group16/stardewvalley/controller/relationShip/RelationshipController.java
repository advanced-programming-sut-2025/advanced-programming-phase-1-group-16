package com.group16.stardewvalley.controller.relationShip;

import com.group16.stardewvalley.model.NPC.NPC;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.items.Item;
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
        Player currentPlayer = game.getCurrentPlayer();
        Player targetPlayer = game.getPlayerByUsername(username);
        // این شخص کلا وجود نداشته باشد
        if (targetPlayer == null) {
            return new Result(false, "This person seems to exist only in legends... " +
                    "or maybe your imagination?");
        }

        //در ۸ خانه ی مجاور نباشد
        if (!game.isAdjacent(currentPlayer.getPosition(), targetPlayer.getPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        // بتواند صحبت کند
        boolean hadTalkedToday = currentPlayer.getInteractionWith(targetPlayer).isTalked();
        String newStr = message + currentPlayer.getName();
        currentPlayer.getInteractionWith(targetPlayer).addDialogue(newStr);
        if (!currentPlayer.getInteractionWith(targetPlayer).isTalked()) {
            currentPlayer.getInteractionWith(targetPlayer).increaseFriendshipLevelScore(20);
        }
        // TODO ارسال نوتیفیکیشن

        return new Result(true, "Message sent successfully!");
    }

    public Result showTalkHistory(Matcher matcher) {
        String username = matcher.group("username");
        Player targetPlayer = game.getPlayerByUsername(username);
        Player player = game.getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        for (String line : player.getInteractionWith(targetPlayer).getDialogueHistory()) {
            result.append(line).append("\n");
        }
        return new Result(true, result.toString());

    }

    public Result trading(Matcher matcher) {
        // این چرا هیچی نداره اصلا چطور باید پیاده بشه خب
        //TODO
    }

    public Result gift(Matcher matcher) {
        String username = matcher.group("username");
        String itemName = matcher.group("itemName");
        String amountStr = matcher.group("amount");
        int amount = Integer.parseInt(amountStr);
        Player currentPlayer = game.getCurrentPlayer();
        Player targetPlayer = game.getPlayerByUsername(username);


        // سطح یک نباشند
        if (currentPlayer.getInteractionWith(targetPlayer).getFriendshipLevel() < 1) {
            return new Result(false, "Friendship level too low! You can't exchange gifts yet.");
        }

        // در نزدیک هم نباشند
        if (!game.isAdjacent(currentPlayer.getPosition(), targetPlayer.getPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        Item targetItem = currentPlayer.getInventory().getItemByName(itemName);
        if (targetItem == null) {
            return new Result(false, "You don't have enough of this item in your inventory!");
        }

        // به مقدار کافی در اینونتوری اش نداشته باشد
        if (currentPlayer.getInventory().getNumberOfItem(targetItem) < amount) {
            return new Result(false, "You don't have enough of this item in your inventory!");
        }

        // بتواند هدیه بدهد:
        targetPlayer.getInventory().addItem(targetItem, amount);
       handleRospond(matcher, targetPlayer, itemName);
       return new Result(true, "");

    }

    public Result hug(Matcher matcher) {
        String username = matcher.group("username");
        Player targetPlayer = game.getPlayerByUsername(username);
        Player currentPlayer = game.getCurrentPlayer();

        // سطح دو نباشند
        if (currentPlayer.getInteractionWith(targetPlayer).getFriendshipLevel() < 2) {
            return new Result(false, "Friendship level too low! You can't exchange gifts yet.");
        }

        // در نزدیک هم نباشند
        if (!game.isAdjacent(currentPlayer.getPosition(), targetPlayer.getPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        if (!currentPlayer.getInteractionWith(targetPlayer).isHugged()) {
            currentPlayer.getInteractionWith(targetPlayer).increaseFriendshipLevelScore(60);
        }
        // بتواند بغل کند
        return new Result(true, "Hug attack successful! +1 Happiness! ʕ•ᴥ•ʔ");


    }

    public Result flower(Matcher matcher) {
        String username = matcher.group("username");
        Player player = game.getCurrentPlayer();
        Player targetPlayer = game.getPlayerByUsername(username);
        // کنار هم نباشند
        if (!game.isAdjacent(player.getPosition(), targetPlayer.getPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        //گل در اینونتوری نباشد
        if (player.getInventory().getItemByName("flower") == null) {
            return new Result(false, "You don't have enough of this item in your inventory!");
        }

        // سطح دو نباشند
        if (player.getInteractionWith(targetPlayer).getFriendshipLevel() < 2) {
            return new Result(false, "Friendship level too low! You can't exchange gifts yet.");
        }

        Item flower = player.getInventory().getItemByName("flower");
        // گل بدهد
        targetPlayer.getInventory().addItem(flower, 1);
        player.getInventory().removeItem(flower, 1);
        player.getInteractionWith(targetPlayer).setFriendshipLevel(3);
        targetPlayer.getInteractionWith(player).setFriendshipLevel(3);
        return new Result(true, "Their cheeks flush pink as they accept the flowers." +
                " 'It's beautiful... thank you!");

    }

    private Result handleRospond(Matcher matcher, Player targetPlayer, String giftName) {
        String rateStr = matcher.group("giftNumber");
        int rate = Integer.parseInt(rateStr);
        Player currentPlayer = game.getCurrentPlayer();
        int addingScore = (rate - 3) * 30 + 15;
        currentPlayer.getInteractionWith(targetPlayer).increaseFriendshipLevelScore(addingScore);
        targetPlayer.getInteractionWith(currentPlayer).increaseFriendshipLevelScore(addingScore);
        currentPlayer.getInteractionWith(targetPlayer).addGift(giftName);
        targetPlayer.getInteractionWith(currentPlayer).addGift(giftName);
        return new Result(true, "Gift delivered successfully!");
    }

    public Result askMarriage(Matcher matcher) {
        String username = matcher.group("username");
        String ringName = matcher.group("ring");
        Player player = game.getCurrentPlayer();
        Player targetPlayer = game.getPlayerByUsername(username);

        // کنار هم نباشند
        if (!game.isAdjacent(player.getPosition(), targetPlayer.getPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        // سطح سه نباشند
        if (player.getInteractionWith(targetPlayer).getFriendshipLevel() < 3) {
            return new Result(false, " 'Not enough hearts!");
        }


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
