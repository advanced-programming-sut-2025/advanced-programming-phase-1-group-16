package com.group16.stardewvalley.controller.menu;


import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.command.LoginMenuCommands;
import com.group16.stardewvalley.model.command.Menu;
import com.group16.stardewvalley.model.user.Result;
import com.group16.stardewvalley.model.user.SecurityQuestions;
import com.group16.stardewvalley.model.user.User;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.group16.stardewvalley.model.user.User.getUserByUsername;


public class LoginMenuController  {

    //sign up  methods
    public Result register(String username, String password, String passwordConfirm, String nickName, String email, String gender) {

        if (LoginMenuCommands.Username.getMatcher(username) == null) {
            return new Result(false, "username format is invalid!");
        }
        if (LoginMenuCommands.Email.getMatcher(email) == null) {
            return new Result(false, "email format is invalid!");
        }


        //how to turn back to enter password again?
        if(password.equals("random") || password.equals("Random")){
            return new Result(false, generateRandomPassword());
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

        //successful
        App.users.add(new User(username,password,nickName,email,gender));

        //make a list of security questions and show to user
        String securityQuestionList = "choose a security question and enter its number: \n" +
                "1- " + SecurityQuestions.q1.getQuestion() + "\n" +
                "2- " + SecurityQuestions.q2.getQuestion() + "\n" + "3- " + SecurityQuestions.q3.getQuestion() + "\n"+
                "4- " + SecurityQuestions.q4.getQuestion() + "\n" + "5- " + SecurityQuestions.q5.getQuestion() + "\n";

        return new Result(true, securityQuestionList);
    }

    private String generateRandomPassword() {

        final String LOWER = "abcdefghijklmnopqrstuvwxyz";
        final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        final String SPECIAL = "!@#$%^&*()-_=+[]{}<>?/";
        final String ALL_CHARS = LOWER + UPPER + DIGITS + SPECIAL;
        final SecureRandom random = new SecureRandom();


        int length = 8 + random.nextInt(13); // random length between 8 and 20

        List<Character> password = new ArrayList<>();

        // Ensure at least one of each required type
        password.add(LOWER.charAt(random.nextInt(LOWER.length())));
        password.add(UPPER.charAt(random.nextInt(UPPER.length())));
        password.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.add(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // Fill the rest
        for (int i = 4; i < length; i++) {
            password.add(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        Collections.shuffle(password);

        StringBuilder sb = new StringBuilder();
        for (char c : password) {
            sb.append(c);
        }

        return sb.toString();
    }

    public Result setSecurityQuestion(String securityQuestion, String answer, String answerConfirm) {
        if (!(securityQuestion.equals("1") || securityQuestion.equals("2") || securityQuestion.equals("3")
            || securityQuestion.equals("4") || securityQuestion.equals("5"))) {
            return new Result(false, "question number is invalid!");
        }
        if(!answer.equals(answerConfirm)){
            return new Result(false, "your answer confirm does not match with answer!");
        }

        //correct inputs
        switch (securityQuestion){
            case "1": App.getLoggedInUser().setUserSecurityQuestion(SecurityQuestions.q1);
            case "2": App.getLoggedInUser().setUserSecurityQuestion(SecurityQuestions.q2);
            case "3": App.getLoggedInUser().setUserSecurityQuestion(SecurityQuestions.q3);
            case "4": App.getLoggedInUser().setUserSecurityQuestion(SecurityQuestions.q4);
            case "5": App.getLoggedInUser().setUserSecurityQuestion(SecurityQuestions.q5);
        }
        App.getLoggedInUser().setSecurityAnswer(answer);

        return new Result(true, "security question set successfully!");


    }


    //login  methods
    public Result login(String username, String password, boolean stayLoggedIn){
        User user = getUserByUsername(username);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        }
        if(!(user.getPassword().equals(password))) {
            return new Result(false, "password is incorrect!");
        }

        App.setLoggedInUser(user);
        App.getLoggedInUser().setLogged_in_flag(stayLoggedIn);
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "logged in successfully!");
    }

    public Result forgetPassword(String username){
        User user = getUserByUsername(username);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        }
        return new Result(true, "answer to your security question:\n" + user.getUserSecurityQuestion().getQuestion());
    }

    public Result checkSecurityAnswer(String username, String answer){
        User user = getUserByUsername(username);
        if(!user.getSecurityAnswer().equals(answer)) {
            return new Result(false, "your answer is incorrect!");
        }
        return new Result(true, "enter a new password, or 'random' if you want a random password.");
    }

    public Result getNewPassword(String username, String password){
        //validate password
        //how to turn back to enter password again?
        if(password.equals("random") || password.equals("Random")){
            return new Result(false, generateRandomPassword());
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
        if (!password.matches(".*[!#$%^&*()_+\\-=\\[\\]{};':\"|,.<>/?].*")){
            return new Result(false, "weak password! password should contains at least one special character.");
        }

        //Strong password -> set as new password
        User user = getUserByUsername(username);
        user.setPassword(password);

        return new Result(true, "password changed successfully!");

    }



    public Result showCurrentMenu(){
        return new Result(true, App.getCurrentMenu().getName());
    }




}
