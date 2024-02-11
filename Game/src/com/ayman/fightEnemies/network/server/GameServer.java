package com.ayman.fightEnemies.network.server;


import com.ayman.fightEnemies.network.server.controller.Controller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.function.BooleanSupplier;

public class GameServer extends Thread {

        private final int port;
        private DatagramSocket socket;
        private final List<ServerClient> clients = new ArrayList<>();

        public final int MAX_CLIENTS = 4;

        public GameServer(int port) {
                this.port = port;
                try {
                        socket = new DatagramSocket(port);
                } catch (Exception e) {
                        e.printStackTrace();
                }
               this.start();
        }

        public void run() {
                System.out.println("Server started on port: " + port);
                while(true) {
                        byte[] data = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(data, data.length);
                        try {
                                socket.receive(packet);
                        } catch (Exception e) {
                                e.printStackTrace();
                        }

                }
        }

        public List<ServerClient> getClients() {
                return clients;
        }

        public void send(String message, ServerClient serverClient){
                try {
                        System.out.println("Sending: " + message + " to " + serverClient.getClientIp() + " " + serverClient.getClientPort());
                        socket.send(new DatagramPacket(message.getBytes(), message.length(), serverClient.getClientIp(), serverClient.getClientPort()));
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
        public synchronized void addClient(ServerClient serverClient) {
                this.clients.add(serverClient);
        }

        public DatagramSocket getSocket() {
                return socket;
        }


        public void shutdown(){
                for(ServerClient client : clients){
                        send("D" + client.getClientIp(), client);
                }
                socket.close();
        }
        public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter port number: ");
                int port = scanner.nextInt();
                GameServer server = new GameServer(port);
                Controller controller = new Controller(server);
                controller.start();
        }


    public void sendToAll(String s) {
                synchronized (clients) {
                        for (ServerClient client : clients) {
                                send(s, client);
                        }
                }
    }




        public void sendUntil(String message, ServerClient serverClient, BooleanSupplier function) {
                Thread thread = new Thread(() -> {
                        while (!function.getAsBoolean()) {
                                send(message, serverClient);
                                try {
                                        sleep(100);
                                } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                }
                        }
                });
                thread.start();
        }


        public ServerClient getClient(UUID uuid) {
                synchronized (clients) {
                        for (ServerClient client : clients) {
                                if (client.getUUID().equals(uuid)) {
                                        return client;
                                }
                        }
                }
                return null;
        }

        public ServerClient getClient(String name) {
                synchronized (clients) {
                        for(ServerClient client : clients) {
                                if(client.getName().equals(name)) {
                                        return client;
                                }
                        }
                }
                return null;
        }

}