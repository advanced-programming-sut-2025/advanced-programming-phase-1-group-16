package com.group16.stardewvalley.controller.menu.Graphics;

import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.view.menuGraphics.LoginMenuView;
import com.group16.stardewvalley.view.menuGraphics.StartMenuView;

import static com.group16.stardewvalley.model.user.User.getUserByUsername;

public class LoginViewController {
    private LoginMenuView view;
    private boolean answerVerified = false;

    public void setView(LoginMenuView view) {
        this.view = view;
    }



    public void handleLoginButtons() {
        if (view.getLoginButton().isPressed()) {
            if (App.getLoggedInUser() == null  ) {
                GameAssetManager.getGameAssetManager().getClickButtonSound().play();
            }
            String username = view.getUsernameField().getText();
            String password = view.getPasswordField().getText();

            User user = getUserByUsername(username);

            if (user == null) {
                view.setMessage("Username Not Found.");
            } else if (!user.getPassword().equals(password)) {
                view.setMessage("Incorrect Password.");
            } else {
                App.setLoggedInUser(user);
                App.setCurrentPlayer(new Player(user));
                Main.getMain().getScreen().dispose();
//                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        }

        if (view.getForgotPasswordButton().isPressed()) {
            if (App.getLoggedInUser() == null  ) {
                GameAssetManager.getGameAssetManager().getClickButtonSound().play();
            }
            view.toggleSecurityUI(true);
            view.setMessage("Answer your security question:");
        }

        if(view.getBackButton().isPressed()){
            if (App.getLoggedInUser() == null  ) {
                GameAssetManager.getGameAssetManager().getClickButtonSound().play();
            }
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new StartMenuView(new StartMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
        }

        if (view.isSecurityUIVisible() && view.getSubmitSecurityButton().isPressed()) {
            if (App.getLoggedInUser() == null  ) {
                GameAssetManager.getGameAssetManager().getClickButtonSound().play();
            }
            String username = view.getUsernameField().getText();
            User user = getUserByUsername(username);

            if (user == null) {
                view.setMessage("Username Not Found.");
                return;
            }

            if (!answerVerified) {
                String answer = view.getSecurityAnswerField().getText();
                if (user.getSecurityAnswer() != null && user.getSecurityAnswer().equalsIgnoreCase(answer.trim())) {
                    view.setMessage("Correct! Enter your new password:");
                    view.getSecurityAnswerField().setText(""); // clear field
                    view.getSecurityAnswerField().setPasswordMode(true);
                    view.getSecurityAnswerField().setPasswordCharacter('*');
                    view.getSubmitSecurityButton().setText("Set Password");
                    answerVerified = true;
                } else {
                    view.setMessage("Incorrect answer.");
                }
            } else {
                String newPassword = view.getSecurityAnswerField().getText();
                if (!isRegistrationValid(newPassword)) {
                    view.setMessage("Weak Password.");
                    return;
                }


                user.setPassword(newPassword);
//                userDatabase.save();
                view.setMessage("Password updated successfully.");
                answerVerified = false;
                view.toggleSecurityUI(false);
            }
        }
    }



    private boolean isRegistrationValid( String password) {
        if ( password.length() < 8) return false;

        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        String specialChars = "@_()*&%$#";

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




