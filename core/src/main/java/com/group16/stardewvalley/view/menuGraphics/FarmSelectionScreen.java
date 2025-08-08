package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class FarmSelectionScreen implements Screen {

    private Stage stage;
    private Skin skin;

    private Texture smallFarmTexture;
    private Texture bigFarmTexture;

    private Image smallFarmImage;
    private Image bigFarmImage;

    private CheckBox smallFarmCheckBox;
    private CheckBox bigFarmCheckBox;
    private TextButton readyButton;

    public FarmSelectionScreen(Skin skin) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        smallFarmTexture = new Texture(Gdx.files.internal("Farms/farm1.png"));
        bigFarmTexture = new Texture(Gdx.files.internal("Farms/farm2.png"));

        smallFarmImage = new Image(smallFarmTexture);
        bigFarmImage = new Image(bigFarmTexture);

        smallFarmCheckBox = new CheckBox("Small Farm", skin);
        bigFarmCheckBox = new CheckBox("Big Farm", skin);

        ButtonGroup<CheckBox> checkBoxGroup = new ButtonGroup<>(smallFarmCheckBox, bigFarmCheckBox);
        checkBoxGroup.setMinCheckCount(1);
        checkBoxGroup.setMaxCheckCount(1);

        readyButton = new TextButton("I'm ready!", skin);
        readyButton.addListener(event -> {
            if (readyButton.isPressed()) {
                String choice = smallFarmCheckBox.isChecked() ? "small" : "big";
                System.out.println("Selected farm: " + choice);
                // اینجا می‌تونی پیام رو به سرور بفرستی
            }
            return false;
        });

        // ساخت جدول چینش
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.defaults().pad(10);

        Table farmsTable = new Table();
        farmsTable.add(smallFarmImage).width(200).height(200).pad(10);
        farmsTable.add(bigFarmImage).width(200).height(200).pad(10);
        farmsTable.row();
        farmsTable.add(smallFarmCheckBox).padTop(5);
        farmsTable.add(bigFarmCheckBox).padTop(5);

        rootTable.add(new Label("Choose your farm", skin)).colspan(2).align(Align.center).padBottom(20);
        rootTable.row();
        rootTable.add(farmsTable).colspan(2).padBottom(20);
        rootTable.row();
        rootTable.add(readyButton).colspan(2).width(200).height(50);

        stage.addActor(rootTable);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        smallFarmTexture.dispose();
        bigFarmTexture.dispose();
    }
}
