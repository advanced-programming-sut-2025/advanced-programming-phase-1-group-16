package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.group16.stardewvalley.controller.menu.Graphics.SignUpViewController;

public class SignUpMenuView implements Screen {
    private Stage stage;
    private final Label titleLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextButton registerButton;
    private final Table table;
    private final SignUpViewController controller;
    private final Label messageLabel;
    private final Label securityQuestionLabel;
    private final TextField securityQuestionField;
    private final TextButton securityQuestionButton;
    private final TextButton backButton;


    public SignUpMenuView(SignUpViewController controller, Skin skin) {
        this.controller = controller;
        this.titleLabel = new Label("S i g n  U p    M e n u", skin.get("title", Label.LabelStyle.class));
        this.usernameField = new TextField("", skin);
        this.passwordField = new TextField("", skin);
        this.registerButton = new TextButton("Sign Up", skin);
        this.messageLabel = new Label("", skin);
        this.securityQuestionLabel = new Label("", skin);
        this.securityQuestionField = new TextField("", skin);
        this.securityQuestionButton = new TextButton("Submit", skin);
        this.backButton = new TextButton("Back", skin);

        this.table = new Table();

        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        controller.setView(this);
    }



    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Texture bgTexture = new Texture(Gdx.files.internal("Background/mainBack.jpeg"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        table.setFillParent(true);
        table.center();
        table.add(titleLabel).colspan(2).padTop(30);
        table.row().pad(10, 0, 10, 0);
        // Username row
        table.add(new Label("Username:", controller.getSkin())).left();
        table.add(usernameField).width(300);
        table.row().pad(10, 0, 10, 0);

        // Password row
        table.add(new Label("Password:", controller.getSkin())).left();
        table.add(passwordField).width(300);
        table.row().pad(10, 0, 10, 0);

        // Security question row
        securityQuestionLabel.setText("Your favorite color?             ");  // example question
        table.add(securityQuestionLabel).left();
        table.add(securityQuestionField).width(300);
        table.row().pad(10, 0, 10, 0);

        // Register button row
        table.add(registerButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);

        // Message label row
        table.add(messageLabel).colspan(2).center();

        table.row().pad(0, 0, 0, 600);
        table.add(backButton).width(200);


        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleLoginButtons();
    }

    @Override
    public void resize(int width, int height) {
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
    }


    public void setMessage(String message) {
        messageLabel.setText(message);
    }


    public TextButton getRegisterButton() {
        return registerButton;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Skin getSkin() {
        return controller.getSkin();
    }

    public TextField getSecurityQuestionField() {
        return securityQuestionField;
    }

    public Label getSecurityQuestionLabel() {
        return securityQuestionLabel;
    }

    public TextButton getBackButton() {
        return backButton;
    }
}
