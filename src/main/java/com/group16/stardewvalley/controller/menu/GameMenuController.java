package com.group16.stardewvalley.controller.menu;



import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.menu.GameMenuCommands;
import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.model.map.Farm;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.model.map.FarmType;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static com.group16.stardewvalley.model.user.User.getUserByUsername;


public class GameMenuController {

    public Result newGame(String input){
        if(input == null || input.isEmpty()){
            return new Result(false, "empty usernames!");
        }

        String[] users = input.split("\\s+");

        if(users.length > 3){
            return new Result(false, "too many usernames!");
        }

        for (String username : users) {
            if (GameMenuCommands.Username.getMatcher(username) == null) {
                return new Result(false, "invalid username format!");
            }
            User user = getUserByUsername(username);
            if(user == null){
                return new Result(false, "user not found!");
            }
            if(user.isActiveGame()){
                return new Result(false, "username already in an active game!");
            }
        }

        ArrayList<Player> gamePlayers = new ArrayList<>();
        gamePlayers.add(new Player(App.getLoggedInUser()));
        gamePlayers.add(new Player(getUserByUsername(users[0])));
        gamePlayers.add(new Player(getUserByUsername(users[1])));
        gamePlayers.add(new Player(getUserByUsername(users[2])));
        Game newGame = new Game(new Player(App.getLoggedInUser()), gamePlayers);
        App.setActiveGame(newGame);
        App.games.add(newGame);
        return new Result(true, "new game created!\nnow choose your farm in turn.");
    }

//بازیکن ها بصورت نوبتی و همه از یک سیستم مزرعه ی خود را انتخاب میکنند
    public Result chooseFarm(Player player, String farmNumber){
        Game game = App.getActiveGame();

        if(farmNumber.matches("\\d+")){
            int farmNum = Integer.parseInt(farmNumber);

            return switch (farmNum) {
                case 1 -> {
                    player.setFarm(new Farm(FarmType.small));
                    yield new Result(true, "small farm has been chosen!");
                }
                case 2 -> {
                    player.setFarm(new Farm(FarmType.big));
                    yield new Result(true, "big farm has been chosen!");
                }
                default -> new Result(false, "farm number must be between 1 and 2");
            };

        }else{
            return new Result(false, "invalid farm number!");
        }
    }

    public void randomItems(Farm farm){
        Random random = new Random();
        int totalTiles = farm.getType().getHeight() * farm.getType().getWidth();

        // تعداد رندم آیتم‌ها (مثلاً بین 5 تا 20 درصد کل تایل‌ها)
        int itemCount = random.nextInt(totalTiles / 5) + totalTiles / 20;

        for (int k = 0; k < itemCount; k++) {
            int i = random.nextInt(farm.getType().getHeight());           // ردیف رندم
            int j = random.nextInt(farm.getType().getWidth());        // ستون رندم
            if (farm.getType().getTiles()[j][i] == TileType.Ground) {
                farm.getType().getTiles()[j][i] = TileType.Tree;
            }
        }
        for (int k = 0; k < itemCount; k++) {
            int i = random.nextInt(farm.getType().getHeight());           // ردیف رندم
            int j = random.nextInt(farm.getType().getWidth());        // ستون رندم
            if (farm.getType().getTiles()[j][i] == TileType.Ground) {
                farm.getType().getTiles()[j][i] = TileType.Stone;
            }
        }
        for (int k = 0; k < itemCount; k++) {
            int i = random.nextInt(farm.getType().getHeight());           // ردیف رندم
            int j = random.nextInt(farm.getType().getWidth());        // ستون رندم
            if (farm.getType().getTiles()[j][i] == TileType.Ground) {
                farm.getType().getTiles()[j][i] = TileType.Forage;
            }
        }
    }


    public Result loadGame(){
        if(App.getActiveGame() == null){
            return new Result(false, "no active game!");
        }
        Game game = App.getActiveGame();
        game.setLoader(game.getCurrentPlayer());
        return new Result(true, game.getCurrentPlayer().getUser().getUsername() + " loaded the game successfully!");

    }

    public Result exit(){
        Game game = App.getActiveGame();
        if(game.getLoader() != null && game.getCurrentPlayer() == game.getLoader()){
            //TODO save game
            App.setCurrentMenu(Menu.ExitMenu);
            return new Result(true, "bye bye");

        }
        if(game.getCurrentPlayer() == game.getCreator()){
            //TODO save game
            App.setCurrentMenu(Menu.ExitMenu);
            return new Result(true, "bye bye");
        }
        return new Result(false, "wrong user entered exit command. try again!");
    }

    public Result forceTerminateGame(Map<Player, Boolean>votes){
        Game game = App.getActiveGame();

        boolean result = true;
        for(boolean vote : votes.values()){
            result = result & vote;
        }

        if(result){
            //terminating won the election

            App.setActiveGame(null);
            App.setCurrentMenu(Menu.GameMenu);
            return new Result(true, "terminated game!");
            //TODO; how to delete the game

        }else{
            //terminating did not win the election
            return new Result(false, "terminating the game did not win the election!");
        }


    }

//next turn have been handled in Game class


    public Result exitMenu(){
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "you are in the main menu!");
    }






}
