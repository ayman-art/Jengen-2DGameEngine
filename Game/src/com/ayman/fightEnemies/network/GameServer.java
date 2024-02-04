package com.ayman.fightEnemies.network;

import org.w3c.dom.ls.LSOutput;

import java.net.DatagramSocket;
import java.util.List;

public class GameServer extends Thread {

        private int port;
        private DatagramSocket socket;
        private List<GameClient> clients;
        private Thread run, send, receive;

        public GameServer() {

        }

}