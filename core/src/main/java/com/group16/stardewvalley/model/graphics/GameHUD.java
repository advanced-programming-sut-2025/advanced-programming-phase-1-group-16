package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.BuffType;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.user.Player;

public class GameHUD {
    private Table rootTable;
    private Label timeLabel;
    private Label energyLabel;
    private Image buffIcon;
    private Skin skin;

    public GameHUD(Stage stage, Skin skin) {
        this.skin = skin;

        rootTable = new Table();
        rootTable.top().left().pad(40);
        rootTable.setFillParent(true);

        timeLabel = new Label("Day 1 - 6:00 AM", skin);
        energyLabel = new Label("Energy: 100", skin);

        buffIcon = new Image(); // بعداً texture می‌دی

        rootTable.add(timeLabel).left().padRight(20);
        rootTable.add(energyLabel).left().padRight(20);
        rootTable.add(buffIcon).size(64, 64).left();

        stage.addActor(rootTable);
    }

    public void updateHUD() {
        TimeDate td = App.getActiveGame().getTimeDate();
        Player player = App.getActiveGame().getCurrentPlayer();

        timeLabel.setText(td.getDateTime());
        energyLabel.setText("Energy: " + player.getEnergy());

        BuffType buff = player.getBuffer();
        if (buff != BuffType.NONE) {
            buffIcon.setDrawable(new TextureRegionDrawable(
                new TextureRegion(GameAssetManager.getGameAssetManager().getTexture(buff.getTexturePath()))
            ));
        } else {
            buffIcon.setDrawable(null);
        }
    }
}

