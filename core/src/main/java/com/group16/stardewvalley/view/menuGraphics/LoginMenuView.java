package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.group16.stardewvalley.controller.menu.Graphics.LoginViewController;
import com.group16.stardewvalley.controller.menu.LoginMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.graphics.GameAssetManager;


public class LoginMenuView implements Screen {
    private final LoginViewController controller;
    private Stage stage;
    private final Label titleLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextButton loginButton;
    private final Table table;
    private final Label messageLabel;
    private final TextButton forgotPasswordButton;
    private final TextField securityAnswerField;
    private final TextButton submitSecurityButton;
    private boolean securityUIVisible = false;
    private final TextButton backButton;



    public LoginMenuView(LoginViewController controller, Skin skin) {
        this.controller = controller;
        this.titleLabel = new Label("L o g i n   M e n u", skin.get("title", Label.LabelStyle.class));
        this.usernameField = new TextField("", skin);
        this.passwordField = new TextField("", skin);
        this.loginButton = new TextButton("Login", skin);
        this.table = new Table();
        this.messageLabel = new Label("", skin); // empty message initially
        this.forgotPasswordButton = new TextButton("Forgot Password?", skin);
        this.securityAnswerField = new TextField("", skin);
        this.submitSecurityButton = new TextButton( "Submit", skin);
        securityAnswerField.setMessageText("Your security answer");
        securityAnswerField.setVisible(false);
        submitSecurityButton.setVisible(false);
        this.backButton = new TextButton("Back", skin);

        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        controller.setView(this);
    }



    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load background texture and set it as an Image
        Texture bgTexture = new Texture(Gdx.files.internal("Background/mainBack.jpeg"));
        Image background = new Image(bgTexture);
        background.setFillParent(true); // Make it stretch to screen size

        // Add background first so it stays behind everything else
        stage.addActor(background);

        //*------------------------------------------*//
        //button functions

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                controller.loginButtonClicked();
            }
        });

        forgotPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                controller.forgotPasswordButtonClicked();

            }
        });

        submitSecurityButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                controller.submitSecurityQuestionButtonClicked();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                controller.back();
            }
        });
        //*------------------------------------------*//


        table.setFillParent(true);
        table.center();
        table.add(titleLabel).colspan(2).padTop(30);
        table.row().pad(10, 0, 10, 0);
        table.add(new Label("Username"+":", controller.getSkin())).left();
        table.add(usernameField).width(300);
        table.row().pad(10, 0, 10, 0);
        table.add(new Label("Password"+":", controller.getSkin())).left();
        table.add(passwordField).width(300);
        table.row().pad(10, 250, 10, 0);
        table.add(loginButton).width(500).padLeft(300);

        table.row().pad(10, 0, 10, 0);
        table.add(messageLabel).colspan(2);

        table.row().pad(5, 0, 5, 0);
        table.add(forgotPasswordButton).colspan(2).width(500);

        table.row().pad(10, 0, 10, 0);
        table.add(securityAnswerField).width(490).padLeft(300);
        table.row().pad(10, 0, 10, 0);
        table.add(submitSecurityButton).colspan(2).width(500);

        table.row().pad(0, 0, 0, 600);
        table.add(backButton).width(200);


        securityAnswerField.setVisible(false);
        submitSecurityButton.setVisible(false);


        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

    public TextButton getLoginButton() {
        return loginButton;
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


    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public TextButton getForgotPasswordButton() {
        return forgotPasswordButton;
    }

    public TextField getSecurityAnswerField() {
        return securityAnswerField;
    }

    public TextButton getSubmitSecurityButton() {
        return submitSecurityButton;
    }

    public void toggleSecurityUI(boolean visible) {
        securityAnswerField.setVisible(visible);
        submitSecurityButton.setVisible(visible);
        securityUIVisible = visible;
    }

    public boolean isSecurityUIVisible() {
        return securityUIVisible;
    }

    public TextButton getBackButton() {
        return backButton;
    }
}
