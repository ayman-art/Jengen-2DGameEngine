package com.ayman.fightEnemies.network.client.commands;

import com.ayman.fightEnemies.network.client.GameClient;

import java.util.UUID;

public class SetIdCommand extends ClientCommand {

    private GameClient gameClient;
    private final UUID id;

    public SetIdCommand(GameClient gameClient, UUID id){
        this.gameClient = gameClient;
        this.id = id;
    }


    public SetIdCommand(UUID id){
        this.id = id;
    }

    @Override
    public void execute() {
        gameClient.setUUID(id);
    }
}