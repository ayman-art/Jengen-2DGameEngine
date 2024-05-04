package com.ayman.fightEnemies.network.server.commands;

import com.ayman.fightEnemies.network.server.GameServer;
import com.ayman.fightEnemies.network.server.ServerClient;

/**
 * This class is responsible for updating the player's position and health.

 */
public class UpdateMulPlayerCommand extends Command{
    private final GameServer gameServer;
    private final ServerClient serverClient;

    private final int x, y, health;

    public UpdateMulPlayerCommand(GameServer gameServer, ServerClient serverClient, int x, int y, int health) {
        this.gameServer = gameServer;
        this.serverClient = serverClient;
        this.x = x;
        this.y = y;
        this.health = health;
    }

    public void execute() {

        System.out.println("Updating player " + this.serverClient.getName() + " to " + x + " " + y + " " + health);

        synchronized (this.gameServer.getClients()) {
            for (int i = 0; i < this.gameServer.getClients().size(); i++) {
                gameServer.sendToAll("U" + this.serverClient.getName() + " " + x + " " + y + " " + health);
            }
        }
    }


}
