package com.ayman.fightEnemies;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{


    public static final int width = 300;
    public static final int height = width / 12 * 8;
    public static final int scaleFactor = 3;
    private boolean running = false;


    private Thread thread;
    private JFrame jFrame;


    public Game() {

        Dimension size = new Dimension(width * scaleFactor, height * scaleFactor);
        setPreferredSize(size);

        jFrame = new JFrame();
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
        while(running) {
            update();
            render();
        }
    }

    public void update() {

    }

    public void render() {

        BufferStrategy bufferStrategy = getBufferStrategy();
        if(bufferStrategy == null) {
            createBufferStrategy(3); //triple buffering
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, getWidth()  , getHeight() );
        graphics.setColor(new Color(21, 232, 165));
        graphics.fillRect(0, 0, getWidth() /2 , getHeight() /2);

        graphics.dispose();
        bufferStrategy.show(); //swap buffers
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.jFrame.setResizable(false);
        game.jFrame.setTitle("FightEnemies");
        game.jFrame.add(game);
        game.jFrame.pack();
        game.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.jFrame.setLocationRelativeTo(null);
        game.jFrame.setVisible(true);

        game.start();
    }
}
