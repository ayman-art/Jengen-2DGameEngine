package com.ayman.fightEnemies.network;

public class GameClient extends Thread{

        private String ipAddress;
        private int port;

        public GameClient(String ipAddress, int port) {
            this.ipAddress = ipAddress;
            this.port = port;
        }

        public void run() {

        }
}
