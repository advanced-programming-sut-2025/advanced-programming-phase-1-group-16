//package com.group16.stardewvalley.controller.menu.tempGraphics;
//
//import com.tilldawn.Control.GameController;
//import com.tilldawn.Main;
//import com.tilldawn.Model.*;
//import com.tilldawn.View.GameView;
//import com.tilldawn.View.menu.MainMenuView;
//import com.tilldawn.View.menu.PreGameMenuView;
//
//public class PreGameMenuController {
//    private PreGameMenuView view;
//    private final UserDatabase userDatabase;
//
//
//    public PreGameMenuController() {
//        this.userDatabase =  new UserDatabase();
//
//    }
//
//    public void setView(PreGameMenuView view) {
//        this.view = view;
//    }
//
//    public void setAvatar(String fileName) {
//        Player player = App.getCurrentPlayer();
//
//
//        switch (fileName) {
//            case "Idle_0.png": player.setHero(Heros.Shana); break;
//            case "Idle_1.png": player.setHero(Heros.Diamond); break;
//            case "Idle_2.png": player.setHero(Heros.Scarlet); break;
//            case "Idle_3.png": player.setHero(Heros.Lilith); break;
//            case "Idle_4.png": player.setHero(Heros.Dasher); break;
//        }
//
//        player.setAvatarPath("assets/Images/Sprite/Idle/" + fileName);
//        userDatabase.save();
//        view.setMessage("Avatar set to " + player.getHero().toString());
//    }
//
//
//
//    public void startGame() {
//        if (view != null) {
//            Main.getMain().getScreen().dispose();
//            GameView gameView = new GameView(new GameController(), GameAssetManager.getGameAssetManager().getSkin());
//            App.setGameView(gameView);
//            Main.getMain().setScreen(gameView);
//        }
//    }
//
//
//    public void back(){
//        Main.getMain().getScreen().dispose();
//        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
//    }
//
//    public void selectGameTime(String selected) {
//        switch (selected) {
//            case "2 minutes":
//                App.getCurrentPlayer().setTotalGameTime(120);
//                break;
//            case "5 minutes":
//                App.getCurrentPlayer().setTotalGameTime(300);
//                break;
//            case "10 minutes":
//                App.getCurrentPlayer().setTotalGameTime(600);
//                break;
//            case "20 minutes":
//                App.getCurrentPlayer().setTotalGameTime(1200);
//                break;
//        }
//    }
//
//}
