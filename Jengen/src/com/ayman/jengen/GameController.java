package com.ayman.jengen;


import com.ayman.jengen.Graphics.Screen;
import com.ayman.jengen.Input.Keyboard;
import com.ayman.jengen.Input.Mouse;
import com.ayman.jengen.audio.Sound;
import com.ayman.jengen.entity.mob.IPlayer;
import com.ayman.jengen.entity.mob.Player;
import com.ayman.jengen.game.contexts.AIContext;
import com.ayman.jengen.gui.AppFrame;
import com.ayman.jengen.level.Level;
import com.ayman.jengen.level.RandomLevel;
import com.ayman.jengen.level.SpawnLevel;
import com.ayman.jengen.level.snapshots.InputCareTaker;
import com.ayman.jengen.level.snapshots.InputSnapshot;
import com.ayman.jengen.level.snapshots.LevelCareTaker;
import com.ayman.jengen.level.tile.Tile;
import com.ayman.jengen.network.client.controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

/**
 * Here is the GameController class that is responsible for controlling the game.
 * It is responsible for updating the game and rendering it.
 * It also handles the recording of the game and playing it.
    Also, it handles the pause and resume of the game.
 Besides, it handles the winning and losing of the game, and the transition between levels.
 Also, it handles the window of the game and the buttons in it.
 It handles the difficulty of the game and the player name.
 */
public class GameController extends Canvas implements Runnable{




    public static int width = 300;
    public static int height = width / 12 * 8;
    public static int scaleFactor = 3;
    public static String difficulty = "Medium";
    private  boolean playingRecording = false;
    private boolean running = false;


    private final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();



    private final Screen screen;
    public Keyboard keyboard;
    private final Mouse mouse;
    public long lastTime = 0;



    private Thread thread;
    public JFrame jFrame;
    public JButton pauseButton;
    public JButton mainMenuButton;
    public JButton showRecordingButton;


    public Level level;


    public static boolean paused = false;





    public static String playerName ;
    public static AIContext.AIType aiType = AIContext.AIType.DStar;

    private final LevelCareTaker levelCareTaker = new LevelCareTaker();
    private final InputCareTaker inputCareTaker = new InputCareTaker();


    public GameController(com.ayman.jengen.game.Game game) {
        this(game.getPlayerContext().getName(), new JFrame());
    }

    public GameController(String playerName, JFrame jFrame) {
        Sound.init();
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


        if(SpawnLevel.numberOfLevels == 0 && !ClientController.isOn()) {
            level = new RandomLevel();
            Random random = new Random();
            int xPlayer = 0, yPlayer = 0;
            while(level.getTile(xPlayer, yPlayer).isSolid()) {
                xPlayer = random.nextInt(level.getWidth() - 1);
                yPlayer = random.nextInt(level.getHeight() - 1);
            }
            level.add(new Player(playerName, xPlayer* Tile.TILE_SIZE, yPlayer*Tile.TILE_SIZE, keyboard, mouse));
        } else {
            level = new SpawnLevel();
            int xPlayer = level.getPlayer().getX();
            int yPlayer = level.getPlayer().getY();
            level.removePlayer();
            level.add(new Player(playerName, xPlayer*Tile.TILE_SIZE, yPlayer*Tile.TILE_SIZE, keyboard, mouse));

        }

        handleWindow();
    }

    /**
     * This method is responsible for handling the window components.
     * It adds the buttons to the window and sets the title of the window.
     */
    public void handleWindow() {

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
            AppFrame.getInstance().setGuiState(new com.ayman.jengen.gui.states.MainMenuState());

        });


        showRecordingButton.addActionListener(e -> {
            if(levelCareTaker.getNumberOfSnapshots() == 0) return;
            playingRecording = !playingRecording;

            if(playingRecording) {
                showRecordingButton.setText("Stop Playing");
                mouse.responsive = false;
                keyboard.responsive = false;
                synchronized (levelCareTaker) {
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

    public static int getDifficulty() {
        switch (difficulty) {
            case "Easy" -> {
                return 10;
            }
            case "Medium" -> {
                return 30;
            }
            case "Hard" -> {
                return 50;
            }
        }
        return 30;
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
            if ((levelCareTaker.getNumberOfSnapshots() > 2)) {
                throw new AssertionError();
            }long now = System.nanoTime();

            delta += (now - lastTime) / ns; // number of "Updates" we need to do
            lastTime = now;

            while(delta >= 1) {
                if(!ClientController.isOn()) {
                    if( level.playerWon()){
                        handleWinning();
                    } else if(level.playerLost()) {
                        handleLosing();
                        return;
                    }
                }


                if(!playingRecording && !paused) {
                    handleRecording();
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


                jFrame.setTitle("FightEnemies | " + updates + " ups, " + frames + " fps - " + playerName + " | " + level.getPlayer().getCoins() + " Dollars");


                //reset the updates and frames
                updates = 0;
                frames = 0;
            }
        }
        thread.interrupt();
    }

    private void handleWinning() {
        System.out.println("Congratulations " + playerName);
        System.out.println("You won this level");
        System.out.println();
        if (level instanceof SpawnLevel spawnLevel && !spawnLevel.hasNextLevel()) {
            System.out.println("You have finished the game");
            System.out.println("Congratulations " + playerName);
            spawnLevel.reset();
            System.exit(0);
        }
        Sound.winningClip.setFramePosition(0);
        Sound.winningClip.start();
        loadNextLevel();
    }

    private void handleLosing() {
        System.out.println("You lost");
        System.out.println("Game Over " + playerName);
        playingRecording = false;
        Sound.losingClip.start();
        AppFrame.getInstance().setGuiState(new com.ayman.jengen.gui.states.MainMenuState());
    }

    private void loadNextLevel() {
        levelCareTaker.reset();
        inputCareTaker.reset();
        recordingTimer = 0;

        int xPlayer = level.getPlayer().getX()/Tile.TILE_SIZE;
        int yPlayer = level.getPlayer().getY()/Tile.TILE_SIZE;
        level = level.getNextLevel();
        if(SpawnLevel.numberOfLevels == 0) {
            Random random = new Random();
            while(level.getTile(xPlayer, yPlayer).isSolid()) {
                xPlayer = random.nextInt(level.getWidth() - 1);
                yPlayer = random.nextInt(level.getHeight() - 1);
            }
            level.add(new Player(playerName, xPlayer*Tile.TILE_SIZE, yPlayer*Tile.TILE_SIZE, keyboard, mouse));
        }
        else {
            int xp = level.getPlayer().getX();
            int yp = level.getPlayer().getY();
            level.removePlayer();
            level.add(new Player(playerName, xp*Tile.TILE_SIZE, yp*Tile.TILE_SIZE, keyboard, mouse));
        }

    }
    private void handleRecording() {
        if(recordingTimer % 300 == 0) {
            recordingTimer = 0;

            synchronized (levelCareTaker) {
                if (levelCareTaker.getNumberOfSnapshots() < 2) {
                    levelCareTaker.addSnapshot(level.takeSnapshot());
                } else {
                    synchronized (levelCareTaker) {
                        levelCareTaker.removeOldSnapshot();
                        levelCareTaker.addSnapshot(level.takeSnapshot());
                        if (inputCareTaker.getNumberOfSnapshots() != 600) {
                            System.out.println("You are not a genius the number of snapshots is " + inputCareTaker.getNumberOfSnapshots());
                        }
                        inputCareTaker.removeOldSnapshots(300);
                    }
                }
            }

        }
        inputCareTaker.addSnapshot(new InputSnapshot(mouse.takeSnapshot(), keyboard.takeSnapshot()));
        recordingTimer++;
    }

    private void playRecording() {
        if(paused) return;


        if(inputCareTaker.hasNext()) {
            InputSnapshot inputSnapshot = inputCareTaker.getNextSnapshot();
            mouse.restoreSnapshot(inputSnapshot.mouseSnapshot);
            keyboard.restoreSnapshot(inputSnapshot.keyboardSnapshot);
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
        if(paused)
            return;

        keyboard.update();
        mouse.update();
        level.update();
    }

    int xDelta = 1, yDelta = 1;
    int time = 0;
    public void render() {
        time+= 2;

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



//        if(time % (60*50) == 0) { // Earthquake effect
//            xDelta *= -1;
//        }
//        if((time + 30 * 50) % (60 * 50) == 0) {
//            yDelta *= -1;
//        }


        level.render(xScroll + yDelta , yScroll + xDelta, screen);
        level.renderMiniMap(screen, 0, 0);
        //copy the pixels data from screen to the pixels array of the image object
        System.arraycopy(screen.pixels, 0, this.pixels, 0, pixels.length);

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        graphics.setColor(Color.black);
        graphics.setFont(new Font("Verdana", Font.PLAIN, 20));

        graphics.drawString("Coins Left: " + level.getNumberOfCoins(), 150, 50);


        graphics.dispose();
        bufferStrategy.show();




    }


    public Level getLevel() {
        return level;
    }


    void handlePauseRecording() {

        showRecordingButton.setText("Play Recording");
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

