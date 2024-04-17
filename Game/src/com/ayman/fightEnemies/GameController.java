package com.ayman.fightEnemies;


import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.Input.Mouse;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.BreakTilesDecorator;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.InvisibilityDecorator;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.game.contexts.AIContext;
import com.ayman.fightEnemies.gui.AppFrame;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.RandomLevel;
import com.ayman.fightEnemies.level.TileCoordinate;
import com.ayman.fightEnemies.level.effects.CoinEffect;
import com.ayman.fightEnemies.level.effects.HealthEffect;
import com.ayman.fightEnemies.level.effects.decorationEffects.BreakTilesEffect;
import com.ayman.fightEnemies.level.effects.decorationEffects.HelperFighterEffect;
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

public class GameController extends Canvas implements Runnable{




    public static int width = 300;
    public static int height = width / 12 * 8;
    public static int scaleFactor = 3;
    private  boolean playingRecording = false;
    private boolean running = false;


    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();



    private final Screen screen;
    public Keyboard keyboard;
    public long lastTime = 0;



    private Thread thread;
    public JFrame jFrame;
    public JButton pauseButton;
    public JButton mainMenuButton;
    public JButton showRecordingButton;


    public final Level[] levels = new Level[2];
    public final Level level;


    public static boolean paused = false;





    public static String playerName ;
    public static AIContext.AIType aiType = AIContext.AIType.Dijkstra;

    private final LevelCareTaker levelCareTaker = new LevelCareTaker();
    private final InputCareTaker inputCareTaker = new InputCareTaker();
    private Mouse mouse;


    public GameController(com.ayman.fightEnemies.game.Game game) {
        this(game.getPlayerContext().getName(), new JFrame());
    }

    public GameController(String playerName, JFrame jFrame) {
        Projectile.init();
        GameController.playerName = playerName;

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
        level = new RandomLevel();
//        ((SpawnLevel) level).writeToFile("level11.txt");
        TileCoordinate playerSpawn = new TileCoordinate(level.getWidth()-2, level.getHeight()-2);
        level.add(new Player(playerName, playerSpawn.x(), playerSpawn.y(), keyboard, mouse));
        if(!ClientController.isOn()){
            IPlayer player = new InvisibilityDecorator((new Player(playerName, playerSpawn.x(), playerSpawn.y(), keyboard, mouse)));
            player = new BreakTilesDecorator(player);
//        level.add(new HelperFighterDecorator(player));
//            level.add(new Chaser(1, 1));
            level.add(new CoinEffect(new Vector2i(2, 2)));
            level.add(new SpeedEffect(new Vector2i(level.getWidth()-4, level.getHeight()-4)));
//            level.add(new SpeedEffect(new Vector2i(level.getWidth() - 3, level.getHeight() - 3)));
            level.add(new HealthEffect(new Vector2i(5, 5), 10));
            level.add(new BreakTilesEffect(new Vector2i(4, 4)));
//            level.add(new HelperFighterEffect(new Vector2i(level.getWidth()-4, level.getHeight()-5)));
//            level.add(new HelperFighterEffect(new Vector2i(level.getWidth()-4, level.getHeight()-4)));
//            level.add(new HelperFighterEffect(new Vector2i(level.getWidth()-6, level.getHeight()-5)));
            level.add(new HelperFighterEffect(new Vector2i(level.getWidth()-5, level.getHeight()-4)));



        }

        GameController Game = this;
        Game.jFrame.setResizable(false);
        Game.jFrame.setTitle("FightEnemies");
        Game.jFrame.add(Game);
        showRecordingButton = new JButton("Play Recording");
        pauseButton = new JButton("Pause");
        mainMenuButton = new JButton("Main Menu");

        pauseButton.addActionListener(e -> {
            paused = !paused;
            if(paused) {
                pauseButton.setText("Resume");
            } else {
                Game.requestFocus();
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
        if(levelCareTaker.getNumberOfSnapshots() == 0) return;
        playingRecording = !playingRecording;

        if(playingRecording) {
            showRecordingButton.setText("Stop Playing");
            mouse.responsive = false;
            keyboard.responsive = false;
            synchronized (level) {
                levelCareTaker.addSnapshot(level.takeSnapshot());
                level.restoreSnapshot(levelCareTaker.getNextSnapshot());
            }

        } else {
            handlePauseRecording();
        }
    });




        JPanel jPanel = new JPanel();
        jPanel.add(showRecordingButton);
        jPanel.add(pauseButton);
        jPanel.add(mainMenuButton);
        Game.jFrame.add(jPanel, BorderLayout.SOUTH);
        Game.jFrame.pack();
        Game.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game.jFrame.setLocationRelativeTo(null);
        Game.jFrame.setVisible(true);


        Game.requestFocus(); //request focus for the game

        Game.start();

        setFocusable(true);
    }

    public GameController() {
        this("GameController", new JFrame());
    }


    public GameController(String playerName) {
        this(playerName, new JFrame());
    }





    public synchronized void start() {

        running = true;
        thread = new Thread(this, "GameController");
        thread.start();
    }

    public synchronized void stop() throws InterruptedException {

        running = false;

        //wait until the thread dies first
        thread.join();
    }
    int recordingTimer = 0;
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
            if ((levelCareTaker.getNumberOfSnapshots() > 2)) throw new AssertionError();
            long now = System.nanoTime();

            delta += (now - lastTime) / ns; // number of "Updates" we need to do
            lastTime = now;

            while(delta >= 1) {
                if(level.playerWon()) {
                    System.out.println("You won");
                    System.out.println("Congratulations " + playerName);
                }
                if(!playingRecording && !paused) {
                    if(recordingTimer % 300 == 0) {
                        recordingTimer = 0;
                        if(levelCareTaker.getNumberOfSnapshots() < 2) {
                            synchronized (levelCareTaker) {
                                levelCareTaker.addSnapshot(level.takeSnapshot());
                            }
                        } else {
                            synchronized (levelCareTaker) {
                                levelCareTaker.removeOldSnapshot();
                                levelCareTaker.addSnapshot(level.takeSnapshot());
                                if (inputCareTaker.getNumberOfSnapshots() != 600) {
                                    System.out.println("You are not a genius the number of snapshots is " + inputCareTaker.getNumberOfSnapshots() );
                                }
                                else System.out.println("You are just a genius");
                                inputCareTaker.removeOldSnapshots(300);
                            }
                        }

                    }
                    inputCareTaker.addSnapshot(new InputSnapshot(mouse.takeSnapshot(), keyboard.takeSnapshot()));
                    recordingTimer++;
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
            recordingTimer = 0;
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


//        if(time % (60*50) == 0) {
//            xDelta *= -1;
//        }
//        if((time + 30 * 50) % (60 * 50) == 0) {
//            yDelta *= -1;
//        }


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




//        graphics.drawString("Health: " , 50, 50);
//        for(int x = 0; x < 64; x++) {
//            for(int y = 0; y < 64; y++) {
//                int xp = x * 16 * 3;
//                int yp = y * 16 * 3;
//                xp -= xScroll * 3;
//                yp -= yScroll * 3;
//
//                xp += 12;
//                yp += 20;
//
//// Assuming graphics is your Graphics object
//                Font currentFont = graphics.getFont(); // Get the current font
//                int newSize = 10; // Set the new font size
//                Font newFont = currentFont.deriveFont(Font.PLAIN, newSize); // Create a new font with the desired size
//                graphics.setFont(newFont); // Set the graphics object's font to the new font
//
////// Now draw the string with the new font size
////                if(RandomLevel.dsu != null)graphics.drawString(RandomLevel.dsu.getParent()[x + y * 64] + "", xp, yp);
//
//// Optionally, set the font back to the original size after drawing the string
//                graphics.setFont(currentFont); // Set the graphics object's font back to the original font
//
//            }
//        }
        graphics.fillRect(mouse.getX() , mouse.getY(), 8, 8);
        graphics.dispose();
        bufferStrategy.show();




    }

    public static void main(String[] args) {
        GameController Game = new GameController("SASA", new JFrame());
    }

    public Level getLevel() {
        return level;
    }


    void handlePauseRecording() {

        showRecordingButton.setText("Play Recording");
//            level.restoreSnapshot(levelCareTaker.getLastSnapshot());
//            levelCareTaker.reset();
        paused = false;
        level.restoreSnapshot(levelCareTaker.getLastSnapshot());
        recordingTimer = 0;
        levelCareTaker.reset();
        inputCareTaker.reset();

        keyboard.releaseAll();
        this.requestFocus(); //request focus for the game

        mouse.responsive = true;
        keyboard.responsive = true;
    }

}


