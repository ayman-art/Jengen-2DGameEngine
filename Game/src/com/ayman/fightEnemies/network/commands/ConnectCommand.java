package com.ayman.fightEnemies.network.commands;

import com.ayman.fightEnemies.network.GameClient;
import com.ayman.fightEnemies.network.GameServer;

import java.util.List;

public class ConnectCommand extends Command{

    private GameServer server;
    private GameClient client;


    @Override
    public void execute() {
        if(server != null){
            synchronized (server.getClients()){
                List<GameClient> clients = server.getClients();
                if(clients.size() < server.MAX_CLIENTS) {
                    clients.add(client);
                } else {
                    System.out.println("Server is full");
                }
            }
        }
    }
}