package com.group16.stardewvalley.controller;

import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.user.SecurityQuestions;
import com.group16.stardewvalley.model.user.User;

import java.util.HashMap;

public class MessageFactory {
    public static User getUser(String username) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("username", username);

        Message message = new Message(body, Message.Type.GET_USER_INFO);

        Message response = App.sendAndWait(message);
        if (response == null) {
            return null;
        }

        String correcrtPassword = response.getFromBody("password");
        String nickName = response.getFromBody("nickName");
        String email = response.getFromBody("email");
        String gender = response.getFromBody("gender");
        SecurityQuestions securityQuestion = null;
        try {
            String questionStr = message.getFromBody("securityQuestion");
            if (questionStr != null)
                securityQuestion = SecurityQuestions.valueOf(questionStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid security question: " + e.getMessage());
        }
        String answer = message.getFromBody("answer");

        User user = new User(username, correcrtPassword,nickName,email,gender);
        user.setSecurityAnswer(answer);
        user.setUserSecurityQuestion(securityQuestion);
        return user;
    }
}
