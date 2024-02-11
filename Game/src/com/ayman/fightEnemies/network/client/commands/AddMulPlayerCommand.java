package com.ayman.fightEnemies.network.client.commands;

import com.ayman.fightEnemies.Game;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.network.client.GameClient;

public class AddMulPlayerCommand extends ClientCommand{

    GameClient gameClient;
    private String name;

    public AddMulPlayerCommand(GameClient gameClient, String name) {
        this.gameClient = gameClient;
        this.name = name;
    }
    @Override
    public void execute() {
        synchronized(this.gameClient.getGame().getLevel()) {
            if(this.gameClient.getGame().getLevel().getPlayer(name) == null) {
                this.gameClient.getGame().getLevel().add(new Player(name, 3 * 16, 10 * 16, null));
        }
        this.gameClient.sendData("A" + this.gameClient.getUUID() + " " + name);
    }
}


    }
