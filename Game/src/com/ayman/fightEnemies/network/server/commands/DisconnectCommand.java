package com.ayman.fightEnemies.network.server.commands;

import com.ayman.fightEnemies.network.server.GameServer;

import java.util.UUID;

public class DisconnectCommand extends Command{

    private final GameServer server;

    private final UUID clientID;

    public DisconnectCommand(GameServer server, UUID clientID) {
        this.server = server;
        this.clientID = clientID;
    }

    @Override
    public void execute() {
        System.out.println("Disconnecting client Command");
        synchronized (server.getClients()) {
            for (int i = 0; i < server.getClients().size(); i++) {
                server.getClients().removeIf(client -> client.getUUID().equals(clientID));
            }
        }
    }
}
