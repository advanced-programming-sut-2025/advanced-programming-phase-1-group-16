package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.time.Season;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.weather.WeatherCondition;

public class ClockHUD {
    private final Stage stage;
    private final Table table;
    private final Skin skin;

    private final Label timeLabel;
    private final Label dateLabel;
    private final Label dayLabel;
    private final Label moneyLabel;
    private final Image background;
    private final Image weatherIcon;
    private final Image seasonIcon;

    private final Texture clockTexture;
    private final TextureRegion[][] regions;

    public ClockHUD(Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;

        // === Load Spritesheet ===
        clockTexture = new Texture(Gdx.files.internal("sprites/Clock.png"));
        regions = TextureRegion.split(clockTexture, 16, 16); // assuming each icon is 16x16

        // === UI Elements ===
        background = new Image(clockTexture); // full image
        timeLabel = new Label("", skin);
        dateLabel = new Label("", skin);
        dayLabel = new Label("", skin);
        moneyLabel = new Label("", skin);
        weatherIcon = new Image();
        seasonIcon = new Image();

        // === Container Table ===
        table = new Table();
        table.top().right();
        table.setFillParent(true);

        Table content = new Table();
        content.add(background).size(236, 288).row();
        content.add(dayLabel).padTop(-250).padLeft(50).left().row();
        content.add(seasonIcon).size(24).padLeft(50).left();
        content.add(weatherIcon).size(24).padLeft(10).left().row();
        content.add(timeLabel).padLeft(50).left().row();
        content.add(moneyLabel).padLeft(50).padTop(10).left();

        table.add(content).pad(10).top().right();

        stage.addActor(table);
    }

    public void update() {
        TimeDate timeDate = App.getActiveGame().getTimeDate();

        timeLabel.setText(timeDate.getTime());
        dateLabel.setText("Day " + timeDate.getDay());
        dayLabel.setText(timeDate.getDayOfWeek());
        moneyLabel.setText(App.getActiveGame().getCurrentPlayer().getCoin() + " G");

        setWeatherIcon(App.getActiveGame().getWeatherCondition());
        setSeasonIcon(timeDate.getSeason());
    }

    private void setWeatherIcon(WeatherCondition condition) {
        // Based on known positions in your Clock.png (row 0)
        TextureRegion iconRegion;
        switch (condition) {
            case SUNNY:
                iconRegion = regions[0][6]; break;
            case RAINY:
                iconRegion = regions[0][8]; break;
            case STORM:
                iconRegion = regions[0][7]; break;
            case SNOWY:
                iconRegion = regions[0][9]; break;
            default:
                iconRegion = regions[0][6]; break; // fallback
        }
        weatherIcon.setDrawable(new TextureRegionDrawable(iconRegion));
    }

    private void setSeasonIcon(Season season) {
        // Based on known positions in your Clock.png (row 0)
        TextureRegion iconRegion;
        switch (season) {
            case Spring:
                iconRegion = regions[0][2]; break;
            case Summer:
                iconRegion = regions[0][3]; break;
            case Fall:
                iconRegion = regions[0][4]; break;
            case Winter:
                iconRegion = regions[0][5]; break;
            default:
                iconRegion = regions[0][2]; break; // fallback
        }
        seasonIcon.setDrawable(new TextureRegionDrawable(iconRegion));
    }
}
