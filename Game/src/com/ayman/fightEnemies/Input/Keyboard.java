package com.ayman.fightEnemies.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {


    private boolean[] keys = new boolean[200];
    public boolean up, down, left, right; //States of the keys
    public boolean s;

    public void update() {

        up = keys[KeyEvent.VK_UP] ;
        down = keys[KeyEvent.VK_DOWN] ;
        left = keys[KeyEvent.VK_LEFT] ;
        right = keys[KeyEvent.VK_RIGHT] ;
        s = keys[KeyEvent.VK_S] ;


    }
    @Override
    public void keyTyped(KeyEvent e) {

        keys[e.getKeyCode()] = true;
        System.out.println("y");

    }

    @Override
    public void keyPressed(KeyEvent e) {

        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {

        keys[e.getKeyCode()] = false;
    }
}
