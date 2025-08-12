package com.group16.stardewvalley.app;


import com.group16.stardewvalley.ConnectionThread;
import com.group16.stardewvalley.JSONUtils;
import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.controllers.C2SConnectionController;

import java.io.IOException;
import java.net.Socket;

import static com.group16.stardewvalley.app.ClientApp.TIMEOUT_MILLIS;


public class C2SConnectionThread extends ConnectionThread {

	protected C2SConnectionThread(Socket socket) throws IOException {
		super(socket);
	}

	@Override
	public boolean initialHandshake() {
		try {
			socket.setSoTimeout(TIMEOUT_MILLIS);

			dataInputStream.readUTF();
			Message message1 = C2SConnectionController.status();
			sendMessage(message1);

			socket.setSoTimeout(0);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

    @Override
    protected boolean handleMessage(Message message) {
        System.out.println("Received message type: " + message.getType());
        return switch (message.getType()) {
            case SHOW_FARM_SELECTION -> {
                C2SConnectionController.showFarmSelection(message);
                yield true;
            }
            case GAME_STATE_UPDATE -> {
                // Get the game JSON string from the message body
                String gameJson = message.getFromBody("gameState");

                // Deserialize the JSON into a Game object
                com.group16.stardewvalley.model.app.Game updatedGame = JSONUtils.fromJson(gameJson, com.group16.stardewvalley.model.app.Game.class);

                // Update your client-side active game
                com.group16.stardewvalley.model.app.App.setActiveGame(updatedGame);

                System.out.println("Game state updated from server.");
                yield true;
            }
            default -> false;
        };
    }


	@Override
	public void run() {
		super.run();
		ClientApp.endAll();
		System.exit(0);
	}
}
