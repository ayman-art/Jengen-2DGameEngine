package com.ayman.jengen.network.server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerClient {
    private final String name;
    private final InetAddress clientIp;
    private final int clientPort;

    private final List<String> otherPlayers = new ArrayList<>();

    private final UUID uuid;

    public ServerClient(String name, InetAddress clientIp, int clientPort, UUID uuid) {
        this.name = name;
        this.clientIp = clientIp;
        this.clientPort = clientPort;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public int getClientPort() {
        return clientPort;
    }
    public InetAddress getClientIp() {
        return clientIp;
    }

    public UUID getUUID() {
        return this.uuid;
    }



    public int getNumberOfPlayers() {
        synchronized (otherPlayers) {
            return otherPlayers.size();
        }
    }

    public void addPlayer(String name) {
        synchronized (otherPlayers) {
            for(String player : otherPlayers) {
                if(player.equals(name)) {
                    return;
                }
            }

            otherPlayers.add(name);
        }
    }



    @Override
    public boolean equals(Object object) {
        if(!(object instanceof ServerClient client)) {
            return false;
        }
        return clientIp == client.getClientIp() && clientPort == client.getClientPort();
    }

    public boolean containsPlayer(String name) {
        synchronized (otherPlayers) {
            for(String player : otherPlayers) {
                if(player.equals(name)) {
                    return true;
                }
            }
        }
        return false;

    }
}
