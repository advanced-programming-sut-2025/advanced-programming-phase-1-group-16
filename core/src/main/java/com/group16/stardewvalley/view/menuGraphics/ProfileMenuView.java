package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.controller.menu.ProfileMenuController;
import com.group16.stardewvalley.controller.menu.StartMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.graphics.AnimatedSpriteActor;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.graphics.Heros;

public class ProfileMenuView implements Screen {
    private final ProfileMenuController controller;
    private Stage stage;
    private final Skin skin;

    public ProfileMenuView(ProfileMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Background
        Texture bgTexture = new Texture(Gdx.files.internal("Background/mainBack.jpeg"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        // Logo
        Texture logoTexture = new Texture(Gdx.files.internal("Background/Profile-Menu.png"));
        Image logoImage = new Image(logoTexture);
        logoImage.setSize(logoTexture.getWidth() * 0.3f, logoTexture.getHeight() * 0.3f);

        // Avatar Buttons
        AnimatedSpriteActor[] heroes = {
            createHero(Heros.ABIGAIL), createHero(Heros.ALEX),
            createHero(Heros.KENT), createHero(Heros.LEO), createHero(Heros.MARNIE)
        };
        for (AnimatedSpriteActor hero : heroes) {
            hero.setSize(80, 200);
        }

        heroes[0].addListener(new AvatarClickListener(Heros.ABIGAIL));
        heroes[1].addListener(new AvatarClickListener(Heros.ALEX));
        heroes[2].addListener(new AvatarClickListener(Heros.KENT));
        heroes[3].addListener(new AvatarClickListener(Heros.LEO));
        heroes[4].addListener(new AvatarClickListener(Heros.MARNIE));

        // Avatar row
        Table avatarRow = new Table();
        for (AnimatedSpriteActor hero : heroes) {
            avatarRow.add(hero).pad(10);
        }


        final Label resultLabel = new Label("", skin);
        // Change buttons
        TextButton changeUsername = new TextButton("Change Username", skin);
        TextButton changePassword = new TextButton("Change Password", skin);
        TextButton changeName = new TextButton("Change Name", skin);
        TextButton changeEmail = new TextButton("Change Email", skin);


        // Set up listeners
        changeUsername.addListener(
            new FieldDialogListener("Change Username", "Enter new username", controller::changeUsername, resultLabel));

        changeName.addListener(
            new FieldDialogListener("Change Name", "Enter new name", controller::changeNickName, resultLabel));

        changeEmail.addListener(
            new FieldDialogListener("Change Email", "Enter new Email", controller::changeEmail, resultLabel));

        changePassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                Dialog dialog = new Dialog("Change Password", skin);

                TextField oldPass = new TextField("", skin);
                oldPass.setMessageText("Enter old password");
                oldPass.setPasswordMode(true);
                oldPass.setPasswordCharacter('*');

                TextField newPass = new TextField("", skin);
                newPass.setMessageText("Enter new password");
                newPass.setPasswordMode(true);
                newPass.setPasswordCharacter('*');

                Label feedback = new Label("", skin);
                feedback.setWrap(true);

                TextButton submit = new TextButton("Submit", skin);
                TextButton cancel = new TextButton("Cancel", skin);

                submit.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                        Result result = controller.changePassword(oldPass.getText().trim(), newPass.getText().trim());
                        feedback.setText(result.toString());

                        if (result.isSuccessful()) {
                            resultLabel.setText(result.toString());
                            dialog.hide();
                        }
                    }
                });

                cancel.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                        dialog.hide();
                    }
                });

                dialog.getContentTable().add(oldPass).width(400).padBottom(10).row();
                dialog.getContentTable().add(newPass).width(400).padBottom(10).row();
                dialog.getContentTable().add(feedback).width(400).height(80).padBottom(20).row();

                dialog.getButtonTable().add(submit).width(250).pad(5);
                dialog.getButtonTable().add(cancel).width(250).pad(5);

                dialog.show(stage);
                dialog.setSize(600, 400);
            }
        });

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        // Two-row button layout
        Table buttonGrid = new Table();
        buttonGrid.add(changeUsername).width(530).pad(10);
        buttonGrid.add(changePassword).width(530).pad(10);
        buttonGrid.row();
        buttonGrid.add(changeName).width(530).pad(10);
        buttonGrid.add(changeEmail).width(530).pad(10);

        // Root layout
        Table root = new Table();
        root.setFillParent(true);
        root.top().padTop(30);
        stage.addActor(root);

        root.add(logoImage).colspan(2).padBottom(40).row();
        root.add(avatarRow).colspan(2).padBottom(30).row();
        root.add(buttonGrid).colspan(2).row();
// Feedback label
        root.row().padTop(10);
        root.add(resultLabel).colspan(2).center().width(500);


// Bottom-left back button row
        Table bottomRow = new Table();
        bottomRow.add(backButton).width(250).pad(10);
        bottomRow.add().expandX(); // Filler pushes content to the left

        root.row().expandY().bottom();
        root.add(bottomRow).colspan(2).fillX().padBottom(20);

    }

    private AnimatedSpriteActor createHero(Heros hero) {
        Texture texture = new Texture(Gdx.files.internal(hero.getTexturePath()));
        return new AnimatedSpriteActor(texture, hero.getFrameWidth(), hero.getFrameHeight(), hero.getUpRow(), 0.3f);
    }

    private class AvatarClickListener extends ClickListener {
        private final Heros hero;

        public AvatarClickListener(Heros hero) {
            this.hero = hero;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            GameAssetManager.getGameAssetManager().getBrightClickSound().play();
            controller.setAvatar(hero);
        }
    }


    private class FieldDialogListener extends ClickListener {
        private final String title;
        private final String hint;
        private final InputHandler handler;
        private final Label resultLabel;

        public FieldDialogListener(String title, String hint, InputHandler handler, Label resultLabel) {
            this.title = title;
            this.hint = hint;
            this.handler = handler;
            this.resultLabel = resultLabel;

        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            GameAssetManager.getGameAssetManager().getBrightClickSound().play();

            Dialog dialog = new Dialog(title, skin);
            TextField input = new TextField("", skin);
            input.setMessageText(hint);

            TextButton submit = new TextButton("Submit", skin);
            TextButton cancel = new TextButton("Cancel", skin);

            Label feedback = new Label("", skin);
            feedback.setWrap(true);

            submit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                    Result result = handler.onSubmit(input.getText().trim());

                    feedback.setText(result.toString());

                    if (result.isSuccessful()) {
                        resultLabel.setText(result.toString());
                        dialog.hide();
                    }
                }
            });

            cancel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                    dialog.hide();
                }
            });

            dialog.getContentTable().add(input).width(500).padBottom(10).row();
            dialog.getContentTable().add(feedback).width(500).height(80).padBottom(20).row();
            dialog.getButtonTable().add(submit).width(250).pad(5);
            dialog.getButtonTable().add(cancel).width(250).pad(5);

            dialog.show(stage);
            dialog.setSize(600, 400);
        }
    }


    @FunctionalInterface
    private interface InputHandler {
        Result onSubmit(String input);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }
}
