package com.group16.stardewvalley.controller.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.group16.stardewvalley.view.graphics.GameScreen;

public class CharacterController {
    protected Texture spriteSheet;
    protected Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight;
    protected TextureRegion currentFrame;
    protected float x, y;
    protected float speed;
    protected float stateTime = 0f;
    protected boolean moving = false;

    public enum Direction { UP, DOWN, LEFT, RIGHT }
    protected Direction currentDirection = Direction.DOWN;

    public CharacterController(String spritePath, float x, float y, float speed, int frameWidth, int frameHeight) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        spriteSheet = new Texture(spritePath);
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        walkDown = new Animation<>(0.4f, tmp[2]);
        walkLeft = new Animation<>(0.4f, tmp[3]);
        walkRight = new Animation<>(0.4f, tmp[1]);
        walkUp = new Animation<>(0.6f, tmp[0]);
    }

    public void update(float delta, boolean up, boolean down, boolean left, boolean right) {
        moving = false;

        if (up) {
            y += speed * delta;
            currentDirection = Direction.UP;
            moving = true;
        } else if (down) {
            y -= speed * delta;
            currentDirection = Direction.DOWN;
            moving = true;
        }

        if (left) {
            x -= speed * delta;
            currentDirection = Direction.LEFT;
            moving = true;
        } else if (right) {
            x += speed * delta;
            currentDirection = Direction.RIGHT;
            moving = true;
        }

        if (moving) stateTime += delta;
        else stateTime = 0f;
    }

    public void render(SpriteBatch batch) {
        switch (currentDirection) {
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

