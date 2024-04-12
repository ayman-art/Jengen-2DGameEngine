package com.ayman.fightEnemies;


import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.Input.Mouse;
import com.ayman.fightEnemies.entity.mob.Chaser;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.BreakTilesDecorator;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.FastPlayer;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.HelperFighterDecorator;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.InvisibilityDecorator;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.gui.AppFrame;
import com.ayman.fightEnemies.level.*;
import com.ayman.fightEnemies.level.effects.CoinEffect;
import com.ayman.fightEnemies.level.effects.Effect;
import com.ayman.fightEnemies.level.effects.HealthEffect;
import com.ayman.fightEnemies.level.effects.decorationEffects.BreakTilesEffect;
import com.ayman.fightEnemies.level.effects.decorationEffects.HelperFighterEffect;
import com.ayman.fightEnemies.level.effects.decorationEffects.InvisibilityEffect;
import com.ayman.fightEnemies.level.effects.decorationEffects.SpeedEffect;
import com.ayman.fightEnemies.level.snapshots.InputCareTaker;
import com.ayman.fightEnemies.level.snapshots.InputSnapshot;
import com.ayman.fightEnemies.level.snapshots.LevelCareTaker;
import com.ayman.fightEnemies.network.client.controller.ClientController;
import com.ayman.fightEnemies.util.Vector2i;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable{




    public static final int width = 300;
    public static final int height = width / 12 * 8;
    public static final int scaleFactor = 3;
    private  boolean playingRecording = false;
    private boolean running = false;


    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();



    private Screen screen;
    public Keyboard keyboard;
    public long lastTime = 0;



    private Thread thread;
    public JFrame jFrame;
    public JButton pauseButton;
    public JButton mainMenuButton;
    public JButton showRecordingButton;


    public final Level level;


    public static boolean paused = false;



    final String playerName;

    private LevelCareTaker levelCareTaker = new LevelCareTaker();
    private InputCareTaker inputCareTaker = new InputCareTaker();
    private Mouse mouse;

    public Game(String playerName, JFrame jFrame) {
        Projectile.init();
        this.playerName = playerName;

        Dimension size = new Dimension(width * scaleFactor, height * scaleFactor);
        setPreferredSize(size);


        screen = new Screen(width, height);
        keyboard = new Keyboard();
        mouse = new Mouse();
        addKeyListener(keyboard);

        addMouseListener(mouse); // for mouse pressed and released
        addMouseMotionListener(mouse); // for mouse moved and dragged

        this.jFrame = jFrame;
        jFrame.setLayout(new BorderLayout());

//        level = new RandomLevel(64, 64);
//        level = new RandomLevel(64, 64);
        level = new SpawnLevel("C:\\Projects\\JavaGames\\Fight-Enemies\\level11.txt");
        ((SpawnLevel) level).writeToFile("level11.txt");
        TileCoordinate playerSpawn = new TileCoordinate(level.getWidth()-2, level.getHeight()-2);
        level.add(new Player(playerName, playerSpawn.x(), playerSpawn.y(), keyboard, mouse));
        if(!ClientController.isOn()){
            IPlayer player = new InvisibilityDecorator((new Player(playerName, playerSpawn.x(), playerSpawn.y(), keyboard, mouse)));
            player = new BreakTilesDecorator(player);
//        level.add(new HelperFighterDecorator(player));
            level.add(new Chaser(1, 1));
            level.addEffect(new CoinEffect(new Vector2i(2, 2)));
            level.addEffect(new SpeedEffect(new Vector2i(level.getWidth()-4, level.getHeight()-4)));
            level.addEffect(new SpeedEffect(new Vector2i(level.getWidth() - 3, level.getHeight() - 3)));
            level.addEffect(new HealthEffect(new Vector2i(5, 5), 10));
            level.addEffect(new BreakTilesEffect(new Vector2i(4, 4)));
            level.addEffect(new InvisibilityEffect(new Vector2i(level.getWidth()-5, level.getHeight()-5)));


        }

        Game game = this;
        game.jFrame.setResizable(false);
        game.jFrame.setTitle("FightEnemies");
        game.jFrame.add(game);
        showRecordingButton = new JButton("Play Recording");
        pauseButton = new JButton("Pause");
        mainMenuButton = new JButton("Main Menu");

        pauseButton.addActionListener(e -> {
            paused = !paused;
            if(paused) {
                pauseButton.setText("Resume");
            } else {
                game.requestFocus();
                keyboard.releaseAll();
                pauseButton.setText("Pause");
            }
        });
        mainMenuButton.addActionListener(e -> {
            //relaunch the game
            try {
                playingRecording = false;
                stop();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            AppFrame.getInstance().setGuiState(new com.ayman.fightEnemies.gui.states.MainMenuState());

        });


    showRecordingButton.addActionListener(e -> {
        playingRecording = !playingRecording;

        if(playingRecording) {
            showRecordingButton.setText("Stop Playing");
            mouse.responsive = false;
            keyboard.responsive = false;
            synchronized (level) {
                level.restoreSnapshot(levelCareTaker.getNextSnapshot());
            }

        } else {
            showRecordingButton.setText("Play Recording");
//            level.restoreSnapshot(levelCareTaker.getLastSnapshot());
//            levelCareTaker.reset();
            paused = false;
            level.restoreSnapshot(levelCareTaker.getLastSnapshot());
            levelCareTaker.reset();
            inputCareTaker.reset();

            keyboard.releaseAll();
            game.requestFocus(); //request focus for the game

            mouse.responsive = true;
            keyboard.responsive = true;
        }
    });




        JPanel jPanel = new JPanel();
        jPanel.add(showRecordingButton);
        jPanel.add(pauseButton);
        jPanel.add(mainMenuButton);
        game.jFrame.add(jPanel, BorderLayout.SOUTH);
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

        lastTime = System.nanoTime();
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

                if(!playingRecording && !paused) {
                    levelCareTaker.addSnapshot(level.takeSnapshot());
                    inputCareTaker.addSnapshot(new InputSnapshot(mouse.takeSnapshot(), keyboard.takeSnapshot()));
                } else {
                    playRecording();
                }
                counter++;
                update();
                updates++;
                delta--;


            }

            //render limit without limit
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {     // Update the title every second
                timer += 1000;


                jFrame.setTitle("FightEnemies | " + updates + " ups, " + frames + " fps - " + playerName + " | " + level.getPlayer().getCoins() + " coins");


                //reset the updates and frames
                updates = 0;
                frames = 0;
            }
        }
        thread.interrupt();
    }

    private void playRecording() {
        if(paused) return;


        if(inputCareTaker.hasNext()) {
InputSnapshot inputSnapshot = inputCareTaker.getNextSnapshot();
            mouse.restoreSnapshot(inputSnapshot.mouseSnapshot);
            keyboard.restoreSnapshot(inputSnapshot.keyboardSnapshot);
//                        System.out.println("restoring");
        } else {


            showRecordingButton.setText("Play Recording");
            level.restoreSnapshot(levelCareTaker.getLastSnapshot());
            levelCareTaker.reset();
            inputCareTaker.reset();
            paused = false;
            playingRecording = false;
            mouse.responsive = true;
            keyboard.responsive = true;


            keyboard.releaseAll();
            this.requestFocus(); //request focus for the game
        }
    }
    public void update() {
        if(paused) return;

        keyboard.update();
        mouse.update();
        level.update();



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


        if(time % (60*50) == 0) {
            xDelta *= -1;
        }
        if((time + 30 * 50) % (60 * 50) == 0) {
            yDelta *= -1;
        }
        level.render(xScroll + yDelta , yScroll + xDelta, screen);
//        AnimatedTile animatedTile = new AnimatedTile((new Sprite(16, 16, 0x00FF00)));
//        animatedTile.render(level.getPlayer().getX(), level.getPlayer().getY(), screen);
        level.renderMiniMap(screen, 0, 0);
        for(int i = 0; i < pixels.length; i++) {
            this.pixels[i] = screen.pixels[i]; //copy the pixels data from screen to the pixels array of the image object
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        graphics.setColor(Color.black);
        graphics.setFont(new Font("Verdana", Font.PLAIN, 50));
        graphics.drawString("X: " + player.getX()/16 + ", Y: " + player.getY()/16, 450, 450);
        graphics.drawString(mouse.getButton() + "", 80, 80);




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

//// Now draw the string with the new font size
//                if(RandomLevel.dsu != null)graphics.drawString(RandomLevel.dsu.getParent()[x + y * 64] + "", xp, yp);

// Optionally, set the font back to the original size after drawing the string
                graphics.setFont(currentFont); // Set the graphics object's font back to the original font

            }
        }
        graphics.fillRect(mouse.getX() , mouse.getY(), 8, 8);
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


