package com.group16.stardewvalley.controller.menu;



import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.command.GameMenuCommands;
import com.group16.stardewvalley.model.command.Menu;
import com.group16.stardewvalley.model.user.Result;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.model.map.FarmType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        for (String user : users) {
            if (GameMenuCommands.Username.getMatcher(user) == null) {
                return new Result(false, "invalid username format!");
            }
            if(getUserByUsername(user).isActiveGame()){
                return new Result(false, "user already in an active game!");
            }
        }

        ArrayList<User> gamePlayers = new ArrayList<>();
        gamePlayers.add(App.getLoggedInUser());
        gamePlayers.add(getUserByUsername(users[0]));
        gamePlayers.add(getUserByUsername(users[1]));
        gamePlayers.add(getUserByUsername(users[2]));
        Game newGame = new Game(App.getLoggedInUser(), gamePlayers);
        App.setActiveGame(newGame);
        App.games.add(newGame);
        return new Result(true, "new game created!\nnow choose your farm in turn.");
    }

//بازیکن ها بصورت نوبتی و همه از یک سیستم مزرعه ی خود را انتخاب میکنند
    public Result chooseFarm(String username, String farmNumber){
        User user = getUserByUsername(username);
        Game game = App.getActiveGame();

        if(farmNumber.matches("\\d+")){
            int farmNum = Integer.parseInt(farmNumber);

            switch (farmNum){
                case 1: game.addUserFarm(user, FarmType.big);break;
                case 2: game.addUserFarm(user, FarmType.small);break;
            }
        }else{
            return new Result(false, "invalid farm number!");
        }
        return new Result(true, "your choice have been saved!");
    }

    public Result loadGame(){
        if(App.getActiveGame() == null){
            return new Result(false, "no active game!");
        }
        Game game = App.getActiveGame();
        game.setLoader(game.getCurrentPlayer());
        return new Result(true, game.getCurrentPlayer().getUsername() + " loaded the game successfully!");

    }

    public Result exit(){
        Game game = App.getActiveGame();
        if(game.getLoader() != null && game.getCurrentPlayer() == game.getLoader()){
            //TODO save game
            App.setCurrentMenu(Menu.ExitMenu);
            return new Result(true, "bye bye");
;
        }
        if(game.getCurrentPlayer() == game.getCreator()){
            //TODO save game
            App.setCurrentMenu(Menu.ExitMenu);
            return new Result(true, "bye bye");
        }
        return new Result(false, "wrong user entered exit command. try again!");
    }

    public Result forceTerminateGame(Map<User, Boolean>votes){
        Game game = App.getActiveGame();

        boolean result = true;
        for(boolean vote : votes.values()){
            result = result & vote;
        }

        if(result){
            //terminating won the election
            App.setActiveGame(null);
            App.setCurrentMenu(Menu.GameMenu);
            //TODO; how to delete the game

        }else{
            //terminating did not win the election
            return new Result(false, "terminating the game did not win the election!");
        }


    }

    public Result nextTurn(){

    }







}
