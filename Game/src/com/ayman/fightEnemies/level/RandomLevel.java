package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.Game;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.mob.Chaser;
import com.ayman.fightEnemies.level.tile.Tile;
import com.ayman.fightEnemies.util.AdjacentCheckGenerator;
import com.ayman.fightEnemies.util.DSU;
import com.ayman.fightEnemies.util.Vector2i;

import java.util.*;

import static java.lang.Thread.sleep;

public class RandomLevel extends Level {

    private volatile boolean generatingDone = false;

    private static final Random random = new Random();
    int attempts = 0;
    public volatile static DSU dsu;
    int counter = 1;
    public RandomLevel(int width, int height) {
        super(width, height);

        add(new Chaser(3,3));
    }

    protected void generateLevel() {
        {

            Thread thread1 = new Thread(() -> {
                generateLevel2();
                if(!generatingDone) {
                    generatingDone = true;
                    System.out.println("Generating Done by Thread 1");
                }

            });
            Thread thread2 = new Thread(() -> {
                generateLevel2();
                if(!generatingDone) {
                    generatingDone = true;
                    System.out.println("Generating Done by Thread 2");
                }

                generatingDone = true;
            });
            thread1.start();
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            thread2.start();

            if(true) return;
        }
        attempts++;
//        System.out.println("Random Level");

        Set<Vector2i> freeTiles = new TreeSet<>( (v1, v2) -> {
            if(v1.getX() == v2.getX()) return v1.getY() - v2.getY();
            return v1.getX() - v2.getX();
        });

        int[] SolidTilesColors = {Tile.brickColor};
        int[] nonSolidTilesColors = {Tile.grassColor};


            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {


                    if(x == 0 || y == 0 || x == width - 1 || y == height - 1) tiles[x + y * width] = Tile.brickColor;
                    else {
                        if (random.nextInt(5) == 0)
                            tiles[x + y * width] = SolidTilesColors[random.nextInt(SolidTilesColors.length)];
                        else {
                            tiles[x + y * width] = nonSolidTilesColors[random.nextInt(nonSolidTilesColors.length)];
                            freeTiles.add(new Vector2i(x, y));
                        }
                    }
                }
            }

            if(!isValidLevel(freeTiles)) {
//                System.out.println("Invalid Level, Generating new level");
                generateLevel();
            }

    }



    private boolean isValidLevel(Set<Vector2i> freeTiles) {

        Vector2i start = new Vector2i(1,1);
        if(!freeTiles.contains(start)) return false;
        Set<Vector2i> visited = new TreeSet<>( (v1, v2) -> {
            if(v1.getX() == v2.getX()) return v1.getY() - v2.getY();
            return v1.getX() - v2.getX();
        });

        Queue<Vector2i> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while(!queue.isEmpty()) {
//            System.out.println("Generating Level");
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


        if (visited.size() == freeTiles.size() - 1) {
            for(Vector2i freeTile : freeTiles) {
                if(!visited.contains(freeTile)) System.out.println(freeTile);
                else tiles[freeTile.getX() + freeTile.getY() * width] = Tile.skyColor;
            }

            System.out.println("Attempts: " + attempts);
            return true;
        }
        return false;
    }



    protected void generateLevel2() {
        DSU dsu = new DSU(width * height);
        int[] tiles = new int[width * height];



        for(int x = 0; x < width; x++) {
            int y1 = 0, y2 = height - 1;
            tiles[x + y1 * width] = Tile.brickColor;
            tiles[x + y2 * width] = Tile.brickColor;

            dsu.union(x + y1 * width, 0);
            dsu.union(x + y2 * width, 0);

        }

        for(int y = 0; y < height; y++) {
            int x1 = 0, x2 = width - 1;
            tiles[x1 + y * width] = Tile.brickColor;
            tiles[x2 + y * width] = Tile.brickColor;

            dsu.union(x1 + y * width, 0);
            dsu.union(x2 + y * width, 0);
        }
        int _i = 0;
        for( _i = 0; _i < width * height && !generatingDone; _i++) {
            int x = 1 + random.nextInt(width - 2);
            int y = 1 + random.nextInt(height - 2);



            if(tiles[x + y * width] == Tile.brickColor) continue;

            boolean ok = true;

            for(int i : new int[] {-1, 1}) {
                for(int j : new int[] {-1, 1}) {
                    if(getTile(x + i, y + j).isSolid()) ok = false;
                }
            }
            for(Vector2i vector2i[] : AdjacentCheckGenerator.vectors) {
                Vector2i current = new Vector2i(x, y);  current = current.add(vector2i[0]);

                Vector2i adjacent = new Vector2i(x, y); adjacent = adjacent.add(vector2i[1]);

                int p = current.getX() + current.getY() * width;
                int q = adjacent.getX() + adjacent.getY() * width;

                if(p < 0 || p >= width * height || q < 0 || q >= width * height) {
//                    System.out.println("Current: " + current + ", Adjacent: " + adjacent);
//                    System.out.println("p: " + p + ", q: " + q);
//                    System.out.println("X: " + x + ", Y: " + y);

                }
                if(dsu.connected(p, q)) {
//                    System.out.println("V1 " + current + ", V2 " + adjacent);
//                    System.out.println("p: " + p + ", q: " + q);
//                    System.out.println("rootP: " + dsu.find(p) + ", rootQ: " + dsu.find(q));
                    ok = false;
                    break;
                }
            }

            if(ok) {
                tiles[x + y * width] = Tile.rockColor;
                for(int i = -1; i <= 1; i++) {
                    for(int j = -1; j <= 1; j++) {
                        int p = (x + i) + (y + j) * width;
                        System.out.println("p: " + p + ", x: " + x + ", y: " + y + ", i: " + i + ", j: " + j);
                        if(getTile(x+i, (y+j)).isSolid()) {
                            System.out.println("cnnctng");
                            dsu.union(p, x + y * width);
                        }
                    }
                }

            }
        }

        System.out.println("i: " + _i +"from Thread: " + Thread.currentThread().getName());
        synchronized (RandomLevel.class) {
            if(RandomLevel.dsu != null)
                RandomLevel.dsu = dsu;

        }

    }


}
