package com.ayman.fightEnemies.network;

import java.net.InetAddress;

public class ServerClient {
    private final InetAddress clientIp;
    private final int clientPort;

    public ServerClient(InetAddress clientIp, int clientPort) {
        this.clientIp = clientIp;
        this.clientPort = clientPort;
    }

    public int getClientPort() {
        return clientPort;
    }
    public InetAddress getClientIp() {
        return clientIp;
    }
}
