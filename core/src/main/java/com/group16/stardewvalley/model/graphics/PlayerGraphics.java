package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.group16.stardewvalley.controller.GameController;
import com.group16.stardewvalley.model.map.Direction;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.view.graphics.GameScreen;

public class PlayerGraphics {
    private Player player;
    int tileSize;
    private Texture spriteSheet;
    private Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight, faint;
    private TextureRegion currentFrame;
    private Texture face;
    private float stateTime = 0f;
    protected float x, y;

    public PlayerGraphics(Player player, String spritePath, int frameWidth, int frameHeight) {
        this.player = player;
        this.x = player.getPosition().getX();
        this.y = player.getPosition().getY();
        this.tileSize = GameScreen.TILE_SIZE;
        spriteSheet = new Texture(spritePath);
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        walkDown = new Animation<>(0.4f, tmp[2]);
        walkLeft = new Animation<>(0.4f, tmp[3]);
        walkRight = new Animation<>(0.4f, tmp[1]);
        walkUp = new Animation<>(0.6f, tmp[0]);

        String pathStr = spritePath.substring(0, spritePath.length() - 4);
        Texture faintSpriteSheet = new Texture(pathStr + "_faint.png");
        TextureRegion faintSprite = new TextureRegion(faintSpriteSheet, 200, 266);

        faint = new Animation<>(0.5f, faintSprite);

        face = new Texture(pathStr + "_face.png");
    }

    public Texture getFace() {
        return face;
    }

    public void update(float delta, boolean up, boolean down, boolean left, boolean right) {
        if (player.isFainted()) return;
        player.setMoving(false);

        if (up) {
            y += player.getSpeed() * delta;
            player.setCurrentDirection(Direction.UP);
            player.setMoving(true);
        } else if (down) {
            y -= player.getSpeed() * delta;
            player.setCurrentDirection(Direction.DOWN);
            player.setMoving(true);
        }

        if (left) {
            x -= player.getSpeed() * delta;
            player.setCurrentDirection(Direction.LEFT);
            player.setMoving(true);
        } else if (right) {
            x += player.getSpeed() * delta;
            player.setCurrentDirection(Direction.RIGHT);
            player.setMoving(true);
        }

        if (player.isMoving()) stateTime += delta;
        else stateTime = 0f;
    }

    public void render(SpriteBatch batch) {
        if (player.isFainted()) {
            stateTime += Gdx.graphics.getDeltaTime();
            currentFrame = faint.getKeyFrame(stateTime, false);
            batch.draw(currentFrame, x * GameScreen.TILE_SIZE, y * GameScreen.TILE_SIZE , (float) spriteSheet.getWidth() / 7, (float) spriteSheet.getHeight() / 7);
            return;
        }

        switch (player.getCurrentDirection()) {
            case UP: currentFrame = walkUp.getKeyFrame(stateTime, true); break;
            case DOWN: currentFrame = walkDown.getKeyFrame(stateTime, true); break;
            case LEFT: currentFrame = walkLeft.getKeyFrame(stateTime, true); break;
            case RIGHT: currentFrame = walkRight.getKeyFrame(stateTime, true); break;
        }
        batch.draw(currentFrame, x * GameScreen.TILE_SIZE, y * GameScreen.TILE_SIZE, (float) spriteSheet.getWidth() / 7, (float) spriteSheet.getHeight() / 7);
    }

    public void dispose() {
        spriteSheet.dispose();
    }


}
