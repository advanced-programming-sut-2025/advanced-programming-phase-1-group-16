package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.time.Season;
import com.group16.stardewvalley.model.weather.WeatherCondition;
import com.group16.stardewvalley.model.user.Player;

public class ClockWidget {
    private final Texture clockTexture;
    private final TextureRegion clockFaceRegion;
    private final TextureRegion clockHandRegion;
    private final TextureRegion[] weatherIcons;
    private final TextureRegion[] seasonIcons;
    private final BitmapFont font;

    private static final int CLOCK_WIDTH = 65;
    private static final int CLOCK_HEIGHT = 65;

    public ClockWidget() {
        clockTexture = new Texture("sprites/Clock.png"); // Place this in assets
        font = new BitmapFont(); // You can replace with a custom font

        // Main clock face
        clockFaceRegion = new TextureRegion(clockTexture, 0, 0, 59, 59);

        // Clock hand (pointer)
        clockHandRegion = new TextureRegion(clockTexture, 59, 0, 6, 59);

        // Icons
        weatherIcons = new TextureRegion[4];
        seasonIcons = new TextureRegion[4];

        for (int i = 0; i < 4; i++) {
            weatherIcons[i] = new TextureRegion(clockTexture, 59 + i * 17, 59, 17, 17);
            seasonIcons[i] = new TextureRegion(clockTexture, 59 + (i + 4) * 17, 59, 17, 17);
        }
    }

    public void render(SpriteBatch batch, float screenX, float screenY) {
        TimeDate time = App.getActiveGame().getTimeDate();
        Player player = App.getActiveGame().getCurrentPlayer();

        // Draw clock face
        batch.draw(clockFaceRegion, screenX, screenY, CLOCK_WIDTH, CLOCK_HEIGHT);

        // Draw clock hand
        float originX = screenX + CLOCK_WIDTH / 2f;
        float originY = screenY + CLOCK_HEIGHT / 2f;
        float rotation = (time.getHour() - 6) * 30f; // 6 AM is 0 degrees
        batch.draw(clockHandRegion,
            originX - 3, originY - 29.5f,
            3, 29.5f, 6, 59,
            1f, 1f, rotation
        );

        // Draw date (upper field)
        font.draw(batch, time.getDayOfWeek() + " " + time.getDay() + " " + time.getSeason().getName(),
            screenX + 5, screenY + 54);

        // Draw time (lower field)
        font.draw(batch, time.getTime(), screenX + 10, screenY + 14);

        // Draw season icon
        int seasonIndex = time.getSeason().getIndex();
        batch.draw(seasonIcons[seasonIndex], screenX - 20, screenY + 20, 17, 17);

        // Draw weather icon
        int weatherIndex = App.getActiveGame().getWeatherCondition().getWeatherNumber() - 1;
        batch.draw(weatherIcons[weatherIndex], screenX - 20, screenY + 0, 17, 17);
    }

    public void dispose() {
        clockTexture.dispose();
        font.dispose();
    }
}
