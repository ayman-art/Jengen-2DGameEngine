package com.ayman.jengen.network.client.commands;

import com.ayman.jengen.Graphics.Sprite;
import com.ayman.jengen.entity.mob.Player;
import com.ayman.jengen.network.client.GameClient;

/**
 * This class is responsible for adding a new player to the game.

 */
public class AddMulPlayerCommand extends ClientCommand{

    GameClient gameClient;
    private final String name;

    public AddMulPlayerCommand(GameClient gameClient, String name) {
        this.gameClient = gameClient;
        this.name = name;
    }
    @Override
    public void execute() {
        synchronized(this.gameClient.getGame().getLevel()) {
            if(this.gameClient.getGame().getLevel().getPlayer(name) == null) {
                this.gameClient.getGame().getLevel().add(new Player(name, 3 * Sprite.TILE_SIZE, 10 * Sprite.TILE_SIZE, null, null));
        }
        this.gameClient.sendData("A" + this.gameClient.getUUID() + " " + name);
    }
}


    }
