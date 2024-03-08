package com.ayman.fightEnemies;


import com.ayman.fightEnemies.Graphics.AnimatedTile;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.Input.Mouse;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.FastPlayer;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.HelperFighterDecorator;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.InvisibilityDecorator;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.RandomLevel;
import com.ayman.fightEnemies.level.TileCoordinate;
import com.ayman.fightEnemies.level.snapshots.LevelCareTaker;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable{




    public static final int width = 300;
    public static final int height = width / 12 * 8;
    public static final int scaleFactor = 3;


    private boolean running = false;


    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();



    private Screen screen;
    public Keyboard keyboard;



    private Thread thread;
    public JFrame jFrame;

    public Level level;


    public static boolean paused = false;



    final String playerName;

    private LevelCareTaker levelCareTaker = new LevelCareTaker();
    public Game(String playerName, JFrame jFrame) {
        Projectile.init();
        this.playerName = playerName;

        Dimension size = new Dimension(width * scaleFactor, height * scaleFactor);
        setPreferredSize(size);


        screen = new Screen(width, height);
        keyboard = new Keyboard();
        addKeyListener(keyboard);

        Mouse mouse = new Mouse();
        addMouseListener(mouse); // for mouse pressed and released
        addMouseMotionListener(mouse); // for mouse moved and dragged

        this.jFrame = jFrame;
        jFrame.setLayout(new BorderLayout());

//        level = new RandomLevel(64, 64);
        level = new RandomLevel(64, 64);

        TileCoordinate playerSpawn = new TileCoordinate(62, 62);
        IPlayer player = new InvisibilityDecorator(new FastPlayer(new Player(playerName ,playerSpawn.x(), playerSpawn.y(), keyboard)));

        level.add(new HelperFighterDecorator(player));

        Game game = this;
        game.jFrame.setResizable(false);
        game.jFrame.setTitle("FightEnemies");
        game.jFrame.add(game);
        game.jFrame.pack();
        game.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.jFrame.setLocationRelativeTo(null);
        game.jFrame.setVisible(true);


        game.requestFocus(); //request focus for the game

        game.start();

        setFocusable(true);
    }

    public Game() {
        this("Game", new JFrame());
    }


    public Game(String playerName) {
        this(playerName, new JFrame());
    }





    public synchronized void start() {

        running = true;
        thread = new Thread(this, "Game");
        thread.start();
    }

    public synchronized void stop() throws InterruptedException {

        running = false;

        //wait until the thread dies first
        thread.join();
    }
    @Override
    public void run() {

        running = true;

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        long counter = 0;
        final double ns = 1000000000.0 / 60.0; //for converting to one of sixty part of second
        double delta = 0 / ns;
        int frames = 0;
        int updates = 0;
        while(running) {
            long now = System.nanoTime();

            delta += (now - lastTime) / ns; // number of "Updates" we need to do
            lastTime = now;

            while(delta >= 1) {
                counter++;
                update();
                updates++;
                delta--;


                levelCareTaker.addSnapshot(level.takeSnapshot());
                boolean recording = false;
                if(counter % 600 == 0 && recording) {
                    int a= 4;
                    for(int i = 0; i < levelCareTaker.getNumberOfSnapshots(); i++) {
                        level.restoreSnapshot(levelCareTaker.getSnapshot(i));
                        try {
                            Thread.sleep(1000 / 60);
                            System.out.println("Playing back the recording" + i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        render();
                        lastTime = System.nanoTime(); // To avoid the huge delta time update
                    }

                    levelCareTaker.reset();
                    counter = 1;
                }
            }

            //render limit without limit
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {     // Update the title every second
                timer += 1000;


                jFrame.setTitle("FightEnemies | " + updates + " ups, " + frames + " fps - " + playerName);

                //reset the updates and frames
                updates = 0;
                frames = 0;
            }
        }
        try {
            stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        if(paused) return;

        level.update();

        keyboard.update();


    }

    int xDelta = 1, yDelta = 1;
    int time = 0;
    public void render(  ) {time+= 2;

        IPlayer player = level.getPlayer();

        BufferStrategy bufferStrategy = getBufferStrategy();
        if(bufferStrategy == null) {
            createBufferStrategy(3); //triple buffering for faster rendering
            return;
        }

        screen.clear();
        // Center the screen on the player
        int xScroll = player.getX() - screen.width / 2;
        int yScroll = player.getY() - screen.height / 2;


        if(time % (60*5) == 0) {
            xDelta *= -1;
        }
        if((time + 30 * 5) % (60 * 5) == 0) {
            yDelta *= -1;
        }
        level.render(xScroll + yDelta , yScroll + xDelta, screen);
        AnimatedTile animatedTile = new AnimatedTile((new Sprite(16, 16, 0x00FF00)));
        animatedTile.render(level.getPlayer().getX(), level.getPlayer().getY(), screen);
        level.renderMiniMap(screen, 0, 0);
        for(int i = 0; i < pixels.length; i++) {
            this.pixels[i] = screen.pixels[i]; //copy the pixels data from screen to the pixels array of the image object
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        graphics.setColor(Color.black);
        graphics.setFont(new Font("Verdana", Font.PLAIN, 50));
        graphics.drawString("X: " + player.getX()/16 + ", Y: " + player.getY()/16, 450, 450);
        graphics.drawString(Mouse.getButton() + "", 80, 80);




        graphics.drawString("Health: " , 50, 50);
        for(int x = 0; x < 64; x++) {
            for(int y = 0; y < 64; y++) {
                int xp = x * 16 * 3;
                int yp = y * 16 * 3;
                xp -= xScroll * 3;
                yp -= yScroll * 3;

                xp += 12;
                yp += 20;

// Assuming graphics is your Graphics object
                Font currentFont = graphics.getFont(); // Get the current font
                int newSize = 10; // Set the new font size
                Font newFont = currentFont.deriveFont(Font.PLAIN, newSize); // Create a new font with the desired size
                graphics.setFont(newFont); // Set the graphics object's font to the new font

// Now draw the string with the new font size
                if(RandomLevel.dsu != null)graphics.drawString(RandomLevel.dsu.getParent()[x + y * 64] + "", xp, yp);

// Optionally, set the font back to the original size after drawing the string
                graphics.setFont(currentFont); // Set the graphics object's font back to the original font

            }
        }
        graphics.fillRect(Mouse.getX() , Mouse.getY(), 8, 8);
        graphics.dispose();
        bufferStrategy.show();




    }

    public static void main(String[] args) {
        Game game = new Game("SASA", new JFrame());
    }

    public Level getLevel() {
        return level;
    }
}


