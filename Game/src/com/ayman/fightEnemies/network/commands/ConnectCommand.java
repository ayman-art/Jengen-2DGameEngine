package com.ayman.fightEnemies.network.commands;

import com.ayman.fightEnemies.network.GameClient;
import com.ayman.fightEnemies.network.GameServer;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

public class ConnectCommand extends Command{
    static int counter = 0;

    private final GameServer server;
    private final InetAddress ip;
    private final int port;
    private final String clientName;

    public ConnectCommand(GameServer server, InetAddress ip, int port, String clientName) {
        this.server = server;
        this.ip = ip;
        this.port = port;
        this.clientName = clientName;
    }


    @Override
    public void execute() {

        if(server != null){
            System.out.println("Connecting to server");
            synchronized (server.getClients()){
                List<GameClient> clients = server.getClients();
                    GameClient client = new GameClient(ip, port, clientName);
                    System.out.println("the size of the clients is " + clients.size());
                    System.out.println("connecting" + clientName);
                    server.send("c" + UUID.randomUUID(), client);
                System.out.println("the counter is " + counter++);
            }
        }
    }
}
//20162