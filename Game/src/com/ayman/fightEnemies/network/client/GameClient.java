package com.ayman.fightEnemies.network.client;

import com.ayman.fightEnemies.Game;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.UUID;

public class GameClient extends Thread{



    private DatagramSocket socket;
    private final InetAddress ipAddress;
    private final int port;

    private final String clientName;
    private UUID id;



    private final Game game;



    public GameClient(InetAddress ipAddress, int port, String clientName) {
         this.port = port;
        this.clientName = clientName;
        this.ipAddress = ipAddress;
        this.game = new Game(getName());
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("Forming GameClient with fields: " + ipAddress + " " + port + " " + clientName);

    }


    public void run() {

        while(true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
                System.out.println(new String(packet.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        

    }


    public  UUID getUUID() {
        synchronized (this) {
            return id;
        }
    }
    public void setUUID(UUID id) {
        synchronized (this) {
            if(this.id == null) {
                this.id = id;
                System.out.println("IDincl: " + id);
            }
        }
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }
    public int getPort() {
        return port;
    }
    public DatagramSocket getSocket() {
        return socket;
    }


    public void sendData(String data){
        byte[] dataBytes = data.getBytes();
        DatagramPacket packet = new DatagramPacket(dataBytes, dataBytes.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
