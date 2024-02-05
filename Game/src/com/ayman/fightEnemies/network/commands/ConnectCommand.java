package com.ayman.fightEnemies.network.commands;

import com.ayman.fightEnemies.network.GameClient;
import com.ayman.fightEnemies.network.GameServer;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

public class ConnectCommand extends Command{

    private final GameServer server;
    private InetAddress ip;
    private int port;

    public ConnectCommand(GameServer server, InetAddress ip, int port) {
        this.server = server;
        this.ip = ip;
        this.port = port;
    }


    @Override
    public void execute() {
        if(server != null){
            System.out.println("Connecting to server");
            synchronized (server.getClients()){
                List<GameClient> clients = server.getClients();
                if(clients.size() < server.MAX_CLIENTS) {
                    GameClient client = new GameClient(ip, port);
                    server.send("c" + UUID.randomUUID(), client);
                } else {
                    System.out.println("Server is full");
                }
            }
        }
    }
}