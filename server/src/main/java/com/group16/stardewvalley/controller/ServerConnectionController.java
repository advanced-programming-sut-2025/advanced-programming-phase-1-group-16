package com.group16.stardewvalley.controller;


import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.ServerApp;
import com.group16.stardewvalley.app.ClientConnectionThread;
import com.group16.stardewvalley.data.UserDataSQL;
import com.group16.stardewvalley.data.UserJsonUtil;
import com.group16.stardewvalley.model.user.SecurityQuestions;
import com.group16.stardewvalley.model.user.User;

import java.util.*;

public class ServerConnectionController {
    public static Message register(Message message) {
        String username = message.getFromBody("username");
        String password = message.getFromBody("password");
        String nickName = message.getFromBody("nickName");
        String email = message.getFromBody("email");
        String gender = message.getFromBody("gender");
        SecurityQuestions securityQuestion = null;
        try {
            String questionStr = message.getFromBody("securityQuestion");
            if (questionStr != null)
                securityQuestion = SecurityQuestions.valueOf(questionStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid security question: " + e.getMessage());
        }
        String answer = message.getFromBody("answer");

        User newUser = new User(username,password,nickName,email,gender);
        newUser.setSecurityAnswer(answer);
        newUser.setUserSecurityQuestion(securityQuestion);

        UserDataSQL.getInstance().addUser(newUser);

        HashMap<String, Object> body = new HashMap<>();

        body.put("result", "user registered successfully!");

        return new Message(body, Message.Type.response);
    }

    public static Message isUsernameTaken(Message message) {
        String username = message.getFromBody("username");
        User user = UserDataSQL.getInstance().getUserByUsername(username);

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("isTaken", user != null);

        return new Message(responseBody, Message.Type.IS_USERNAME_TAKEN);
    }

    public static Message updateSecurityQuestion(Message message) {
        String username = message.getFromBody("username");
        String question = message.getFromBody("question");
        String answer = message.getFromBody("answer");

        boolean success = UserDataSQL.getInstance().updateSecurityQuestion(username, question, answer);

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", success);

        return new Message(responseBody, Message.Type.UPDATE_SECURITY_QUESTION);
    }

    public static Message updatePassword(Message message) {
        String username = message.getFromBody("username");
        String password = message.getFromBody("password");

        UserDataSQL.getInstance().updatePassword(username, password);

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);

        return new Message(responseBody, Message.Type.UPDATE_PASSWORD);
    }

    public static Message updateNickname(Message message) {
        String username = message.getFromBody("username");
        String nickName = message.getFromBody("nickName");

        boolean success = UserDataSQL.getInstance().updateNickname(username, nickName);

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", success);

        return new Message(responseBody, Message.Type.UPDATE_NICKNAME);
    }

    public static Message updateUsername(Message message) {
        String oldUsername = message.getFromBody("oldUsername");
        String newUsername = message.getFromBody("newUsername");

       UserDataSQL.getInstance().updateUsername(oldUsername, newUsername);

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);

        return new Message(responseBody, Message.Type.UPDATE_USERNAME);
    }

    public static Message updateEmail(Message message) {
        String username = message.getFromBody("username");
        String email = message.getFromBody("email");

        UserDataSQL.getInstance().updateEmail(username, email);

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);

        return new Message(responseBody, Message.Type.UPDATE_EMAIL);
    }

    public static Message deleteUser(Message message) {
        String username = message.getFromBody("username");
        UserDataSQL.getInstance().deleteUser(username);
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        return new Message(responseBody, Message.Type.DELETE_USER);
    }


    public static Message getUserInfo(Message message) {
        String username = message.getFromBody("username");
        User user = UserDataSQL.getInstance().getUserByUsername(username);
        HashMap<String, Object> responseBody = new HashMap<>();
        if (user == null) {
            responseBody.put("success", false);
            responseBody.put("user", null);
            return new Message(responseBody, Message.Type.GET_USER_INFO);
        }
        responseBody.put("username", username);
        responseBody.put("password", user.getPassword());
        responseBody.put("nickName", user.getNickName());
        responseBody.put("email", user.getEmail());
        responseBody.put("gender", user.getGender());
        responseBody.put("securityQuestion", user.getUserSecurityQuestion());
        responseBody.put("answer", user.getSecurityAnswer());
        responseBody.put("success", true);
        return new Message(responseBody, Message.Type.GET_USER_INFO);
    }




	private static Message buildErrorResponse(String errorType) {
		HashMap<String, Object> errorBody = new HashMap<>();
		errorBody.put("response", "error");
		errorBody.put("error", errorType);
		return new Message(errorBody, Message.Type.response);
	}


	public static Map<String, List<String>> getSends(ClientConnectionThread connection) {
		// Build the command message to request "get_sends"
		HashMap<String, Object> body = new HashMap<>();
		body.put("command", "get_sends");
		Message command = new Message( body, Message.Type.command);

		// Send the command and wait for a response
		Message response = connection.sendAndWaitForResponse(command, ServerApp.TIMEOUT_MILLIS);

		// If there's no response or no expected data, return an empty map
		if (response == null) {
			return new HashMap<>();
		}

		Map<String, List<String>> sentFiles = response.getFromBody("sent_files");
		return sentFiles != null ? sentFiles : new HashMap<>();
	}


	public static Map<String, List<String>> getReceives(ClientConnectionThread connection) {
		HashMap<String, Object> body = new HashMap<>();
		body.put("command", "get_receives");
		Message command = new Message(body, Message.Type.command);

		Message response = connection.sendAndWaitForResponse(command, ServerApp.TIMEOUT_MILLIS);

		if (response == null) {
			return new HashMap<>();
		}

		Map<String, List<String>> receivedFiles = response.getFromBody("received_files");
		return receivedFiles != null ? receivedFiles : new HashMap<>();
	}
}
