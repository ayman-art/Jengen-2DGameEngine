package com.ayman.fightEnemies.network.client.commands;

import com.ayman.fightEnemies.Game;

public class AddMulPlayerCommand extends ClientCommand{

    private Game game;
    private String name;
    private int id;

    public AddMulPlayerCommand(String name, int id){
        this.name = name;
        this.id = id;
    }

    @Override
    public void execute() {

    }
}
