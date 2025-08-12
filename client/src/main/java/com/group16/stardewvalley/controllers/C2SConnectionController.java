package com.group16.stardewvalley.controllers;


import com.badlogic.gdx.Gdx;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.app.C2SConnectionThread;
import com.group16.stardewvalley.app.ClientApp;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.view.menuGraphics.FarmSelectionScreen;
import com.group16.stardewvalley.view.menuGraphics.OnlinePlayersView;

import java.io.File;
import java.util.HashMap;

public class C2SConnectionController {
	public static void refreshOnlinePlayers(Message message) {
		OnlinePlayersView.fetchOnlinePlayers(message);
	}

    public static void showFarmSelection(Message message) {
        Gdx.app.postRunnable(() -> {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new FarmSelectionScreen(GameAssetManager.getGameAssetManager().getSkin()));
        });
    }


    public static Message status() {
		HashMap<String, Object> body = new HashMap<>();
		body.put("command", "status");
		body.put("response", "ok");
		body.put("peer", ClientApp.getClientIP());
		body.put("listen_port", ClientApp.getClientPort());

		return new Message(body, Message.Type.response);
	}

	public static Message getFilesList() {
		HashMap<String, Object> messageContent = new HashMap<>();
		messageContent.put("command", "get_files_list");
		messageContent.put("response", "ok");

		HashMap<String, String> files = new HashMap<>();


		messageContent.put("files", files);
		return new Message(messageContent, Message.Type.response);
	}

	private static Message getSends() {
		HashMap<String, Object> payload = new HashMap<>();
		payload.put("command", "get_sends");
		payload.put("response", "ok");

		Message reply = new Message(payload, Message.Type.response);
		return reply;
	}

	private static Message getReceives() {
		HashMap<String, Object> messageBody = new HashMap<>();
		messageBody.put("response", "ok");
		messageBody.put("command", "get_receives");

		Message message =  new Message(messageBody, Message.Type.response);
		return message;
	}


}
