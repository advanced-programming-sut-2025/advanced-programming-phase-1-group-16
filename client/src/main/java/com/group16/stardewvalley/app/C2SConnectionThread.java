package com.group16.stardewvalley.app;


import com.group16.stardewvalley.ConnectionThread;
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
			case REQUEST_ONLINE_PLAYERS -> {
				C2SConnectionController.refreshOnlinePlayers(message);
				yield true;
			}
            case GAME_STARTED -> {
                C2SConnectionController.startGame(message);
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
