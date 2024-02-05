package com.ayman.fightEnemies.network.client.commands;

public class AddMPlayerCommand extends ClientCommand{

    private String name;
    private int id;

    public AddMPlayerCommand(String name, int id){
        this.name = name;
        this.id = id;
    }

    @Override
    public void execute() {
        System.out.println("Adding Multiplayer Player: " + name + " with id: " + id);
    }
}
