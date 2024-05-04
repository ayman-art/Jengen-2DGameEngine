package com.ayman.jengen.network.server.commands;

import com.ayman.jengen.network.server.GameServer;

import java.util.UUID;

public class AcknowledgeCommand extends Command {

    private final GameServer server;
    private final UUID clientID;
    private final String playerName;

    public AcknowledgeCommand(GameServer server, UUID clientID, String playerName) {
        this.server = server;
        this.clientID = clientID;
        this.playerName = playerName;
    }

    @Override
    public void execute() {
        System.out.println("Acknowledging client Command");
        synchronized (server.getClients()) {
            for (int i = 0; i < server.getClients().size(); i++) {
                if (server.getClients().get(i).getUUID().equals(clientID) ) {
                    server.getClients().get(i).addPlayer(playerName);
                    System.out.println("Client acknowledged with id " + clientID);
                    return;
                }
            }
            System.out.println("Client not found");
        }
    }
}
