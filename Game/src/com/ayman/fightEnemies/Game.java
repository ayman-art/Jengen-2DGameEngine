package com.ayman.fightEnemies;


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

    private Thread thread;
    private JFrame jFrame;
    private int xr = 0;


    public Game() {

        Dimension size = new Dimension(width * scaleFactor, height * scaleFactor);
        setPreferredSize(size);

        jFrame = new JFrame();
        screen = new Screen(width, height);
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
        final double ns = 1000000000.0 / 60.0; //for converting to one of sixity part of second
        double delta = 0 / ns;
        int frames = 0;
        int updates = 0;
        while(running) {
            long now = System.nanoTime();

            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                update();
                updates++;
                delta--;
            }


            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                jFrame.setTitle("FightEnemies | " + updates + " ups, " + frames + " fps");
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
        xr++;
    }

    public void render() {

        BufferStrategy bufferStrategy = getBufferStrategy();
        if(bufferStrategy == null) {
            createBufferStrategy(3); //triple buffering
            return;
        }

        screen.clear();
        screen.render(xr, 0);

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        graphics.dispose();
        bufferStrategy.show();


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
