package com.group16.stardewvalley.controller.menu;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.app.GameState;
import com.group16.stardewvalley.model.command.GameMenuCommands;
import com.group16.stardewvalley.model.map.FarmType;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.User;

import java.util.ArrayList;
import java.util.Objects;

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
        App.getActiveGame().setGameState(GameState.WAITING_FOR_MAP_SELECTION);
        return new Result(true, "new game created!\nnow choose your farm in turn.");
    }

    //بازیکن ها بصورت نوبتی و همه از یک سیستم مزرعه ی خود را انتخاب میکنند
    public Result chooseMap(String username, String farmNumber){
        User user = getUserByUsername(username);
        Game game = App.getActiveGame();

        if(farmNumber.matches("\\d+")){
            int farmNum = Integer.parseInt(farmNumber);

            switch (farmNum){
                case 1: game.addUserFarm(user, FarmType.small);break;
                case 2: game.addUserFarm(user, FarmType.big);break;
            }
        }else{
            return new Result(false, "invalid farm number!");
        }
        return new Result(true, "your choice have been saved!");
    }

    public void loadGame(){}

    public Result exit(){}
    private boolean isUserTheMainUser(){}
    private boolean isMainUserTurn(){}

    public Result terminateGame(boolean user1vote, boolean user2vote, boolean user3vote){}

    public Result nextTurn(){}

}
