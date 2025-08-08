// client
package com.group16.stardewvalley.controllers;

import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.app.C2SConnectionThread;
import com.group16.stardewvalley.network.NetworkManager;

import static com.group16.stardewvalley.app.ClientApp.TIMEOUT_MILLIS;

public class ClientNetworkManager implements NetworkManager {
    private static C2SConnectionThread connection;

    public static void setConnection(C2SConnectionThread conn) {
        connection = conn;
    }

    @Override
    public Message sendAndWait(Message message) {
        return connection.sendAndWaitForResponse(message, TIMEOUT_MILLIS);
    }
}
