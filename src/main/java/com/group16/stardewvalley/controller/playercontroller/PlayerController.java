package com.group16.stardewvalley.controller.playercontroller;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.user.Player;

import java.util.regex.Matcher;

public class PlayerController {

    public Result showCoin() {
        Player current = App.getActiveGame().getCurrentPlayer();
        return new Result(true, "coin : " + current.getCoin());
    }

    public Result cheatAddCoin(Matcher matcher) {
        String amountStr = matcher.group("amount");
        int amount = Integer.parseInt(amountStr);
        Player current = App.getActiveGame().getCurrentPlayer();
        current.setCoin(amount);
        return new Result(true, "coin set successfully new coin = " + current.getCoin());
    }

}


