package com.ayman.fightEnemies.network.server.commands;

import com.ayman.fightEnemies.network.client.GameClient;
import com.ayman.fightEnemies.network.server.GameServer;
import com.ayman.fightEnemies.network.server.ServerClient;

public class UpdateMulPlayerCommand extends Command{
    private final GameServer gameServer;
    private final ServerClient serverClient;

    private int x, y;

    public UpdateMulPlayerCommand(GameServer gameServer, ServerClient serverClient, int x, int y) {
        this.gameServer = gameServer;
        this.serverClient = serverClient;
        this.x = x;
        this.y = y;
    }

    public void execute() {
        synchronized (this.gameServer.getClients()) {
            for (int i = 0; i < this.gameServer.getClients().size(); i++) {
                gameServer.sendToAll("U" + this.serverClient.getName() + " " + x + " " + y);
            }
        }
    }


}
