package com.ayman.jengen.network.client.controller;

import com.ayman.jengen.network.client.GameClient;
import com.ayman.jengen.network.client.commands.*;

import java.net.DatagramPacket;
import java.util.UUID;


/**
 *  responsible for controlling the client and handling the commands
 */
public class ClientController extends Thread {

    public static boolean twoPlayerMode;
    private static ClientController instance;

    private final GameClient gameClient;
    private final ClientCommandInvoker commandInvoker;

    private ClientController(GameClient gameClient){
        this.gameClient = gameClient;
        this.commandInvoker = new ClientCommandInvoker();
    }

    public synchronized static ClientController getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ClientController has not been initialized yet");
        }
        return instance;
    }

    public static synchronized boolean isOn() {
        return twoPlayerMode;
    }

    public synchronized static ClientController init(GameClient gameClient) {
        if (instance != null) {
            throw new IllegalStateException("ClientController has already been initialized");
        }
        return instance = new ClientController(gameClient);
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
                String clientName = commandArgs[0];
                return new AddMulPlayerCommand(this.gameClient, clientName);
            } case "U" -> {
                String[] commandArgs = commandString.substring(1).split(" ");
                String clientName = commandArgs[0];
                int x = Integer.parseInt(commandArgs[1]);
                int y = Integer.parseInt(commandArgs[2]);
                int health = Integer.parseInt(commandArgs[3]);
                return new UpdateMulPlayerClientCommand(this.gameClient, clientName, x, y, health);
            } case "S" -> {
                String[] commandArgs = commandString.substring(1).split(" ");
                String clientName = commandArgs[0];
                int x = Integer.parseInt(commandArgs[1]);
                int y = Integer.parseInt(commandArgs[2]);
                double angle = Double.parseDouble(commandArgs[3]);
                return new ShootclientCommand(this.gameClient, clientName, x, y, angle);
            }
            default -> {
                return null;
            }
        }
    }


    public void run() {
        listenForClose();
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

    private void listenForClose() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down");
            long lastTime = System.nanoTime();
            int attemps = 10;
            while (attemps > 0) {

                if(System.nanoTime() - lastTime < 100000000) {
                    continue;
                }
                lastTime = System.nanoTime();
                attemps--;
                System.out.println("Trying to disconnect from server" + gameClient.getUUID() + " with " + attemps + " attempts");
                gameClient.sendData("D" + gameClient.getUUID());
            }
            System.out.println("Disconnected from server" + gameClient.getUUID() + " with " + attemps + " attempts");
        }));
    }


    public void connectToServer() {
        Thread connectThread = new Thread(() -> {
            System.out.println(gameClient.getUUID() + "uuid");
            int count = 0;
            int attemps = 0;
            long now = System.nanoTime();
            long lastTime = now;
            while (gameClient.getUUID() == null) {
                now = System.nanoTime();
                long tenthOfSecond = 100000000;
                if(System.nanoTime() - lastTime < tenthOfSecond) {
                    continue;
                }
                lastTime = now;

                gameClient.sendData("C" + gameClient.getClientName());
                attemps++;

            }

            System.out.println("Connected to server" + gameClient.getUUID() + " with " + attemps + " attempts");
        });
        connectThread.start();
    }


    public void sendPlayerPosition(int x, int y, int health) {
        gameClient.sendData("U" + gameClient.getUUID() + " " + x + " " + y + " " + health);
    }

    public void sendPlayerShoot(int x, int y, double dir) {
        gameClient.sendData("S" + gameClient.getUUID() + " " + x + " " + y + " " + dir);
    }
}
