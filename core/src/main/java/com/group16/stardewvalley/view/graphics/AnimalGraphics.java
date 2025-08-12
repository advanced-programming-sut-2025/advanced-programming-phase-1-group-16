package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.group16.stardewvalley.model.map.Direction;

public class AnimalGraphics {
    private Animation<TextureRegion> downAnim, rightAnim, leftAnim, upAnim;
    private float stateTime = 0f;

    public AnimalGraphics(String name) {
        System.out.println("loading " + name);
        Texture texture = new Texture(Gdx.files.internal("Animals/" + "Dog_Animation" + ".png"));

        try {
            texture = new Texture(Gdx.files.internal("Animals/" + name + ".png"));

        }catch (Exception e) {
            texture = new Texture(Gdx.files.internal("Animals/" + "Dog_Animation" + ".png"));
        }
        int FRAME_COLS = 4; // how many frames per row
        int FRAME_ROWS = 4; // how many directions

        TextureRegion[][] tmp = TextureRegion.split(texture,
            texture.getWidth() / FRAME_COLS,
            texture.getHeight() / FRAME_ROWS);

        // Assuming row order: down, right, left, up
        downAnim = new Animation<>(0.2f, tmp[0]);
        rightAnim = new Animation<>(0.2f, tmp[1]);
        leftAnim = new Animation<>(0.2f, tmp[2]);
        upAnim = new Animation<>(0.2f, tmp[3]);
    }

    public TextureRegion getCurrentFrame(Direction dir, float delta) {
        stateTime += delta;
        switch (dir) {
            case DOWN: return downAnim.getKeyFrame(stateTime, true);
            case RIGHT: return rightAnim.getKeyFrame(stateTime, true);
            case LEFT: return leftAnim.getKeyFrame(stateTime, true);
            case UP: return upAnim.getKeyFrame(stateTime, true);
        }
        return downAnim.getKeyFrame(stateTime, true);
    }
}
