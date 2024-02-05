package com.ayman.fightEnemies.network.controller;

import com.ayman.fightEnemies.network.GameServer;
import com.ayman.fightEnemies.network.commands.Command;
import com.ayman.fightEnemies.network.commands.CommandInvoker;
import com.ayman.fightEnemies.network.commands.ConnectCommand;
import com.ayman.fightEnemies.network.commands.DisconnectCommand;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Controller {


    GameServer server;
    CommandInvoker invoker;

    public Controller(GameServer server){
        this.server = server;
        invoker = new CommandInvoker();
    }
    public Command getCommand(DatagramPacket packet){


        String commandString = new String(packet.getData());
        String commandType = commandString.substring(0, 1);

        switch (commandType){
            case "D"-> {
                String[] commandArgs = commandString.substring(1).split(" ");
                UUID clientID = UUID.fromString(commandArgs[0]);
                return new DisconnectCommand(server, clientID);
            } case "C" -> {
                String[] commandArgs = commandString.substring(1).split(" ");
                InetAddress ip = packet.getAddress();
                int port = packet.getPort();
                return new ConnectCommand(server, ip, port);
            } default -> {
                return null;
            }

        }
    }


    public void start() {
        CommandInvoker invoker = new CommandInvoker();
        while(true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                server.getSocket().receive(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Command command = this.getCommand(packet);
            if(command != null){
                invoker.setCommand(command);
                invoker.executeCommand();
            }

        }
    }
}
