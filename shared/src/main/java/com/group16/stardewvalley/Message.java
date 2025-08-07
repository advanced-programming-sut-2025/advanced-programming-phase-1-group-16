package com.group16.stardewvalley;

import java.util.HashMap;

public class Message {
	private Type type;
	private HashMap<String, Object> body;

	public Message() {}

	public Message(HashMap<String, Object> body, Type type) {
		this.body = body;
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public <T> T getFromBody(String fieldName) {
		return (T) body.get(fieldName);
	}

	public int getIntFromBody(String fieldName) {
		return (int) ((double) ((Double) body.get(fieldName)));
	}

    //TODO change the enum
    public enum Type {
		command,
        response,
        error,
        REGISTER,
        IS_USERNAME_TAKEN,
        REGISTER_RESULT,
        USERNAME_TAKEN_RESULT,
        GET_USER_INFO,
        UPDATE_SECURITY_QUESTION,
        UPDATE_PASSWORD,
        UPDATE_NICKNAME,
        UPDATE_USERNAME,
        UPDATE_EMAIL,
        DELETE_USER,
        CREATE_LOBBY,
        JOIN_LOBBY,
        GET_LOBBY_LIST,
        LEAVE_LOBBY,
        SET_LOBBY_VISIBILITY,
        SEARCH_LOBBY,
        SET_READY_STATE,
        LOBBY_UPDATE,
        START_GAME,
	}
}
