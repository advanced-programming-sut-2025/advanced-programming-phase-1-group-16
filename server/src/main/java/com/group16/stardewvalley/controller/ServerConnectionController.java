package com.group16.stardewvalley.controller;


import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.ServerApp;
import com.group16.stardewvalley.app.ClientConnectionThread;
import com.group16.stardewvalley.data.UserDataSQL;
import com.group16.stardewvalley.data.UserJsonUtil;
import com.group16.stardewvalley.model.user.User;

import java.util.*;

public class ServerConnectionController {
    public static Message register(Message message) {
        String username = message.getFromBody("username");
        String password = message.getFromBody("password");
        String nickName = message.getFromBody("nickName");
        String email = message.getFromBody("email");
        String gender = message.getFromBody("gender");

        User newUser = new User(username,password,nickName,email,gender);
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
