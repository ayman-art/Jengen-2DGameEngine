package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.level.tile.Tile;
import com.ayman.fightEnemies.util.Vector2i;

import java.util.*;

public class RandomLevel extends Level {

    private static final Random random = new Random();

    public RandomLevel(int width, int height) {
        super(width, height);
        generateLevel();
    }

    protected void generateLevel() {
        System.out.println("Random Level");

        Set<Vector2i> freeTiles = new TreeSet<>( (v1, v2) -> {
            if(v1.getX() == v2.getX()) return v1.getY() - v2.getY();
            return v1.getX() - v2.getX();
        });

        int[] SolidTilesColors = {Tile.brickColor, Tile.rockColor};
        int[] nonSolidTilesColors = {Tile.waterColor, Tile.flowerColor, Tile.grassColor, Tile.woodColor};


            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {


                    if(random.nextInt(5) == 0)tiles[x + y * width] = SolidTilesColors[random.nextInt(SolidTilesColors.length)];
                    else {
                        tiles[x + y * width] = nonSolidTilesColors[random.nextInt(nonSolidTilesColors.length)];
                        freeTiles.add(new Vector2i(x, y));
                    }
                    if(x == 0 || y == 0 || x == width - 1 || y == height - 1) tiles[x + y * width] = Tile.brickColor;
                }
            }

            if(!isValidLevel(freeTiles)) {
                System.out.println("Invalid Level, Generating new level");
                generateLevel();
            }
    }



    private boolean isValidLevel(Set<Vector2i> freeTiles) {

        Vector2i start = new Vector2i(1,1);
        Set<Vector2i> visited = new TreeSet<>( (v1, v2) -> {
            if(v1.getX() == v2.getX()) return v1.getY() - v2.getY();
            return v1.getX() - v2.getX();
        });

        Queue<Vector2i> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while(!queue.isEmpty()) {
            System.out.println("Generating Level");
            Vector2i current = queue.poll();
            int x = current.getX();
            int y = current.getY();




            List<Vector2i> neighbors = new ArrayList<>();
            if(x - 1 >= 0) neighbors.add(new Vector2i(x - 1, y));
            if(x + 1 < width) neighbors.add(new Vector2i(x + 1, y));
            if(y - 1 >= 0) neighbors.add(new Vector2i(x, y - 1));
            if(y + 1 < height) neighbors.add(new Vector2i(x, y + 1));

            for(Vector2i neighbor : neighbors) {
                if(freeTiles.contains(neighbor) && !visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }


        }
        return visited.size() == freeTiles.size();
    }


}
