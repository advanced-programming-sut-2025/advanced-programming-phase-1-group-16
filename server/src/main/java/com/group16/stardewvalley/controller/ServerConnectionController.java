package com.group16.stardewvalley.controller;


import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.ServerApp;
import com.group16.stardewvalley.app.ClientConnectionThread;
import com.group16.stardewvalley.app.PlayerSession;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.data.UserDataSQL;
import com.group16.stardewvalley.model.Lobby;
import com.group16.stardewvalley.model.LobbyInfo;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.PlayerSessionInfo;
import com.group16.stardewvalley.model.user.SecurityQuestions;
import com.group16.stardewvalley.model.user.User;

import java.util.*;

public class ServerConnectionController {
    public static Message register(ClientConnectionThread connectionThread, Message message) {
        String username = message.getFromBody("username");
        String password = message.getFromBody("password");
        String nickName = message.getFromBody("nickName");
        String email = message.getFromBody("email");
        String gender = message.getFromBody("gender");
        SecurityQuestions securityQuestion = null;
        try {
            int questionNumber = ((Number) message.getFromBody("securityQuestion")).intValue();
            securityQuestion = SecurityQuestions.fromNumber(questionNumber);

        } catch (Exception e) {
            System.out.println("Invalid security question: " + e.getMessage());
        }
        String answer = message.getFromBody("answer");

        User newUser = new User(username,password,nickName,email,gender);
        newUser.setSecurityAnswer(answer);
        newUser.setUserSecurityQuestion(securityQuestion);

        UserDataSQL.getInstance().addUser(newUser);

        connectionThread.setConnectedUser(newUser);
        ServerApp.addOnlinePlayer(new PlayerSession(newUser, connectionThread, null), newUser);



        HashMap<String, Object> body = new HashMap<>();

        body.put("success", true);
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

    public static Message getUserInfo(ClientConnectionThread connectionThread, Message message) {
        String username = message.getFromBody("username");
        User user = UserDataSQL.getInstance().getUserByUsername(username);
        HashMap<String, Object> responseBody = new HashMap<>();
        if (user == null) {
            responseBody.put("success", false);
            responseBody.put("user", null);
            return new Message(responseBody, Message.Type.GET_USER_INFO);
        }
        if (!ServerApp.isTheUserOnline(user)) {
            ServerApp.addOnlinePlayer(new PlayerSession(user, connectionThread, null), user);
        }
        connectionThread.setConnectedUser(user);
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

    public static Message addLobby(Message message) {
        String username = message.getFromBody("username");
        User creator = UserDataSQL.getInstance().getUserByUsername(username);
        if (creator == null) {
            HashMap<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            return new Message(responseBody, Message.Type.CREATE_LOBBY);
        }
        String lobbyName = message.getFromBody("lobbyName");
        boolean privateLobby = message.getFromBody("private");
        String password = "";
        if (privateLobby) {
            password = message.getFromBody("password");
        }
        LobbyManager.createLobby(lobbyName, creator, password, privateLobby);

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        return new Message(responseBody, Message.Type.CREATE_LOBBY);
    }

    public static Message getLobbies(Message message) {
        HashMap<String, Object> responseBody = new HashMap<>();

        List<LobbyInfo> lobbyInfoList = LobbyManager.getAllLobbies().stream()
            .map(lobby -> new LobbyInfo(
                lobby.getName(),
                lobby.getUsers().stream()
                    .map(User::getUsername)
                    .toList(),
                lobby.getPassword(),
                lobby.isPrivate(),
                lobby.getLobbyId(),
                lobby.getCreator().getUsername(),
                lobby.isVisible()
            ))
            .toList();

        responseBody.put("success", true);
        responseBody.put("lobbies", lobbyInfoList);

        return new Message(responseBody, Message.Type.GET_LOBBY_LIST);
    }

    public static Message joinLobby(Message message) {
        String username = message.getFromBody("username");
        User user = UserDataSQL.getInstance().getUserByUsername(username);
        if (user == null) {
            return buildErrorResponse("user not found!", Message.Type.JOIN_LOBBY);
        }
        String lobbyName = message.getFromBody("lobbyName");
        Lobby lobby = LobbyManager.getLobby(lobbyName);
        if (lobby == null) {
            return buildErrorResponse("lobby not found!", Message.Type.JOIN_LOBBY);
        }
        if (lobby.isPlayerExists(user)) {
            return buildErrorResponse("You are already in the lobby!", Message.Type.JOIN_LOBBY);
        }
        lobby.addPlayer(user);

        PlayerSession session = ServerApp.getOnlinePlayers().get(username);
        session.setCurrentLobby(lobby);

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        return new Message(responseBody, Message.Type.JOIN_LOBBY);
    }

    public static Message leaveLobby(Message message) {
        String username = message.getFromBody("username");
        User user = UserDataSQL.getInstance().getUserByUsername(username);
        if (user == null) {
            return buildErrorResponse("user not found!", Message.Type.JOIN_LOBBY);
        }
        String lobbyName = message.getFromBody("lobbyName");
        Lobby lobby = LobbyManager.getLobby(lobbyName);
        if (lobby == null) {
            return buildErrorResponse("lobby not found!", Message.Type.JOIN_LOBBY);
        }
        if (!lobby.isPlayerExists(user)) {
            return buildErrorResponse("You are not in the lobby!", Message.Type.JOIN_LOBBY);
        }
        if (lobby.getUsers().size() < 2) {
            LobbyManager.removeLobby(lobbyName);
        }
        if (lobby.getCreator().getUsername().equals(username)) {
            lobby.nextAdmin();
        }
        lobby.removePlayer(user);
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        return new Message(responseBody, Message.Type.JOIN_LOBBY);
    }

    public static Message setLobbyVisibility(Message message) {
        String lobbyName = message.getFromBody("lobbyName");
        Lobby lobby = LobbyManager.getLobby(lobbyName);
        if (lobby == null) {
            return buildErrorResponse("lobby not found!", Message.Type.JOIN_LOBBY);
        }
        boolean visible = message.getFromBody("visible");
        lobby.setVisible(visible);
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        return new Message(responseBody, Message.Type.SET_LOBBY_VISIBILITY);
    }

    public static Message searchLobby(Message message) {
        String lobbyId = message.getFromBody("id");
        Lobby lobby = LobbyManager.getLobbyById(lobbyId);
        if (lobby == null) {
            return buildErrorResponse("lobby not found!", Message.Type.SEARCH_LOBBY);
        }
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("lobbyName", lobby.getName());
        return new Message(responseBody, Message.Type.SEARCH_LOBBY);
    }

    public static void refreshOnlinePlayers() {
        Message message = getOnlinePlayers();
        for (PlayerSession playerSession : ServerApp.getOnlinePlayers().values()) {
            User user = playerSession.getUser();
            sendMessageToUser(user, message);
        }
    }

    public static Message getOnlinePlayers() {
        HashMap<String, Object> responseBody = new HashMap<>();

        List<PlayerSessionInfo> playerInfoList = ServerApp.getOnlinePlayers().values().stream()
                .map(session -> new PlayerSessionInfo(
                        session.getUser().getUsername(),
                        session.getCurrentLobby() != null
                                ? new LobbyInfo(
                                session.getCurrentLobby().getName(),
                                session.getCurrentLobby().getUsers().stream()
                                        .map(User::getUsername)
                                        .toList(),
                                session.getCurrentLobby().getPassword(),
                                session.getCurrentLobby().isPrivate(),
                                session.getCurrentLobby().getLobbyId(),
                                session.getCurrentLobby().getCreator().getUsername(),
                                session.getCurrentLobby().isVisible()
                        )
                                : null
                ))
                .toList();

        responseBody.put("success", true);
        responseBody.put("players", playerInfoList);

        return new Message(responseBody, Message.Type.REQUEST_ONLINE_PLAYERS);
    }

    public static Message startGame(Message message) {
        String lobbyId = message.getFromBody("lobbyId");
        Lobby lobby = LobbyManager.getLobbyById(lobbyId);

        if (lobby == null) {
            return buildErrorResponse("Lobby not found!", Message.Type.START_GAME);
        }

        if (lobby.getUsers().isEmpty()) {
            return buildErrorResponse("No players in lobby!", Message.Type.START_GAME);
        }

        ArrayList<Player> gamePlayers = new ArrayList<>();
        gamePlayers.add(new Player(App.getLoggedInUser()));
        for (User user : lobby.getUsers()) {
            if (user.getHasActiveGame()) {
                return buildErrorResponse("Player " + user.getUsername() + " is already in a game!", Message.Type.START_GAME);
            }
            gamePlayers.add(new Player(user));
        }


        Game newGame = new Game(new Player(lobby.getCreator()), gamePlayers);

        ServerApp.addGame(newGame);
        for (User user : lobby.getUsers()) {
            user.setHasActiveGame(true);
        }

        HashMap<String, Object> chooseFarmMessage = new HashMap<>();
        chooseFarmMessage.put("job", "show choose farm page");

        for (User user : lobby.getUsers()) {
            sendMessageToUser(user, new Message(chooseFarmMessage, Message.Type.SHOW_FARM_SELECTION));
        }


        //removeLobby(lobby.getName());

        HashMap<String, Object> body = new HashMap<>();
        body.put("success", true);
        return new Message(body, Message.Type.START_GAME);
    }

    public static void sendMessageToUser(User user, Message message) {
        boolean sent = false;
        for (ClientConnectionThread connection : ServerApp.getConnections()) {
            if (connection.getConnectedUser() != null &&
                connection.getConnectedUser().getUsername().equals(user.getUsername())) {
                connection.sendMessage(message);
                System.out.println("Sent " + message.getType() + " to " + user.getUsername());
                sent = true;
            }
        }
        if (!sent) {
            System.out.println("No connection found for user: " + user.getUsername());
        }
    }


    public static Message handleFarmSelectionReady(Message message) {
        String farmType = message.getFromBody("choice");
        String username = message.getFromBody("username");

        User user = UserDataSQL.getInstance().getUserByUsername(username);
        if (user == null) {
            return buildErrorResponse("User not found", Message.Type.FARM_SELECTION_READY);
        }

        Game game = ServerApp.getActiveGameByUser(user);
        if (game == null) {
            return buildErrorResponse("Active game not found for user", Message.Type.FARM_SELECTION_READY);
        }

        Player player = game.getPlayerByUser(user);
        if (player == null) {
            return buildErrorResponse("Player not found in game", Message.Type.FARM_SELECTION_READY);
        }

        GameMenuController gameMenuController = new GameMenuController();
        MapController mapController = new MapController();

        Result result = gameMenuController.chooseFarm(player, farmType);
        if (!result.isSuccessful()) {
            return buildErrorResponse(result.message(), Message.Type.FARM_SELECTION_READY);
        }

        String[] characterPaths = {
            "Character/maidnpc.png",
            "Character/gardenernpc.png",
            "Character/woman_016_npc.png",
            "Character/man_002_npc.png"
        };

        if (game.allReady()) {
            mapController.createMap();
            int index = 0;
            for (Player player1 : game.getPlayers()) {
                player1.setPlayerGraphics(characterPaths[index], 48, 64);
                index++;
                //sendMessageToUser(player1.getUser(), new Message(Map.of("mapInfo", "..."), Message.Type.GAME_STARTED));
            }
        }

        HashMap<String, Object> body = new HashMap<>();
        body.put("success", true);

        return new Message(body, Message.Type.FARM_SELECTION_READY);
    }




    private static Message buildErrorResponse(String errorType, Message.Type type) {
        HashMap<String, Object> errorBody = new HashMap<>();
        errorBody.put("success", false);
        errorBody.put("error", errorType);
        return new Message(errorBody, type);
    }
}
