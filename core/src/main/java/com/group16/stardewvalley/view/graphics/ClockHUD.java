package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.BuffType;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.weather.WeatherCondition;
import com.group16.stardewvalley.model.time.Season;

import java.beans.XMLEncoder;

public class ClockHUD {
    private final Texture clockTexture;
    private final TextureRegion clockFaceRegion;
    private final TextureRegion clockHandRegion;

    private final TextureRegion springRegion;
    private final TextureRegion summerRegion;
    private final TextureRegion fallRegion;
    private final TextureRegion winterRegion;
    private final TextureRegion sunnyRegion;
    private final TextureRegion rainyRegion;
    private final TextureRegion stormRegion;
    private final TextureRegion snowyRegion;
    private final BitmapFont font;

    private static final int CLOCK_WIDTH = 70;
    private static final int CLOCK_HEIGHT = 60;
    private static final float SCALE = 1.2f; // 2x bigger

    private Texture buffIcon;


    public ClockHUD() {
        clockTexture = new Texture("sprites/Clock.png"); // Place this in assets

        BitmapFont originalFont = GameAssetManager.getGameAssetManager().getSkin().getFont("font");
        font = new BitmapFont(originalFont.getData().fontFile, originalFont.getRegion(), false);
        font.getData().setScale(0.5f); //  Smaller font only for the clock
        font.setColor(Color.BLACK);

        // Main clock face
        clockFaceRegion = new TextureRegion(clockTexture, 0, 0, 70, 60);

//         Clock hand (pointer)
        clockHandRegion = new TextureRegion(clockTexture, 71, 0, 9, 59);


        springRegion = new TextureRegion(clockTexture, 80, 0, 12, 8);
        summerRegion = new TextureRegion(clockTexture, 93, 0, 12, 8);
        fallRegion   = new TextureRegion(clockTexture, 106, 0, 12, 8);
        winterRegion = new TextureRegion(clockTexture, 80, 9, 12, 8);

        sunnyRegion = new TextureRegion(clockTexture, 119, 9, 12, 8);
        rainyRegion = new TextureRegion(clockTexture, 106, 9, 12, 8);
        stormRegion = new TextureRegion(clockTexture, 119, 18, 12, 8);
        snowyRegion = new TextureRegion(clockTexture, 93, 18, 12, 8);



    }

    public void render(SpriteBatch batch, float screenX, float screenY) {
        Player player = App.getActiveGame().getCurrentPlayer();

        float scaledWidth = CLOCK_WIDTH * SCALE;
        float scaledHeight = CLOCK_HEIGHT * SCALE;
        // Draw scaled clock face
        batch.draw(clockFaceRegion, screenX-10, screenY-5, scaledWidth, scaledHeight);


        // Update origin for pointer
        float originX = screenX + scaledWidth / 2f - 20;
        float originY = screenY + scaledHeight / 2f + 12;

        //TODO: uncomment following line and delete its next line if you debugged time (hour) system
//        float rotation = (TimeDate.getInstance(App.getActiveGame()).getHour() - 6) * 30f;
        float rotation = 5 * 30f;

//         Draw pointer, scaled
        batch.draw(clockHandRegion, originX - 3, originY - scaledHeight / 2,
            3, scaledHeight / 2, 10, CLOCK_HEIGHT,
            SCALE, SCALE, rotation );

// Draw time (lower field)
        font.draw(batch, TimeDate.getInstance(App.getActiveGame()).getTime(), screenX + 30, screenY + 32);

// Draw date (upper field)
        font.draw(batch, TimeDate.getInstance(App.getActiveGame()).getDayOfWeek() + ". " +
            TimeDate.getInstance(App.getActiveGame()).getDay(), screenX + 25, screenY + 59);


        // Draw season icon
        switch (App.getActiveGame().getSeason()){
            case Spring:
                batch.draw(springRegion, screenX + 25 , screenY+38, SCALE * 12, SCALE * 8 ); break;
            case Summer:
                batch.draw(summerRegion, screenX + 25 , screenY+38, SCALE * 12, SCALE * 8 ); break;
            case Fall:
                batch.draw(fallRegion, screenX + 25 , screenY+38, SCALE * 12, SCALE * 8 ); break;
            case Winter:
                batch.draw(winterRegion, screenX + 25 , screenY+38, SCALE * 12, SCALE * 8 ); break;
        }

        // Draw weather icon
        if (App.getActiveGame().getWeatherCondition() == null) {
            batch.draw(sunnyRegion, screenX + 54 , screenY+ 38, SCALE * 12, SCALE * 8 );
        }else{
            switch (App.getActiveGame().getWeatherCondition()){
                case SUNNY:
                    batch.draw(sunnyRegion, screenX + 54 , screenY+ 38, SCALE * 12, SCALE * 8 ); break;
                case RAINY:
                    batch.draw(rainyRegion, screenX + 54 , screenY+ 38, SCALE * 12, SCALE * 8 ); break;
                case STORM:
                    batch.draw(stormRegion, screenX + 54 , screenY+ 38, SCALE * 12, SCALE * 8 ); break;
                case SNOWY:
                    batch.draw(snowyRegion, screenX + 54 , screenY+ 38, SCALE * 12, SCALE * 8 ); break;
            }
        }


        //Draw money
        String money;
        if (App.getActiveGame().getCurrentPlayer() == null || App.getActiveGame().getCurrentPlayer().getCoin() == 0) {
            money = "000";
        } else {
            money = String.valueOf(App.getActiveGame().getCurrentPlayer().getCoin());
        }

// Define box area
        float boxX = screenX - 4; // start of beige area (adjust based on image)
        float boxY = screenY + 8;  // vertical position
        float boxWidth = 70f;      // width of beige area (adjust as needed)

// Measure text width
        GlyphLayout layout = new GlyphLayout(font, money);
        float textWidth = layout.width;

// Draw text right-aligned inside the box
        float textX = boxX + boxWidth - textWidth; // right edge - text width
        font.draw(batch, layout, textX, boxY);

        BuffType buff = player.getBuffer();
        if (buff != BuffType.NONE) {
            buffIcon = GameAssetManager.getGameAssetManager().getTexture(buff.getTexturePath());
            batch.draw(buffIcon, screenX - 50, screenY + 20, 30, 30);
        }



    }

    public void dispose() {
        clockTexture.dispose();
        font.dispose();
    }
}
