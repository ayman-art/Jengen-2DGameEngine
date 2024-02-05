package com.ayman.fightEnemies.network.server.controller;

import com.ayman.fightEnemies.network.server.GameServer;
import com.ayman.fightEnemies.network.server.commands.Command;
import com.ayman.fightEnemies.network.server.commands.CommandInvoker;
import com.ayman.fightEnemies.network.server.commands.ConnectCommand;
import com.ayman.fightEnemies.network.server.commands.DisconnectCommand;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.UUID;

/**
 *  responsible for controlling the server and handling the commands
 */
public class Controller {


    GameServer server;
    CommandInvoker invoker;


    public Controller(GameServer server){
        this.server = server;
        invoker = new CommandInvoker();
    }
    /**
     *  extract the command from the packet
     * @param packet the packet that contains the command
     * @return the command
     */
    public Command getCommand(DatagramPacket packet){


        String commandString = new String(packet.getData()).trim();
        System.out.println("Command: " + commandString);
        String commandType = commandString.substring(0, 1);

        switch (commandType){
            case "D"-> {
                String[] commandArgs = commandString.substring(1).split(" ");
                System.out.println(commandArgs[0]+'b');
                UUID clientID = UUID.fromString(commandArgs[0]);
                System.out.println("clientID: " + clientID);
                return new DisconnectCommand(server, clientID);
            } case "C" -> {
                String[] commandArgs = commandString.substring(1).split(" ");
                String clientName = commandArgs[0];
                InetAddress ip = packet.getAddress();
                int port = packet.getPort();
                System.out.println("IHOPE" + port);
                return new ConnectCommand(server, ip, port, clientName);
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
                System.out.println("Waiting for packet");
                server.getSocket().receive(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Command command = this.getCommand(packet);
            System.out.printf("Command: %s%n", command);

            if(command != null){
                invoker.setCommand(command);
                invoker.executeCommand();
            } else {
                System.out.println("Command is null");
                System.out.printf("Packet: %s%n", Arrays.toString(packet.getData()));
            }

        }
    }
}
