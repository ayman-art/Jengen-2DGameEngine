package com.ayman.fightEnemies.network;

import org.w3c.dom.ls.LSOutput;

import java.net.DatagramSocket;
import java.util.List;
import java.util.Scanner;

public class GameServer extends Thread {

        private int port;
        private DatagramSocket socket;
        private List<GameClient> clients;
        private Thread run, send, receive;

        public GameServer(int port) {
                this.port = port;
                try {
                        socket = new DatagramSocket(port);
                } catch (Exception e) {
                        e.printStackTrace();
                }

                run = new Thread(this, "Server");


        }

        public void run() {
                System.out.println("Server started on port: " + port);
                while(true) {
                        System.out.println("Server is running...");
                }
        }


        public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter port number: ");
                int port = scanner.nextInt();
                new GameServer(port).start();
        }

}