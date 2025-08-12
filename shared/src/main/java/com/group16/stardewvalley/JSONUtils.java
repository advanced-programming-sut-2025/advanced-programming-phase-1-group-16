package com.group16.stardewvalley;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONUtils {
    private static final GsonBuilder gsonBuilder = new GsonBuilder();
    private static final Gson gson;

    static {
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    public synchronized static String toJson(Message message) {
        return gson.toJson(message);
    }

    public synchronized static Message fromJson(String json) {
        return gson.fromJson(json, Message.class);
    }

    // Generic serialization for any object (e.g., Game)
    public synchronized static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    // Generic deserialization for any object
    public synchronized static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
