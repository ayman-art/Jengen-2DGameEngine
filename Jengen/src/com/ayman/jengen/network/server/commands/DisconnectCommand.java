package com.ayman.jengen.network.server.commands;

import com.ayman.jengen.network.server.GameServer;

import java.util.UUID;

/**
 * This class is responsible for disconnecting the client from the server.

 */
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
                if (server.getClients().get(i).getUUID().equals(clientID)) {
                    server.getClients().remove(i);
                    System.out.println("Client disconnected with id " + clientID);
                    return;
                }
            }
            System.out.println("Client not found");
        }
    }
}
