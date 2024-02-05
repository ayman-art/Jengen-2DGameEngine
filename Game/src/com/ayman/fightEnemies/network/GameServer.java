package com.ayman.fightEnemies.network;


import com.ayman.fightEnemies.network.commands.Command;
import com.ayman.fightEnemies.network.commands.CommandInvoker;
import com.ayman.fightEnemies.network.controller.Controller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;

public class GameServer extends Thread {

        private int port;
        private DatagramSocket socket;
        private List<GameClient> clients;

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

        public List<GameClient> getClients() {
                return clients;
        }

        public void send(String message, GameClient gameClient){
                try {
                        socket.send(new DatagramPacket(message.getBytes(), message.length(), gameClient.getIpAddress(), gameClient.getPort()));
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public DatagramSocket getSocket() {
                return socket;
        }

        public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter port number: ");
                int port = scanner.nextInt();
                GameServer server = new GameServer(port);
                Controller controller = new Controller(server);
                controller.start();
        }


}