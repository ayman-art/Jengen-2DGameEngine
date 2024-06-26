package com.ayman.jengen.network.client;

import com.ayman.jengen.GameController;

import java.io.IOException;
import java.net.*;
import java.util.UUID;

/**
 * Represents the client in the game with respect to the server

 */
public class GameClient extends Thread{



    private DatagramSocket socket;
    private final InetAddress ipAddress;
    private final int port;

    private final String clientName;
    private UUID id;



    private final GameController Game;



    public GameClient(InetAddress ipAddress, int port, String clientName) {
         this.port = port;
        this.clientName = clientName;
        this.ipAddress = ipAddress;
//        this.game = new GameController(clientName);
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("Forming GameClient with fields: " + ipAddress + " " + port + " " + clientName);

        Game = new GameController(clientName);


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

    public String getClientName() {
        return clientName;
    }


    public void sendData(String data){
        byte[] dataBytes = data.getBytes();
        DatagramPacket packet = new DatagramPacket(dataBytes, dataBytes.length, ipAddress, port);
        try {
            System.out.println("Sending: " + data + " to " + ipAddress + " " + port);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameController getGame() {
        return Game;
    }
}
