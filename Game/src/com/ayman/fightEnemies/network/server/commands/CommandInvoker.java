package com.ayman.fightEnemies.network.server.commands;

/**
 * This class is responsible for invoking the command.

 */
public class CommandInvoker {

    private Command currentCommand;
    public void setCommand(Command command){
        this.currentCommand = command;
    }

    public void executeCommand() {
        this.currentCommand.execute();
    }
}