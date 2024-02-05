package com.ayman.fightEnemies.network.client.commands;

public class ClientCommandInvoker {

    private ClientCommand currentCommand;
    public void setCommand(ClientCommand command){
        this.currentCommand = command;
    }

    public void executeClientCommand() {
        this.currentCommand.execute();
    }
}
