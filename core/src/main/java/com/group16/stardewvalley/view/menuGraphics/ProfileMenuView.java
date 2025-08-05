package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.controller.menu.ProfileMenuController;
import com.group16.stardewvalley.controller.menu.StartMenuController;
import com.group16.stardewvalley.controllers.ClientNetworkManager;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.AnimatedSpriteActor;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.graphics.Heros;

import java.util.HashMap;

public class ProfileMenuView implements Screen {

    private ProfileMenuController controller;
    private Stage stage;
    private final Label titleLabel;
    private final TextButton changeUsernmeButton;
    private final TextButton changePasswordButton;
    private final TextField changingField;
    private final TextButton deleteAccountButton;
    private final TextButton changeAvatarButton;
    private final Table table;
    private  Label messageLabel;
    private final TextButton backButton;
    private final TextButton dragDropButton;




    public ProfileMenuView(ProfileMenuController controller, Skin skin) {
        this.controller = controller;
        this.titleLabel = new Label("P r o f i l e    M e n u", skin.get("title", Label.LabelStyle.class));
        this.changingField = new TextField("", skin);
        this.changeUsernmeButton = new TextButton("Change Username", skin);
        this.changePasswordButton = new TextButton("Change Password", skin);
        this.deleteAccountButton = new TextButton("Delete Account", skin);
        this.changeAvatarButton = new TextButton("Choose Avatar", skin);
        this.table = new Table();
        this.messageLabel = new Label("", skin);
        messageLabel.setColor(Color.MAGENTA);
        this.backButton = new TextButton("back", skin);
        this.dragDropButton = new TextButton("Drag & Drop", skin);


        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //  Background
        // Load background texture and set it as an Image
        Texture bgTexture = new Texture(Gdx.files.internal("Background/mainBack.jpeg"));
        Image background = new Image(bgTexture);
        background.setFillParent(true); // Make it stretch to screen size

        stage.addActor(background);

        //*------------------------------------------*//
        //button functions


        changeUsernmeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

                String newUsername = changingField.getText().trim();
                if (newUsername.isEmpty()) {
                    messageLabel.setText("Username cannot be empty.");
                } else {
                    messageLabel.setText(controller.changeUsername(newUsername).message());
                }
            }
        });

        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

                String newPassword = changingField.getText().trim();
                if (newPassword.isEmpty()) {
                    messageLabel.setText("Password cannot be empty.");
                } else {
                    Result result = controller.changePassword(newPassword, App.getLoggedInUser().getPassword());
                    messageLabel.setText(result.message());
                }
            }
        });

        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

                App.logout();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(
                    new StartMenuView(new StartMenuController(), GameAssetManager.getGameAssetManager().getSkin())
                );
                HashMap<String, Object> body = new HashMap<>();
                body.put("username", App.getLoggedInUser().getUsername());

                Message message = new Message(body, Message.Type.DELETE_USER);
                Message response = ClientNetworkManager.sendAndWait(message);
            }
        });

        //change nickname
        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });


        AnimatedSpriteActor hero1 = createHeroAnimation(Heros.ABIGAIL);
        AnimatedSpriteActor hero2 = createHeroAnimation(Heros.ALEX);
        AnimatedSpriteActor hero3 = createHeroAnimation(Heros.KENT);
        AnimatedSpriteActor hero4 = createHeroAnimation(Heros.LEO);
        AnimatedSpriteActor hero5 = createHeroAnimation(Heros.MARNIE);

        // Scale down
        hero1.setSize(64, 128);
        hero2.setSize(64, 128);
        hero3.setSize(64, 128);
        hero4.setSize(64, 128);
        hero5.setSize(64, 128);

        hero1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                controller.setAvatar(Heros.ABIGAIL);
            }
        });

        hero2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                controller.setAvatar(Heros.ALEX);
            }
        });


        hero3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                controller.setAvatar(Heros.KENT);
            }
        });


        hero4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                controller.setAvatar(Heros.LEO);
            }
        });


        hero5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                controller.setAvatar(Heros.MARNIE);
            }
        });


        //*------------------------------------------*//


        table.setFillParent(true);
        table.add(titleLabel).colspan(5).padBottom(200);
        table.row().pad(10, 0, 10, 0);

        table.center();

        table.add(hero1);
        table.add(hero2);
        table.add(hero3);
        table.add(hero4);
        table.add(hero5);

        table.row().pad(10, 0, 10, 0);


        table.add(changeUsernmeButton).left().colspan(1).width(470);
        table.add(changePasswordButton).right().colspan(5).width(470);
        table.row().pad(10, 0, 10, 0);
        table.row().pad(10, 0, 10, 0);

        table.add(deleteAccountButton).left().colspan(1).width(470);
        table.add(changeAvatarButton).right().colspan(5).width(470);

        table.row().pad(10, 0, 10, 0);
        table.add(changingField).left().colspan(1).width(470);
        table.add(dragDropButton).right().colspan(5).width(470);
        table.row().pad(10, 0, 10, 0);
        table.add(messageLabel).colspan(3).width(470).padLeft(250);

        table.row().pad(0, 0, 0, 600);
        table.add(backButton).width(200);


        stage.addActor(table);


    }




    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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


    public void setMessage(String msg) {
        messageLabel.setText(msg);
    }


    public TextButton getChangeUsernmeButton() {
        return changeUsernmeButton;
    }

    public TextButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public TextField getChangingField() {
        return changingField;
    }

    public TextButton getDeleteAccountButton() {
        return deleteAccountButton;
    }

    public TextButton getChangeAvatarButton() {
        return changeAvatarButton;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    private AnimatedSpriteActor createHeroAnimation(Heros hero) {
        Texture texture = new Texture(Gdx.files.internal(hero.getTexturePath()));
        return new AnimatedSpriteActor(texture, hero.getFrameWidth(), hero.getFrameHeight(), hero.getDownRow(), 0.25f);
    }

}

