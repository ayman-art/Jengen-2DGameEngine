package com.ayman.fightEnemies.network.client.controller;

import com.ayman.fightEnemies.network.client.GameClient;
import com.ayman.fightEnemies.network.client.commands.AddMPlayerCommand;
import com.ayman.fightEnemies.network.client.commands.ClientCommand;
import com.ayman.fightEnemies.network.client.commands.ClientCommandInvoker;
import com.ayman.fightEnemies.network.client.commands.SetIdCommand;

import java.net.DatagramPacket;
import java.util.UUID;


/**
 *  responsible for controlling the client and handling the commands
 */
public class ClientController extends Thread {

    private final GameClient gameClient;
    private final ClientCommandInvoker commandInvoker;

    public ClientController(GameClient gameClient){
        this.gameClient = gameClient;
        this.commandInvoker = new ClientCommandInvoker();
    }

    public ClientCommand getClientCommand(DatagramPacket packet) {
        String commandString = new String(packet.getData()).trim();
        String commandType = commandString.substring(0, 1);
        switch (commandType) {
            case "I" -> {
                String[] commandArgs = commandString.substring(1).split(" ");
                String id = commandArgs[0];
                System.out.println("ID: " + id);
                return new SetIdCommand(gameClient, UUID.fromString(id));
            }
            case "A" -> {
                String[] commandArgs = commandString.substring(1).split(" ");
//                String clientName = commandArgs[0];
//                return new AddMPlayerCommand(
                return null;
            }
            default -> {
                return null;
            }
        }
    }


    public void run() {
        System.out.println("trying to connect");
        connectToServer();
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                gameClient.getSocket().receive(packet);

                commandInvoker.setCommand(getClientCommand(packet));
                commandInvoker.executeClientCommand();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void connectToServer() {
        Thread connectThread = new Thread(() -> {
            System.out.println(gameClient.getUUID() + "uuid");
            while (gameClient.getUUID() == null) {
                gameClient.sendData("C" + gameClient.getName());
            }
            System.out.println("Connected to server" + gameClient.getUUID());
        });
        connectThread.start();
    }



}
