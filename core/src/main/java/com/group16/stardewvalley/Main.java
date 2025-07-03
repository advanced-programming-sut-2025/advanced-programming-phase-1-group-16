package com.group16.stardewvalley;


import com.badlogic.gdx.Game;
import com.group16.stardewvalley.view.AppView;

public class Main extends Game {

    @Override
    public void create() {
        setScreen(new AppView(this));
    }
}
