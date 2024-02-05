package com.ayman.fightEnemies.network.server.commands;

import com.ayman.fightEnemies.network.server.GameServer;
import com.ayman.fightEnemies.network.server.ServerClient;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

public class ConnectCommand extends Command{
    static int counter = 0;

    private final GameServer server;
    private final InetAddress clientIp;
    private final int clientPort;
    private final String clientName;

    public ConnectCommand(GameServer server, InetAddress clientIp, int clientPort, String clientName) {
        this.server = server;
        this.clientIp = clientIp;
        this.clientPort = clientPort;
        this.clientName = clientName;
    }


    @Override
    public void execute() {

        if(server != null){
            System.out.println("Connecting to server");
            synchronized (server.getClients()){
                List<ServerClient> clients = server.getClients();
                    ServerClient client = new ServerClient(clientName, clientIp, clientPort, UUID.randomUUID());
                    System.out.println("the size of the clients is " + clients.size());
                    System.out.println("connecting" + clientName);
                while(true){
                    server.send("C" + client.getUUID(), client);
                    System.out.println(client.getClientIp() + " + " + client.getClientPort());
                }
//                System.out.println("the counter is " + counter++);
            }
        }
    }
}
//20162