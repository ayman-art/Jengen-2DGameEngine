package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.mob.Mob;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.entity.spawner.Spawner;
import com.ayman.fightEnemies.entity.particle.Particle;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.level.tile.Tile;
import com.ayman.fightEnemies.util.Vector2i;

import java.util.*;


public class Level {


    protected int width, height;
    protected int[] tiles;
    protected int[] tilesInt;
    public static Level spawn = new SpawnLevel("resources\\Sheets\\level1.png");
    private List<Entity> mobs = new ArrayList<>();
    private List<Projectile> projectiles = new ArrayList<>();
    private List<Particle> particles = new ArrayList<>();

    public Level(int width, int height) {

        this.width = width;
        this.height = height;
        tilesInt = new int[width * height];
        generateLevel();
    }
    public Level(String path){

        loadLevel(path);
        generateLevel();




    }

    protected void generateLevel() {
    }

    protected void loadLevel(String path) {
        System.out.println("calling loadLevel from Level");
    }

    public void update() {
        for(int i = 0; i < mobs.size(); i++) {
            mobs.get(i).update();
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
        int width = 4; //the width of the Mob collision box
        int height = 4; //the height of the Mob collision box
//        int xOffset = -2; //the offset of the collision box from the Center of the Mob
//        int yOffset = 4; //the offset of the collision box from the Center of the Mob
        for(int c = 0; c < 4; c++) {
//            double xt = ((x + xa) + c % 2 * size/5 -5) /16; //the x coordinate of the tile the Mob is colliding with
//            double xt = ((x + xa) + c % 2 * size/5 -5) /16; //the x coordinate of the tile the Mob is colliding with
            double xt = ((x) + c % 2 * size + xOffset) /16; //the x coordinate of the tile the Mob is colliding with
            double yt = ((y) + c / 2 * size + yOffset) / 16; //the y coordinate of the tile the Mob is colliding with

            if(getTile((int)xt, (int)yt).isSolid()) solid = true;
        }
        return solid;



    }

    public Tile getTile(int x, int y) {
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

    public void add(Entity entity) {
        entity.init(this);

        if(entity instanceof Particle) {
            particles.add((Particle) entity);
        } else if(entity instanceof Projectile) {
            projectiles.add((Projectile) entity);
        } else {
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

    public Player getPlayer() {
        for(int i = 0; i < mobs.size(); i++) {
            if(mobs.get(i) instanceof Player) return (Player) mobs.get(i);
        }
        return null;
    }


    public List<Mob> getMobs(Mob mob, int radius) {
        List<Mob> result = new ArrayList<>();
        int ex = (int)mob.getX();
        int ey = (int)mob.getY();
        for(int i = 0; i < mobs.size(); i++) {
            Entity entity = mobs.get(i);
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
            new Vector2i(0, 1)    // Down
    );
    public List<Node> findPath(Vector2i start, Vector2i goal) {
        List<Node> path = new ArrayList<>();
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingDouble(node -> node.f));
        Set<Node> visited = new HashSet<>();

        Node startNode = new Node(start, null, 0, start.distanceTo(goal));
        frontier.add(startNode);

        while (!frontier.isEmpty()) {
            Node currentNode = frontier.poll();

            if (currentNode.tile.equals(goal)) {
                while (currentNode != null) {
                    path.add(currentNode);
                    currentNode = currentNode.parent;
                }
                break;
            }

            visited.add(currentNode);

            for (Vector2i direction : DIRECTIONS) {
                Vector2i nextTile = currentNode.tile.add(direction);
                if (!getTile(nextTile.getX(), nextTile.getY()).isSolid()) {
                    Node neighbor = new Node(nextTile, currentNode, currentNode.g + 1, nextTile.distanceTo(goal));

                    if (!visited.contains(neighbor) || neighbor.g < currentNode.g) {
                        frontier.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
        }

        Collections.reverse(path);
        return path;
    }



}
