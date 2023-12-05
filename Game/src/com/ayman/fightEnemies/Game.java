package com.ayman.fightEnemies;



public class Game implements Runnable{

    private static final int width = 300;
    private static final int height = width / 12 * 8;
    private static final int scaleFactor = 3;
    private boolean running = false;


    private Thread thread;






    int getWidth() {
        return width;
    }
    int getHeight() {
        return height;
    }
    int getScaleFactor() {
        return scaleFactor;
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

        }
    }
}
