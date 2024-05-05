package com.ayman.jengen.level;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

class RandomLevelTest {


    Random random = new Random();
    int width;
    int height;
    @Test
    void testRandomLevel() {
        width = 40;
        height = 20;
    }

    @AfterEach
    void tearDown() {

        RandomLevel level = new RandomLevel(width, height);
        int[] tiles = level.getTiles();
        //running dfs on the level to check if it's connected
        boolean[] visited = new boolean[width * height];

        int islands = 0;
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(!level.getTile(x, y).isSolid() && !visited[x + y * width]) {
                    islands++;
                    assert islands <= 1;
                    dfs(level, x, y, width, height, visited, tiles);
                }
            }
        }

        //printing
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if (level.getTile(x, y).isSolid()) {
                    System.out.print("*");
                } else {
                    System.out.print("\u001B[32mG\u001B[0m"); // ANSI escape code for green color
                }
            }
            System.out.println();
        }
        assert true;

    }
    private void dfs(Level level, int x, int y, int width, int height, boolean[] visited, int[] tiles) {
        if (x < 0 || y < 0 || x >= width || y >= height) return;
        if (visited[x + y * width]) return;
        visited[x + y * width] = true;
        if (level.getTile(x, y).isSolid()) return;
        dfs(level, x + 1, y, width, height, visited, tiles);
        dfs(level, x - 1, y, width, height, visited, tiles);
        dfs(level, x, y + 1, width, height, visited, tiles);
        dfs(level, x, y - 1, width, height, visited, tiles);


    }

}
