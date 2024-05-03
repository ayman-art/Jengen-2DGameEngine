package com.ayman.fightEnemies.network.client.commands;

import com.ayman.fightEnemies.network.client.GameClient;

public class UpdateMulPlayerClientCommand extends ClientCommand{

    private final GameClient gameClient;
    private final String name;
    private final int x, y, heath;
    public UpdateMulPlayerClientCommand(GameClient gameClient, String name, int x, int y, int health) {
        this.gameClient = gameClient;
        this.name = name;
        this.x = x;
        this.y = y;
        this.heath = health;
    }

    @Override
    public void execute() {
        this.gameClient.getGame().getLevel().getPlayer(name).setXY(x, y);
        this.gameClient.getGame().getLevel().getPlayer(name).setHealth(heath);
    }


}
