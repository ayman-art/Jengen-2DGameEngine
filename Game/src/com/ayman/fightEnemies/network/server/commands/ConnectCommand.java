package com.ayman.fightEnemies.network.server.commands;

import com.ayman.fightEnemies.network.server.GameServer;
import com.ayman.fightEnemies.network.server.ServerClient;

import java.net.InetAddress;
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
                ServerClient client = new ServerClient(clientName, clientIp, clientPort, UUID.randomUUID());

                if(server.getClients().contains(client)){
                    System.out.println("Client already exists");
                    client = server.getClients().get(server.getClients().indexOf(client));
                } else {
                    server.addClient(client);
//                System.out.println("the size of the clients is " + clients.size());
                }
                System.out.println("connecting" + clientName);
                server.send("I" + client.getUUID(), client);
                System.out.println("Now the size of the clients is " + server.getClients().size());
                ServerClient finalClient = client;
                for(ServerClient c : server.getClients()){
                    if(c.getUUID() != client.getUUID()){
                        server.sendUntil("A" + clientName, c, () -> c.containsPlayer(clientName));
                    }
                }
            }
        }
    }
}
//20162