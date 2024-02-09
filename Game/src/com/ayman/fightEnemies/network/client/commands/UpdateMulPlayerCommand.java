package com.ayman.fightEnemies.network.client.commands;

public class UpdateMulPlayerCommand {
    private final String name;
    private final int x, y;

    public UpdateMulPlayerCommand(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}
