package com.ayman.fightEnemies.network;

import com.ayman.fightEnemies.Game;

import java.io.IOException;
import java.net.*;
import java.util.UUID;

public class GameClient extends Thread{



    private DatagramSocket socket;
    private InetAddress ipAddress;
    private int port;

    private UUID id;



    private final Game game;



    public GameClient(InetAddress ipAddress, int port, Game game) {
        this.port = port;
        this.game = game;

        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(String.valueOf(ipAddress));
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }


    public void run() {

        while(true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }


        

    }

    private void parsePacket(byte[] data, InetAddress address, int port) {

    }

    public UUID getUUID() {
        return id;
    }
    public void setUUID(UUID id) {
        this.id = id;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }
    public int getPort() {
        return port;
    }


}