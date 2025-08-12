package com.group16.stardewvalley.app;

import com.group16.stardewvalley.ConnectionThread;
import com.group16.stardewvalley.JSONUtils;
import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.ServerApp;
import com.group16.stardewvalley.controller.ServerConnectionController;
import com.group16.stardewvalley.model.user.User;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static com.group16.stardewvalley.ServerApp.TIMEOUT_MILLIS;


public class ClientConnectionThread extends ConnectionThread {

    private User connectedUser;

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User user) {
        this.connectedUser = user;
    }


    public ClientConnectionThread(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    public void run() {
        super.run();
        ServerApp.removeClientConnection(this);
    }

    @Override
    protected boolean handleMessage(Message message) {
        return switch (message.getType()) {
            case UPDATE_SECURITY_QUESTION -> {
                sendMessage(ServerConnectionController.updateSecurityQuestion(message));
                yield true;
            }
            case REGISTER -> {
                sendMessage(ServerConnectionController.register(this, message));
                ServerConnectionController.refreshOnlinePlayers();
                yield true;
            }
            case IS_USERNAME_TAKEN -> {
                sendMessage(ServerConnectionController.isUsernameTaken(message));
                yield true;
            }
            case GET_USER_INFO -> {
                sendMessage(ServerConnectionController.getUserInfo(this, message));
                ServerConnectionController.refreshOnlinePlayers();
                yield true;
            }
            case UPDATE_EMAIL -> {
                sendMessage(ServerConnectionController.updateEmail(message));
                yield true;
            }
            case UPDATE_NICKNAME -> {
                sendMessage(ServerConnectionController.updateNickname(message));
                yield true;
            }
            case UPDATE_PASSWORD -> {
                sendMessage(ServerConnectionController.updatePassword(message));
                yield true;
            }
            case UPDATE_USERNAME -> {
                sendMessage(ServerConnectionController.updateUsername(message));
                yield true;
            }
            case DELETE_USER -> {
                sendMessage(ServerConnectionController.deleteUser(message));
                yield true;
            }
            case CREATE_LOBBY -> {
                sendMessage(ServerConnectionController.addLobby(message));
                ServerConnectionController.refreshOnlinePlayers();
                yield true;
            }
            case GET_LOBBY_LIST -> {
                sendMessage(ServerConnectionController.getLobbies(message));
                yield true;
            }
            case JOIN_LOBBY -> {
                sendMessage(ServerConnectionController.joinLobby(message));
                ServerConnectionController.refreshOnlinePlayers();
                yield true;
            }
            case LEAVE_LOBBY -> {
                sendMessage(ServerConnectionController.leaveLobby(message));
                ServerConnectionController.refreshOnlinePlayers();
                yield true;
            }
            case SET_LOBBY_VISIBILITY -> {
                sendMessage(ServerConnectionController.setLobbyVisibility(message));
                yield true;
            }
            case START_GAME -> {
                sendMessage(ServerConnectionController.startGame(message));
                yield true;
            }
            case SEARCH_LOBBY -> {
                sendMessage(ServerConnectionController.searchLobby(message));
                yield true;
            }
            case REQUEST_ONLINE_PLAYERS -> {
                sendMessage(ServerConnectionController.getOnlinePlayers());
                yield true;
            }
            default -> false;
        };
    }

    @Override
    public boolean initialHandshake() {
        try {
            if (refreshStatus()) return false;

            ServerApp.addClientConnection(this);
            return true;

        } catch (Exception e) {
            this.end();
            return false;
        }
    }

    public boolean refreshStatus() {
        try {
            HashMap<String, Object> body = new HashMap<>();
            body.put("command", "status");

            Message request = new Message(body, Message.Type.command);
            Message response = sendAndWaitForResponse(request, TIMEOUT_MILLIS);

            if (response == null || !response.getFromBody("response").equals("ok")) {
                return true;
            }
            String peerIP = response.getFromBody("peer");
            int listenPort = response.getIntFromBody("listen_port");

            setOtherSideIP(peerIP);
            setOtherSidePort(listenPort);
            return false;

        } catch (Exception ignored) {
            return true;
        }
    }

    @Override
    public void end() {
        super.end();
        ServerApp.removeOnlinePlayer(connectedUser);
        ServerConnectionController.refreshOnlinePlayers();
    }

}
