//package com.group16.stardewvalley.view.menuGraphics;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.*;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.group16.stardewvalley.controller.menu.tempGraphics.PreGameMenuController;
//
//
//public class PreGameMenuView implements Screen {
//    private Stage stage;
//    private PreGameMenuController controller;
//
//    private final Label titleLabel;
//    private ImageButton avatarButton1, avatarButton2, avatarButton3, avatarButton4, avatarButton5;
//    private SelectBox<String> timeSelect;
//    private TextButton startGameButton;
//    private TextButton backButton;
//    private  Label messageLabel;
//    private final Table table;
//    private SelectBox<String> gameTimeSelect;
//    private Label selectGameTimeLabel;
//
//
//    public PreGameMenuView(PreGameMenuController controller, Skin skin) {
//        this.controller = controller;
//        this.table = new Table();
//        this.messageLabel = new Label("", skin);
//        messageLabel.setColor(Color.MAGENTA);
//
//        this.titleLabel = new Label("P r o f i l e    M e n u", skin.get("title", Label.LabelStyle.class));
//        this.backButton = new TextButton("back", skin);
//        this.startGameButton = new TextButton(TranslatableText.StartGameButton.getText(), skin);
//        this.selectGameTimeLabel = new Label(TranslatableText.SelectGameTimeLabel.getText(), skin);
//
//
//        // Load image button styles
//        ImageButton.ImageButtonStyle style1 = new ImageButton.ImageButtonStyle();
//        style1.imageUp = new Image(new Texture("Images/Sprite/T/T_Shana_Portrait.png")).getDrawable();
//
//        ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();
//        style2.imageUp = new Image(new Texture("Images/Sprite/T/T_Diamond_Portrait.png")).getDrawable();
//
//        ImageButton.ImageButtonStyle style3 = new ImageButton.ImageButtonStyle();
//        style3.imageUp = new Image(new Texture("Images/Sprite/T/T_Scarlett_Portrait.png")).getDrawable();
//
//        ImageButton.ImageButtonStyle style4 = new ImageButton.ImageButtonStyle();
//        style4.imageUp = new Image(new Texture("Images/Sprite/T/T_Lilith_Portrait.png")).getDrawable();
//
//        ImageButton.ImageButtonStyle style5 = new ImageButton.ImageButtonStyle();
//        style5.imageUp = new Image(new Texture("Images/Sprite/T/T_Dasher_Portrait.png")).getDrawable();
//
//
//// Create buttons
//        avatarButton1 = new ImageButton(style1);
//        avatarButton2 = new ImageButton(style2);
//        avatarButton3 = new ImageButton(style3);
//        avatarButton4 = new ImageButton(style4);
//        avatarButton5 = new ImageButton(style5);
//
//// Set click listeners
//
//        avatarButton1.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (App.getCurrentPlayer() == null || App.getCurrentPlayer().isSfx()) {
//                    GameAssetManager.getGameAssetManager().getClickButtonSound().play();
//                }
//                controller.setAvatar("Idle_0.png"); //shana
//                App.getCurrentPlayer().setHero(Heros.Shana);
//                System.out.println(App.getCurrentPlayer().getHero().toString());
//
//            }
//        });
//
//        avatarButton2.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (App.getCurrentPlayer() == null || App.getCurrentPlayer().isSfx()) {
//                    GameAssetManager.getGameAssetManager().getClickButtonSound().play();
//                }
//                controller.setAvatar("Idle_1.png");//diamond
//                App.getCurrentPlayer().setHero(Heros.Diamond);
//                System.out.println(App.getCurrentPlayer().getHero().toString());
//
//
//            }
//        });
//
//
//        avatarButton3.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (App.getCurrentPlayer() == null || App.getCurrentPlayer().isSfx()) {
//                    GameAssetManager.getGameAssetManager().getClickButtonSound().play();
//                }
//                controller.setAvatar("Idle_2.png");//lilith
//                App.getCurrentPlayer().setHero(Heros.Lilith);
//                System.out.println(App.getCurrentPlayer().getHero().toString());
//
//
//            }
//        });
//
//
//        avatarButton4.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (App.getCurrentPlayer() == null || App.getCurrentPlayer().isSfx()) {
//                    GameAssetManager.getGameAssetManager().getClickButtonSound().play();
//                }
//                controller.setAvatar("Idle_3.png");//scarlet
//                App.getCurrentPlayer().setHero(Heros.Scarlet);
//                System.out.println(App.getCurrentPlayer().getHero().toString());
//
//
//            }
//        });
//
//
//        avatarButton5.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (App.getCurrentPlayer() == null || App.getCurrentPlayer().isSfx()) {
//                    GameAssetManager.getGameAssetManager().getClickButtonSound().play();
//                }
//                controller.setAvatar("Idle_4.png");//dasher
//                App.getCurrentPlayer().setHero(Heros.Dasher);
//                System.out.println(App.getCurrentPlayer().getHero().toString());
//
//
//            }
//        });
//
//
//        //make weapon buttons
//        ImageButton.ImageButtonStyle weapon1 = new ImageButton.ImageButtonStyle();
//        weapon1.imageUp = new Image(new Texture("Images/Sprite/RevolverStill/RevolverStill.png")).getDrawable();
//
//        ImageButton.ImageButtonStyle weapon2 = new ImageButton.ImageButtonStyle();
//        weapon2.imageUp = new Image(new Texture("Images/Sprite/T/T_Shotgun_SS_0.png")).getDrawable();
//
//        ImageButton.ImageButtonStyle weapon3 = new ImageButton.ImageButtonStyle();
//        weapon3.imageUp = new Image(new Texture("Images/Sprite/SMGStill/SMGStill.png")).getDrawable();
//
//
//        weaponButton1 = new ImageButton(weapon1);
//        weaponButton2 = new ImageButton(weapon2);
//        weaponButton3 = new ImageButton(weapon3);
//        weaponButton1.getImageCell().size(128, 128);
//        weaponButton2.getImageCell().size(128, 128);
//        weaponButton3.getImageCell().size(128, 128);
//
//
//
//
//        weaponButton1.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (App.getCurrentPlayer() == null || App.getCurrentPlayer().isSfx()) {
//                    GameAssetManager.getGameAssetManager().getClickButtonSound().play();
//                }
//                App.getCurrentPlayer().setWeaponType(Weapons.Revolver);
//                setMessage(TranslatableText.WeaponSetToRevolver.getText());            }
//        });
//
//        weaponButton2.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (App.getCurrentPlayer() == null || App.getCurrentPlayer().isSfx()) {
//                    GameAssetManager.getGameAssetManager().getClickButtonSound().play();
//                }
//                App.getCurrentPlayer().setWeaponType(Weapons.Shotgun);
//                setMessage(TranslatableText.WeaponSetToShotgun.getText());            }
//        });
//
//        weaponButton3.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (App.getCurrentPlayer() == null || App.getCurrentPlayer().isSfx()) {
//                    GameAssetManager.getGameAssetManager().getClickButtonSound().play();
//                }
//                App.getCurrentPlayer().setWeaponType(Weapons.Smg);
//                setMessage(TranslatableText.WeaponSetToSmg.getText());            }
//        });
//
//        this.gameTimeSelect = new SelectBox<>(skin);
//        this.gameTimeSelect.setItems(
//            TranslatableText.GameTimeOption2.getText(),
//            TranslatableText.GameTimeOption5.getText(),
//            TranslatableText.GameTimeOption10.getText(),
//            TranslatableText.GameTimeOption20.getText()
//        );
//        this.gameTimeSelect.setSelected(TranslatableText.GameTimeOption2.getText());
//
//        this.gameTimeSelect.addListener(event -> {
//            if (App.getCurrentPlayer() == null || App.getCurrentPlayer().isSfx()) {
//                GameAssetManager.getGameAssetManager().getClickButtonSound().play();
//            }
//            controller.selectGameTime(this.gameTimeSelect.getSelected());
//            return false;
//        });
//
//        controller.setView(this);
//    }
//
//
//    @Override
//    public void show() {
//        stage = new Stage();
//        Gdx.input.setInputProcessor(stage);
//
//        //  Background
//        Texture bgTexture = new Texture(Gdx.files.internal("blueBack.png"));
//        Image background = new Image(bgTexture);
//        background.setFillParent(true);
//        stage.addActor(background);
//
//
//        startGameButton.addListener(event -> {
//            if (!startGameButton.isPressed()) return false;
//            if (App.getCurrentPlayer() == null || App.getCurrentPlayer().isSfx()) {
//                GameAssetManager.getGameAssetManager().getClickButtonSound().play();
//            }
//            controller.startGame();
//            return false;
//        });
//
//        backButton.addListener(event -> {
//            if (!backButton.isPressed()) return false;
//            if (App.getCurrentPlayer() == null || App.getCurrentPlayer().isSfx()) {
//                GameAssetManager.getGameAssetManager().getClickButtonSound().play();
//            }
//            controller.back();
//            return false;
//        });
//
//
//        table.setFillParent(true);
//        table.center().top().padTop(50);
//
//// Title
//        table.add(titleLabel).colspan(5).padBottom(50);
//        table.row();
//
//// Avatar selection
//        table.add(avatarButton1).size(128).pad(10);
//        table.add(avatarButton2).size(128).pad(10);
//        table.add(avatarButton3).size(128).pad(10);
//        table.add(avatarButton4).size(128).pad(10);
//        table.add(avatarButton5).size(128).pad(10);
//        table.row().padTop(30);
//
//// Weapon selection
//        table.add(weaponButton1).size(256).pad(10).colspan(2).left();
//        table.add(weaponButton2).size(256).pad(10).colspan(1).center();
//        table.add(weaponButton3).size(256).pad(10).colspan(2).right();
//        table.row().padTop(30);
//
//// Game time select
//        table.add(selectGameTimeLabel).colspan(2).right().padRight(10);
//        table.add(gameTimeSelect).colspan(3).left().width(200);
//        table.row().padTop(20);
//
//// Message label
//        table.add(messageLabel).colspan(5).center().width(500).padTop(10);
//        table.row().padTop(60);
//
//// Buttons
//        table.add(backButton).colspan(2).width(300).padRight(30);
//        table.add().colspan(1); // Spacer
//        table.add(startGameButton).colspan(2).width(300).padLeft(30);
//
//        stage.addActor(table);
//
//
//    }
//
//    @Override
//    public void render(float v) {
//        ScreenUtils.clear(0, 0, 0, 1);
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        stage.draw();
//    }
//    @Override
//    public void resize(int i, int i1) {
//
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//    @Override
//    public void dispose() {
//
//    }
//
//
//
//    public void setMessage(String msg) {
//        messageLabel.setText(msg);
//    }
//
//
//    public TextButton getBackButton() {
//        return backButton;
//    }
//
//}
