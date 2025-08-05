package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class CarpenterMenu extends Window {
    public CarpenterMenu(Skin skin) {
        super("Carpenter's Shop", skin);
        setSize(600, 400);
        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - getHeight() / 2f);

        Label label = new Label("Welcome to the Carpenter's Shop!", skin);
        add(label).center().expand();
    }
}
