package com.group16.stardewvalley.controller.menu;

import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.menu.LoginMenuCommands;
import com.group16.stardewvalley.model.user.SecurityQuestions;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.view.menuGraphics.SignUpMenuView;
import com.group16.stardewvalley.view.menuGraphics.StartMenuView;
import com.group16.stardewvalley.Message;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class SignUpMenuController {

    private SignUpMenuView view;

    public void setView(SignUpMenuView view) {
        this.view = view;
    }



    public Result register(String username, String password, String passwordConfirm, String nickName, String email, String gender) {

        if (LoginMenuCommands.Username.getMatcher(username) == null) {
            return new Result(false, "username format is invalid!");
        }
        if (isUsernameTaken(username)) {
            return new Result(false, "username already exists! choose another one.");
        }

        if (LoginMenuCommands.Email.getMatcher(email) == null) {
            return new Result(false, "email format is invalid!");
        }

        if (LoginMenuCommands.Password.getMatcher(password) == null) {
            return new Result(false, "password format is invalid!");
        }
        if (password.length() < 8) {
            return new Result(false, "weak password! password must be at least 8 characters.");
        }
        if (!password.matches(".*[a-z].*")){
            return new Result(false, "weak password! password should contains at least one lowercase letter.");
        }
        if (!password.matches(".*[A-Z].*")){
            return new Result(false, "weak password! password should contains at least one uppercase letter.");
        }
        if (!password.matches(".*[0-9].*")){
            return new Result(false, "weak password! password should contains at least one number.");
        }
        if (!password.matches(".*[!#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")){
            return new Result(false, "weak password! password should contains at least one special character.");
        }
        if(!password.equals(passwordConfirm)){
            return new Result(false, "password does not match! enter your password again.");
        }

        User newUser = new User(username,password,nickName,email,gender);
        App.setLoggedInUser(newUser);

        return new Result(true, "success register!");
    }

    public static boolean isUsernameTaken(String username) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("username", username);

        Message message = new Message(body, Message.Type.IS_USERNAME_TAKEN);
        Message response = App.sendAndWait(message);

        if (response == null) return false;

        return response.getFromBody("isTaken");
    }

    public String generateRandomPassword() {
        final String LOWER = "abcdefghijklmnopqrstuvwxyz";
        final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        final String SPECIAL = "!#$%^&*()=+{}[]|\\/:;'\",<>?"; // No whitespace
        final String ALL_CHARS = LOWER + UPPER + DIGITS + SPECIAL;
        final SecureRandom random = new SecureRandom();

        int length = 8 + random.nextInt(13); // 8-20 chars

        List<Character> password = new ArrayList<>();

        // Ensure at least one of each type (lower, upper, digit, special)
        password.add(LOWER.charAt(random.nextInt(LOWER.length())));
        password.add(UPPER.charAt(random.nextInt(UPPER.length())));
        password.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.add(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // Fill the rest with random allowed chars
        for (int i = 4; i < length; i++) {
            password.add(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        Collections.shuffle(password); // Mix the characters

        // Convert to String
        StringBuilder sb = new StringBuilder();
        for (char c : password) {
            sb.append(c);
        }

        return sb.toString();
    }

    public Result setSecurityQuestion(String username, String securityQuestionNumber, String answer, String answerConfirm) {
        int number;
        try {
            number = Integer.parseInt(securityQuestionNumber);
        } catch (NumberFormatException e) {
            return new Result(false, "Security question number must be a number!");
        }

        SecurityQuestions selectedQuestion = SecurityQuestions.fromNumber(number);
        if (selectedQuestion == null) {
            return new Result(false, "Invalid security question number!");
        }

        if (!answer.equals(answerConfirm)) {
            return new Result(false, "Answer and confirmation do not match!");
        }

        User user = App.getLoggedInUser();

        user.setUserSecurityQuestion(selectedQuestion);
        user.setSecurityAnswer(answer);

        HashMap<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("password", user.getPassword());
        body.put("nickName", user.getNickName());
        body.put("email", user.getEmail());
        body.put("gender", user.getGender());
        body.put("securityQuestion", selectedQuestion.getNumber());
        body.put("answer", answer);

        Message message = new Message(body, Message.Type.REGISTER);

        Message response = App.sendAndWait(message);

        if (response == null) return new Result(false, "No response from server!");

        boolean success = response.getFromBody("success");

        if (!success) return new Result(false, "Failed to update security question on server!");


        return new Result(true, "Security question set successfully!");
    }

    public Result showMenus() {
        String output = "you can go to these menus from Main menu:\n1- Profile Menu\n2- Login Menu\n3- Game Menu";
        return new Result(true, output);
    }


    public Result showCurrentMenu(){
        return new Result(true, App.getCurrentMenu().getName());
    }


    public com.badlogic.gdx.scenes.scene2d.ui.Skin getSkin() {
        return GameAssetManager.getGameAssetManager().getSkin();
    }

}
