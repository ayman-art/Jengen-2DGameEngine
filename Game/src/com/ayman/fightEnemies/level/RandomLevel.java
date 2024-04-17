package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.GameController;
import com.ayman.fightEnemies.entity.IEntity;
import com.ayman.fightEnemies.entity.mob.Chaser;
import com.ayman.fightEnemies.entity.mob.Dummy;
import com.ayman.fightEnemies.entity.mob.IMob;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.FastPlayer;
import com.ayman.fightEnemies.level.effects.CoinEffect;
import com.ayman.fightEnemies.level.effects.Effect;
import com.ayman.fightEnemies.level.effects.HealthEffect;
import com.ayman.fightEnemies.level.effects.decorationEffects.BreakTilesEffect;
import com.ayman.fightEnemies.level.effects.decorationEffects.HelperFighterEffect;
import com.ayman.fightEnemies.level.effects.decorationEffects.InvisibilityEffect;
import com.ayman.fightEnemies.level.effects.decorationEffects.SpeedEffect;
import com.ayman.fightEnemies.level.tile.Tile;
import com.ayman.fightEnemies.level.winning.ItemsCollected;
import com.ayman.fightEnemies.util.AdjacentCheckGenerator;
import com.ayman.fightEnemies.util.DSU;
import com.ayman.fightEnemies.util.Vector2i;

import java.util.*;

public class RandomLevel extends Level {

    private static final Random random = new Random();
    private volatile boolean done = false;

    static public int WIDTH = 64;
    static public int HEIGHT = 64;
    public DSU dsu = new DSU(width * height);
    private static int collisionFactor = 1;
    int counter = 1;

    public RandomLevel(int width, int height) {
        super(width, height, new ItemsCollected());

        add(new Chaser(3,3));
    }

    public RandomLevel() {
        super(WIDTH, HEIGHT, new ItemsCollected());
        add(new Chaser(3,3));

    }

    protected void generateLevel() {
        {
            Thread thread1 = new Thread(this::generateLevel2);
//            Thread thread2 = new Thread(thread1);

            thread1.start();
//            thread2.start();

            try {
                thread1.join();
//                thread2.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < width * height; i++) {
                if(tiles[i] == 0) {
                    tiles[i] = Tile.skyColor;
                }
            }
            if(true) return;
        }
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
                if(!visited.contains(freeTile)) ;
//                    System.out.println(freeTile);
                else tiles[freeTile.getX() + freeTile.getY() * width] = Tile.skyColor;
            }
            return true;
        }
        return false;
    }



    protected void generateLevel2() {

        int attempts = 0;
        int[] tiles = new int[width * height];
        DSU dsu = new DSU(width * height);

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

        for(int i = 1; i < width + height>>2; i++) {
            int x, y;

            if(random.nextInt(2) == 0) {
                y = 1;
            } else {
                y = height - 2;
            }

            x = 3 + random.nextInt(width - 3 -3);
            if(x == 3 && (y == 3) || x == width - 2 && y == height - 2) {
                continue;
            }
            tiles[x + width * y] = Tile.rockColor;
            for(int xx = x - 1; xx <= x + 1; xx++) {
                for(int yy = y - 1; yy <= y + 1; yy++) {
                    if(getTile(xx, yy, tiles).isSolid()) {
                        dsu.union(xx + yy * width, x + y * width);
                    }
                }
            }

            if(random.nextInt(2) == 0) {
                x = 1;
            } else {
                x = width - 2;
            }
            y = 3 + random.nextInt(height-3 -3);
            // what is the difference between the
            if(x == 3 && (y == 3) || x == width - 2 && y == height - 2) {
                continue;
            }
            tiles[x + width * y] = Tile.rockColor;
            for(int xx = x - 1; xx <= x + 1; xx++) {
                for(int yy = y - 1; yy <= y + 1; yy++) {
                    if(getTile(xx, yy, tiles).isSolid()) {
                        dsu.union(xx + yy * width, x + y * width);
                    }
                }
            }

        }

//        for(int i = 1; i < width + height + 2000000; i++) {
//            int x, y;
//            if(random.nextInt(2) == 0) {
//                x = 1;
//            } else {
//                x = width - 2;
//            }
//            y = random.nextInt(height);
//            if(getTile(x, y, tiles).isSolid()) continue;
//            tiles[x + width * y] = Tile.rockColor;
//            for(int xx = x - 1; xx <= x + 1; xx++) {
//                for(int yy = y - 1; yy <= y + 1; yy++) {
//                    if(getTile(xx, yy, tiles).isSolid()) {
//                        dsu.union(xx + yy * width, x + y * width);
//                    }
//                }
//            }
//
//        }
        int _i;
        int maxAttempts = GameController.getDifficulty() * (width-2) * (height-2) / 100;
        int collisions = 0;
        for(_i = 0; _i < maxAttempts && collisions < maxAttempts * collisionFactor && !done  ; _i++) {
            int x = 1 + random.nextInt(width - 2);
            int y = 1 + random.nextInt(height - 2);

            if(getTile(x, y, tiles).isSolid()) {
                _i--;
                collisions++;
                continue;
            }
            if(x == 3 && (y == 3) || x == width - 2 && y == height - 2) {
                _i--;
                continue;
            }

            boolean ok = true;
            for(Vector2i[] vector2i : AdjacentCheckGenerator.vectors) {
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
//                    System.out.println("Not Ok");
                    ok = false;
                    break;
                }
            }

            if(ok) {
                if (getTile(x, y, tiles).isSolid())
                    throw new AssertionError();

                tiles[x + y * width] = Tile.rockColor;
                for(int i = -1; i <= 1; i++) {
                    for(int j = -1; j <= 1; j++) {
                        int p = (x + i) + (y + j) * width;
//                        System.out.println("p: " + p + ", x: " + x + ", y: " + y + ", i: " + i + ", j: " + j);
                        if(getTile(x+i, (y+j), tiles).isSolid()) {
//                            System.out.println("cnnctng");
                            dsu.union(p, x + y * width);
                        }
                    }
                }

            } else {
                _i--;
                collisions++;
            }
        }

        System.out.println("_i: " + _i + "From thread" + Thread.currentThread().getName());

        //To ensure that only one thread can put its level.
        synchronized (RandomLevel.class) {
            if(!done) {
                done = true;
                synchronized (this) {
                    this.tiles = tiles;
                    putEffects(Math.min(getEmptySlots(),GameController.getDifficulty()));
                    putMobs(Math.min(getEmptySlots(),GameController.getDifficulty()));
                }

            }
        }


    }

    private void putMobs(int n) {
        int x = 1 + random.nextInt(width - 2),
        y = 1 + random.nextInt(height - 2);
        while(occupiedSlot(x, y)) {
            x = 1 + random.nextInt(width - 2);
            y = 1 + random.nextInt(height - 2);
        }
        // Base case
        if(n <= 1) {
//            mobs.add(new Player(x, y, null, null));
            return;
        }

        add(Objects.requireNonNull(getRandomMob(x, y)));
        putMobs(n - 1);
    }
    private void putEffects(int n) {

        int x, y;
        do {
            x = 1 + random.nextInt(width - 2);
            y = 1 + random.nextInt(height - 2);
        } while (occupiedSlot(x, y));
        if (n == 1) {
            if(numberOfCoins == 0 && Level.winningState instanceof ItemsCollected) {
                add(new CoinEffect(x, y));
            }
            return;
        }


        int num = random.nextInt(6);
        switch (num) {
            case 0 -> {
                add(new CoinEffect(x, y));
            }
            case 1 -> {
                add(new HealthEffect(x, y));
            }
            case 2 -> {
                add(new BreakTilesEffect(x, y));
            }
            case 3 -> {
                add(new SpeedEffect(x, y));
            }
            case 4 -> {
                add(new HelperFighterEffect(x, y));
            }
            case 5 -> {
                add(new InvisibilityEffect(x, y));
            }
            default -> throw new IllegalStateException("Unexpected value: " + num);
        }

        putEffects(n - 1);
    }
    private IMob getRandomMob(int x, int y) {
        int num = random.nextInt(2);
        switch(num) {
            case 0 -> {
                return new Dummy(x, y);
            }
            case 1 -> {
                return new Chaser(x, y);
            }
        }
        return null;
    }


    public boolean occupiedSlot(int x, int y) {
        if(getTile(x, y).isSolid())
            return true;
        for(IEntity entity : mobs) {
            if(entity instanceof IMob mob) {
                if(mob.getX()/16 == x && mob.getY()/16 == y)
                    return true;
            }
        }
        return false;
    }

    public int getEmptySlots() {
        int count = 0;
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(!getTile(x, y).isSolid()) {
                    count++;
                }
            }
        }
        return count;
    }

}
