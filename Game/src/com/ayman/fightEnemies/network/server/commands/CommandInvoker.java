package com.ayman.fightEnemies.network.server.commands;

public class CommandInvoker {

    private Command currentCommand;
    public void setCommand(Command command){
        this.currentCommand = command;
    }

    public void executeCommand() {
        this.currentCommand.execute();
    }
}