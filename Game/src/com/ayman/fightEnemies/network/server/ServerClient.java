package com.ayman.fightEnemies.network.server;

import java.net.InetAddress;
import java.util.UUID;

public class ServerClient {
    private String name;
    private final InetAddress clientIp;
    private final int clientPort;

    private UUID uuid;

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
}
