package com.ayman.jengen.network.client.commands;

import com.ayman.jengen.entity.mob.IPlayer;
import com.ayman.jengen.network.client.GameClient;

/**
 * This class is responsible for shooting a bullet from the player.

 */
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
            IPlayer player = this.gameClient.getGame().getLevel().getPlayer(playerName);
            player.setXY(xPosition, yPosition); //To ensure that the new position is set
            player.shoot(xPosition, yPosition, shootingAngle);
        }
    }
}
