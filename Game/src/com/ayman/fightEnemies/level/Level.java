package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.IEntity;
import com.ayman.fightEnemies.entity.mob.*;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.DecoratedPlayer;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.FastPlayer;
import com.ayman.fightEnemies.entity.particle.Particle;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.level.snapshots.LevelSnapshot;
import com.ayman.fightEnemies.level.tile.Tile;
import com.ayman.fightEnemies.util.Vector2i;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DefaultContext;

import java.util.*;


public class Level {


    protected int width, height;
    protected int[] tiles;
    public static Level spawn = new SpawnLevel("resources\\Sheets\\level1.png");
    private List<IEntity> mobs = new ArrayList<>();
    private List<Projectile> projectiles = new ArrayList<>();
    private List<Particle> particles = new ArrayList<>();

    public Level(int width, int height) {

        this.width = width;
        this.height = height;
        tiles = new int[width * height];
        generateLevel();
    }
    public Level(String path){

        loadLevel(path);
        generateLevel();



    }

    protected void generateLevel() {
        System.out.println("calling generateLevel from Level");
    }

    protected void loadLevel(String path) {
        System.out.println("calling loadLevel from Level");
    }

    public void update() {
        for(int i = 0; i < mobs.size(); i++) {
            mobs.get(i).update();
            if(mobs.get(i) instanceof DecoratedPlayer decoratedPlayer) {
//                System.out.println(decoratedPlayer.getSpeed());
                if(decoratedPlayer.timeOut()) {
                    mobs.set(i, decoratedPlayer.restorePlayer());
                    System.out.println("restored");
                }
            }
        }
        for(int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
        }
        for(int i = 0; i < particles.size(); i++) {
            particles.get(i).update();
        }

        clean();
    }

    private void clean() {
        for(int i = 0; i < mobs.size(); i++) {
            if(mobs.get(i).isRemoved()) mobs.remove(i);
        }
        for(int i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).isRemoved()) projectiles.remove(i);
        }
        for(int i = 0; i < particles.size(); i++) {
            if(particles.get(i).isRemoved()) particles.remove(i);
        }
    }

    protected void time() {

    }

    public void render(int xScroll, int yScroll, Screen screen) {

        screen.setOffset(xScroll, yScroll);
        int x0 = xScroll >> 4;
        int x1 = (xScroll + screen.width +  16) >> 4; //rendering another tile to the right of the screen
        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.height + 16) >> 4;//rendering another tile to the bottom of the screen

        for(int y = y0; y < y1; y++) {
            for(int x = x0; x < x1; x++) {

                getTile(x, y).render(x, y, screen);
            }
        }

        for(int i = 0; i < mobs.size(); i++) {
            mobs.get(i).render(screen);
            if(mobs.get(i) instanceof Chaser) {
                Chaser chaser = (Chaser) mobs.get(i);
                chaser.renderPath(screen);
            }
            if(mobs.get(i) instanceof IMob mob) {
                mob.renderHealth(screen);
            }
        }
        for(int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(screen);
        }
        for(int i = 0; i < particles.size(); i++) {
            particles.get(i).render(screen);
        }


    }

    public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) { // universal method for tile collision

        boolean solid = false;
        int width = 4; //the width of the IMob collision box
        int height = 4; //the height of the IMob collision box
//        int xOffset = -2; //the offset of the collision box from the Center of the IMob
//        int yOffset = 4; //the offset of the collision box from the Center of the IMob
        for(int c = 0; c < 4; c++) {
//            double xt = ((x + xa) + c % 2 * size/5 -5) /16; //the x coordinate of the tile the IMob is colliding with
//            double xt = ((x + xa) + c % 2 * size/5 -5) /16; //the x coordinate of the tile the IMob is colliding with
            double xt = ((x) + c % 2 * size + xOffset) /16; //the x coordinate of the tile the IMob is colliding with
            double yt = ((y) + c / 2 * size + yOffset) / 16; //the y coordinate of the tile the IMob is colliding with

            if(getTile((int)xt, (int)yt).isSolid()) solid = true;
        }
        return solid;
    }

    public Tile getTile(int x, int y) {
        return this.getTile(x, y, this.tiles);
    }
    public Tile getTile(int x, int y, int[] tiles) {
        if(x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;

       // System.out.println(tiles[x + y * width]);


        if(tiles[x + y * width] == Tile.grassColor) return Tile.grass;
        if(tiles[x + y * width] == Tile.brickColor) return Tile.brick;
        if(tiles[x + y * width] == Tile.rockColor) return Tile.rock;
        if(tiles[x + y * width] == Tile.flowerColor) return Tile.flower;
        if(tiles[x + y * width] == Tile.skyColor) return Tile.sky;
        if(tiles[x + y * width] == Tile.birdColor) return Tile.bird;
        if(tiles[x + y * width] == Tile.waterColor) return Tile.water;
        if(tiles[x + y * width] == Tile.woodColor) return Tile.wood;




        return Tile.voidTile;
    }

    public void add(IEntity entity) {
        entity.init(this);

        if(entity instanceof Particle) {
            particles.add((Particle) entity);
        } else if(entity instanceof Projectile) {
            projectiles.add((Projectile) entity);
        } else if(entity instanceof IPlayer) {
            mobs.add(0,entity);
        }
        else  if(entity instanceof IMob) {
            mobs.add(entity);
        }
    }

    public void removeProjectile(Projectile projectile) {
        this.projectiles.remove(projectile);
    }


    public void addParticle(Particle p) {
        this.particles.add(p);
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }

    public synchronized IPlayer getPlayer() {
        for(int i = 0; i < mobs.size(); i++) {
            if(mobs.get(i) instanceof IPlayer player) return player;
        }
        return null;
    }
    public synchronized IPlayer getPlayer(String name) {
        for(int i = 0; i < mobs.size(); i++) {
            if(mobs.get(i) instanceof IPlayer player) {
                if(player.getName().equals(name)) return player;
            }
        }
        return null;
    }






    public List<IMob> getMobs(IMob mob, int radius) {
        List<IMob> result = new ArrayList<>();
        int ex = (int)mob.getX();
        int ey = (int)mob.getY();
        for(int i = 0; i < mobs.size(); i++) {
            IEntity entity = mobs.get(i);
            int x = (int)mob.getX();
            int y = (int)mob.getY();
            int dx = Math.abs(x - ex);
            int dy = Math.abs(y - ey);
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            if(distance <= radius) result.add(mob);
        }
        return result;
    }


    private static final List<Vector2i> DIRECTIONS = Arrays.asList(
            new Vector2i(-1, 0),  // Left
            new Vector2i(1, 0),   // Right
            new Vector2i(0, -1),  // Up
            new Vector2i(0, 1),    // Down
            new Vector2i(1, 1),
            new Vector2i(-1, 1),
            new Vector2i(1, -1),
            new Vector2i(-1, -1)

    );
    public List<Node> findPath(Vector2i start, Vector2i goal) {
        int counter = 0;

        List<Node> path = new ArrayList<>();
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
        Set<Vector2i> visited = new TreeSet<>(Comparator.comparing(Vector2i::getX).thenComparing(Vector2i::getY));
        Map<Vector2i, Integer> costSoFar = new TreeMap<>(Comparator.comparing(Vector2i::getX).thenComparing(Vector2i::getY));
        //init
        Node startNode = new Node(start, null, 0, start.distanceTo(goal));
        frontier.add(startNode);
        costSoFar.put(startNode.tileCoordinate, 0);

        while(!frontier.isEmpty()) {
            counter++;
            if(frontier.size() > 10000) System.out.println("Wrong work with queue");
            if(visited.size() > 1000) {
//                System.out.println("Wrong work with visiting");
            }


            Node current = frontier.poll();
            boolean bad = false;
//            if(visited.contains(current.tileCoordinate)) System.out.println("what");
//            for(var v: visited) {
//                counter++;
//                if(v.getX() == current.getTileCoordinate().getX() && v.getY() == current.getTileCoordinate().getY()) {
////                    System.out.println("no");
//                    bad = true;
//                    break;
//                }
//            }
            if(bad) continue;
            visited.add(current.tileCoordinate);


            if(current.getTileCoordinate().equals(goal)) {
                while(current.parent != null) {

                    path.add(current);
                    current = current.parent;
                }
                break;
            }

            for(Vector2i direction : DIRECTIONS) {

                Vector2i next = current.tileCoordinate.add(direction);
                if(getTile(next.getX(), next.getY()).isSolid()) continue;
                if(direction.getX()!=0 && direction.getY()!=0) {
                    if(getTile(current.getTileCoordinate().getX()+direction.getX(),
                            current.getTileCoordinate().getY()).isSolid())
                        continue;
                    if(getTile(current.getTileCoordinate().getX(), current.getTileCoordinate().getY()+direction.getY()).isSolid())
                        continue;
                }
                int newCost = costSoFar.get(current.tileCoordinate) + 1;
                Node nextNode = new Node(next, current, newCost, next.distanceTo(goal));
                if(!visited.contains(nextNode.tileCoordinate) && (costSoFar.get(nextNode.tileCoordinate) == null || newCost < costSoFar.get(nextNode.tileCoordinate)) ){
                    costSoFar.put(nextNode.tileCoordinate, newCost);
                    frontier.add(nextNode);
                }
            }
        }



        Collections.reverse(path);
//        System.out.println("counter" + counter);
        return path;
    }
    public Set<Vector2i> findVis(Vector2i start, Vector2i goal) {

        List<Node> path = new ArrayList<>();
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
        Set<Vector2i> visited = new TreeSet<>(Comparator.comparing(Vector2i::getX).thenComparing(Vector2i::getY));
        Map<Vector2i, Integer> costSoFar = new TreeMap<>(Comparator.comparing(Vector2i::getX).thenComparing(Vector2i::getY));

        //init
        Node startNode = new Node(start, null, 0, start.distanceTo(goal));
        frontier.add(startNode);
        costSoFar.put(startNode.tileCoordinate, 0);

        while(!frontier.isEmpty()) {
            if(frontier.size() > 10000) System.out.println("Wrong work with queue");
            if(visited.size() > 4100) {
                System.out.println("Wrong work with visiting");
            }


            Node current = frontier.poll();
            boolean bad = false;
            for(var v: visited) {
                assert current != null;
                if(v.getX() == current.getTileCoordinate().getX() && v.getY() == current.getTileCoordinate().getY()) {

                    bad = true;
                    break;
                }
            }
            if(bad) continue;
            assert current != null;
            visited.add(current.tileCoordinate);


            if(current.getTileCoordinate().equals(goal)) {
                while(current.parent != null) {
                    path.add(current);
                    current = current.parent;
                }
                break;
            }

            for(Vector2i direction : DIRECTIONS) {
                Vector2i next = current.tileCoordinate.add(direction);
                if(getTile(next.getX(), next.getY()).isSolid()) continue;
                if(direction.getX()!=0 && direction.getY()!=0) {
                    if(getTile(current.getTileCoordinate().getX()+direction.getX(),
                            current.getTileCoordinate().getY()).isSolid())
                        continue;
                    if(getTile(current.getTileCoordinate().getX(), current.getTileCoordinate().getY()+direction.getY()).isSolid())
                        continue;
                }
                int newCost = costSoFar.get(current.tileCoordinate) + 1;
                Node nextNode = new Node(next, current, newCost, next.distanceTo(goal)*10);
                if(!visited.contains(nextNode.tileCoordinate) && (costSoFar.get(nextNode.tileCoordinate) == null || newCost < costSoFar.get(nextNode.tileCoordinate)) ){
                    costSoFar.put(nextNode.tileCoordinate, newCost);
                    frontier.add(nextNode);
                }
            }
        }



        Collections.reverse(path);
        return visited;
    }




    public List<IMob> getMobs() {
        List<IMob> result = new ArrayList<>();
        for (IEntity mob : mobs) {
            if (mob instanceof IMob toMob) result.add(toMob);
        }
        return result;
    }


    public void removeMob(IMob mob) {
        this.mobs.remove(mob);
    }

    public LevelSnapshot takeSnapshot() {
        return new LevelSnapshot(tiles, mobs, projectiles, particles);
    }

    public void restoreSnapshot(LevelSnapshot snapshot) {
        tiles = snapshot.tiles();
        mobs = snapshot.mobs();
        projectiles = snapshot.projectiles();
        particles = snapshot.particles();
    }

    public int[] getMap() {
        return tiles;
    }

    public void renderMiniMap(Screen screen, int x, int y) {


        screen.renderSprite(0, 0, new Sprite(tiles, width, height), true, 100);
        for(int i2 = 0; i2 < mobs.size(); i2++) {
            screen.renderPixel(mobs.get(i2).getX() / 16, mobs.get(i2).getY() / 16, 0xff0000, 4, true, 100);
            if(mobs.get(i2) instanceof Player player) {
                screen.renderPixel(player.getX() / 16, player.getY() / 16, 0x00ff00, 4, true, 100);
            }
        }

    }

    public void removeTile(int xt, int yt) {
        int x = xt / 16;
        int y = yt / 16;
        this.tiles[xt + yt * width] = Tile.grassColor;
    }
}
