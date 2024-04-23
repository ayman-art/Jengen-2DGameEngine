package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.GameController;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.IEntity;
import com.ayman.fightEnemies.entity.mob.*;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.DecoratedPlayer;
import com.ayman.fightEnemies.entity.particle.Particle;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.level.effects.CoinEffect;
import com.ayman.fightEnemies.level.effects.Effect;
import com.ayman.fightEnemies.level.snapshots.LevelSnapshot;
import com.ayman.fightEnemies.level.tile.Tile;
import com.ayman.fightEnemies.level.winning.ItemsCollected;
import com.ayman.fightEnemies.level.winning.WinningState;
import com.ayman.fightEnemies.util.Vector2i;

import java.util.*;


public abstract class Level {


    protected int width, height;
    protected int[] tiles;
    //    public static Level spawn = new SpawnLevel("resources\\Sheets\\level1.png");
    protected List<IEntity> mobs = new ArrayList<>();
    protected List<Projectile> projectiles = new ArrayList<>();
    protected List<Particle> particles = new ArrayList<>();

    protected Map<Vector2i, Effect> effects = new TreeMap<>(Comparator.comparing(Vector2i::getX).thenComparing(Vector2i::getY));


    protected int numberOfCoins = 0; // keep track of coins inside the Level
    protected int numberOfEnemies = 0; // keep track of number of alive enemies inside the Level
    protected boolean winningEffectDone = false;

    public static WinningState winningState;

    public Level(int width, int height, WinningState winningState) {

        this.width = width;
        this.height = height;
        tiles = new int[width * height];
        generateLevel();

        Level.winningState = winningState;
    }
    public Level(){};

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
//                System.out.println("TYPE: " + decoratedPlayer.getClass());
//                System.out.println(decoratedPlayer.getSpeed());
                if(decoratedPlayer.timeOut()) {
                    System.out.println("restored from " + decoratedPlayer.getClass());
                    mobs.set(i, decoratedPlayer.restorePlayer());
                }
            }
        }
        for (Projectile projectile : projectiles) {
            projectile.update();
        }
        for (Particle particle : particles) {
            particle.update();
        }
        for(Effect effect : effects.values()) {
            effect.update();
        }

        clean();
    }

    private void clean() {
        for(int i = 0; i < mobs.size(); i++) {
            if(mobs.get(i).isRemoved()) {
                if(mobs.get(i) instanceof Dummy || mobs.get(i) instanceof Chaser)
                    numberOfEnemies--;
                mobs.remove(i--);
            }
        }
        for(int i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).isRemoved()) projectiles.remove(i--);
        }
        for(int i = 0; i < particles.size(); i++) {
            if(particles.get(i).isRemoved()) particles.remove(i--);
        }

        Map<Vector2i, Effect> toRemove = new HashMap<>();
        for(Effect effect : effects.values()) {
            if(effect.isRemoved()) {
                if(effect instanceof CoinEffect)
                    numberOfCoins--;
                toRemove.put(effect.getPosition(), effect);
            }
        }
        for(Vector2i position : toRemove.keySet()) {
            effects.remove(position);
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
            if(mobs.get(i) instanceof Chaser chaser) {
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

        for(Effect effect : effects.values()) {
            effect.render(screen);
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
    public synchronized Tile getTile(int x, int y, int[] tiles) {
        if(x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;

        // System.out.println(custumed[x + y * width]);


        if(tiles[x + y * width] == Tile.grassColor) return Tile.grass;
        if(tiles[x + y * width] == Tile.brickColor) return Tile.brick;
        if(tiles[x + y * width] == Tile.rockColor) return Tile.rock;
        if(tiles[x + y * width] == Tile.flowerColor) return Tile.flower;
        if(tiles[x + y * width] == Tile.skyColor) return Tile.sky;
        if(tiles[x + y * width] == Tile.birdColor) return Tile.bird;
        if(tiles[x + y * width] == Tile.waterColor) return Tile.water;
        if(tiles[x + y * width] == Tile.woodColor) return Tile.wood;

        if(Tile.costumedTiles.containsKey(tiles[x + y * width])) {
            return Tile.costumedTiles.get(tiles[x + y * width]);
        }




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
            if(entity instanceof Dummy || entity instanceof Chaser)
                numberOfEnemies++;
        } else if(entity instanceof Effect effect) {


            Effect oldEffect = effects.get(effect.getPosition());
            if(oldEffect instanceof CoinEffect) {
                numberOfCoins--;
            } else if(effect instanceof CoinEffect) {
                numberOfCoins++;
            }
            effects.put(effect.getPosition(), effect);
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
        for(int i = mobs.size() - 1; i >= 0; i--) {
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
        Set<Vector2i> visited = new TreeSet<>(Comparator.comparing(Vector2i::getX).thenComparing(Vector2i::getY));
        Map<Vector2i, Integer> costSoFar = new TreeMap<>(Comparator.comparing(Vector2i::getX).thenComparing(Vector2i::getY));

        PriorityQueue<Node> frontier = switch (GameController.aiType) {
            case AStar -> new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
            case Dijkstra -> new PriorityQueue<>(Comparator.comparingDouble(Node::getG));
            case DStar -> new PriorityQueue<>(Comparator.comparingDouble(Node::getH));
        };
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



    public LevelSnapshot takeSnapshot() {
        return new LevelSnapshot(tiles, mobs, projectiles, particles, effects);
    }

    public void restoreSnapshot(LevelSnapshot snapshot) {
        tiles = snapshot.tiles();
        mobs = snapshot.mobs();
        projectiles = snapshot.projectiles();
        particles = snapshot.particles();
        effects = snapshot.effects();
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

//    public void addEffect(Effect effect) {
//        this.effects.put(effect.getPosition(), effect);
//    }

    public void removeEffect(Effect effect) {
        this.effects.remove(effect);
    }

    public boolean hasEffect(int xt, int yt) {
        if(xt == 2 && yt == 2) {
            int as = 43;
        }
        return effects.containsKey(new Vector2i(xt, yt));
    }

    public Effect getEffect(int i, int i1) {
        return effects.get(new Vector2i(i, i1));
    }


    public void setPlayer(int i, IPlayer iPlayer) {
        mobs.set(i, iPlayer);
    }
    public int getPlayerIndex(Player player) {
        for(int i = 0; i < mobs.size(); i++) {
            if(!(mobs.get(i) instanceof IPlayer p))continue;
            while(p instanceof DecoratedPlayer decoratedPlayer) {
                p = decoratedPlayer.getPlayer();
            }
            if(p == player) return i;

        }
        return -1;
    }

    public IPlayer getPlayer(int i) {
        return (IPlayer) mobs.get(i);
    }

    public List<Effect> getEffects() {
        return (List<Effect>) effects.values();
    }

    public int getNumberOfCoins() {
        return numberOfCoins;
    }

    public int getNumberOfEnemies() {
        return numberOfEnemies;
    }

    public boolean isWinningEffectVisited() {
        return winningEffectDone;
    }
    public void setWinningEffectVisited(boolean winningEffectDone) {
        this.winningEffectDone = winningEffectDone;
    }

    public boolean playerWon() {
        return winningState.checkWinningState(this);
    }

    public abstract Level getNextLevel();
}
