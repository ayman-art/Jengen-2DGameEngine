package com.ayman.fightEnemies.network.controller;

import com.ayman.fightEnemies.network.GameServer;
import com.ayman.fightEnemies.network.commands.Command;
import com.ayman.fightEnemies.network.commands.ConnectCommand;
import com.ayman.fightEnemies.network.commands.DisconnectCommand;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Controller {


    GameServer server;
    public Command getCommand(DatagramPacket packet){


        String commandString = new String(packet.getData());
        String commandType = commandString.substring(0, 1);

        switch (commandType){
            case "D"-> {
                String[] commandArgs = commandString.substring(1).split(" ");
                return new DisconnectCommand(server, UUID.fromString(commandArgs[0]));
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


}
