package com.group16.stardewvalley.controller.menu.Graphics;

import com.badlogic.gdx.Gdx;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.model.user.UserSaveManager;
import com.group16.stardewvalley.view.menuGraphics.SignUpMenuView;
import com.group16.stardewvalley.view.menuGraphics.StartMenuView;
import com.group16.stardewvalley.model.app.App;

public class SignUpViewController {
    private SignUpMenuView view;

    public void setView(SignUpMenuView view) {
        this.view = view;
    }



    public void handleLoginButtons() {
        if(view.getBackButton().isPressed()){
            if (App.getLoggedInUser() == null ) {
                GameAssetManager.getGameAssetManager().getClickButtonSound().play();
            }
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new StartMenuView(new StartMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
        }
        if (view.getRegisterButton().isPressed()) {
            if (App.getLoggedInUser() == null ) {
                GameAssetManager.getGameAssetManager().getClickButtonSound().play();
            }
            String username = view.getUsernameField().getText();
            String password = view.getPasswordField().getText();
            String securityAnswer = view.getSecurityQuestionField().getText();

            if (!isRegistrationValid(password)) {
                view.setMessage("Weak password.");
                return;
            }

            if (securityAnswer == null || securityAnswer.trim().isEmpty()) {
                view.setMessage("Please answer the security question.");
                return;
            }


            // Save user with security answer

            if (true) {
                view.setMessage("User registered successfully!");
                Gdx.app.log("Register", "Saved at: " + Gdx.files.local("users.json").file().getAbsolutePath());

                User newUser = new User(username, password, "majid", "majid@majid.com", "male");
                Player newPlayer = new Player(newUser);

                UserSaveManager.addUserAndSave(newUser); // Save new user to file


                App.setLoggedInUser(newUser);
                App.setCurrentPlayer(newPlayer);

                Main.getMain().getScreen().dispose();
//                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            } else {
                view.setMessage("Username already taken.");
            }
        }
    }

    private boolean isRegistrationValid( String password) {
        if ( password.length() < 8) return false;

        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        String specialChars = "!@_()*&%$#";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (specialChars.indexOf(c) != -1) hasSpecialChar = true;
        }

        return hasUppercase && hasDigit && hasSpecialChar;
    }


    public com.badlogic.gdx.scenes.scene2d.ui.Skin getSkin() {
        return GameAssetManager.getGameAssetManager().getSkin();
    }
}






