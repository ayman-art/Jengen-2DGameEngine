package com.ayman.jengen.network.client.commands;

import com.ayman.jengen.network.client.GameClient;

import java.util.UUID;

/**
 * This class is responsible for setting the id of the player.

 */
public class SetIdCommand extends ClientCommand {

    private final GameClient gameClient;
    private final UUID id;

    public SetIdCommand(GameClient gameClient, UUID id){
        this.gameClient = gameClient;
        this.id = id;
    }



    @Override
    public synchronized void execute() {
        gameClient.setUUID(id);
    }
}