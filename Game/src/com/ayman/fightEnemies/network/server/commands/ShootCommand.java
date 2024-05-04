package com.ayman.fightEnemies.network.server.commands;

import com.ayman.fightEnemies.network.server.GameServer;

import java.util.UUID;

/**
 * This class is responsible for shooting the player and sending the shooting information to the clients.

 */
public class ShootCommand extends Command{
    private final GameServer server;
    private final UUID clientID;
    private final int xPosition, yPosition;
    private final double shootingAngle;

    public ShootCommand(GameServer server, UUID clientID, int xPosition, int yPosition, double shootingAngle) {
        this.server = server;
        this.clientID = clientID;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.shootingAngle = shootingAngle;
    }

    @Override
    public void execute() {
        String playerName = server.getClient(clientID).getName();
        synchronized (server.getClients()) {
            for (int i = 0; i < server.getClients().size(); i++) {
                if (server.getClients().get(i).containsPlayer(playerName)) {
                    server.send("S" + playerName + " " + xPosition + " " + yPosition + " " + shootingAngle, server.getClients().get(i));
                    return;
                }
            }
        }
    }

}
