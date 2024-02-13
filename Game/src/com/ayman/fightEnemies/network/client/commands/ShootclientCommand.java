package com.ayman.fightEnemies.network.client.commands;

import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.network.client.GameClient;

public class ShootclientCommand extends ClientCommand {


    private final GameClient gameClient;
    private final String playerName;
    private final int xPosition, yPosition;
    private final double shootingAngle;


    public ShootclientCommand(GameClient gameClient, String playerName, int xPosition, int yPosition, double shootingAngle) {
        this.gameClient = gameClient;
        this.playerName = playerName;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.shootingAngle = shootingAngle;
    }
    @Override
    public void execute() {
        synchronized (this.gameClient.getGame().getLevel().getPlayer()) {
            Player player = this.gameClient.getGame().getLevel().getPlayer(playerName);
            player.setXY(xPosition, yPosition); //To ensure that the new position is set
            player.shoot(xPosition, yPosition, shootingAngle);
        }
    }
}
