package com.ayman.jengen.Input;

import com.ayman.jengen.GameController;
import com.ayman.jengen.level.snapshots.InputSnapshot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Keyboard is a class that represents the Keyboard input of the game.
    It includes the states of the keys, and the responsive boolean.
 */
public class Keyboard implements KeyListener {


    private final boolean[] keys = new boolean[200];
    public boolean up, down, left, right; //States of the keys
    public boolean s;

    public boolean responsive = true;

    public  void releaseAll() {
        for(int i = 0; i < 200; i++) {
            keys[i] = false;
        }
    }

    public void update() {
        if(!responsive) {
            return;
        }

        up = keys[KeyEvent.VK_UP] ;
        down = keys[KeyEvent.VK_DOWN] ;
        left = keys[KeyEvent.VK_LEFT] ;
        right = keys[KeyEvent.VK_RIGHT] ;
        s = keys[KeyEvent.VK_S] ;


    }
    @Override
    public void keyTyped(KeyEvent e) {

        if(e.getKeyCode() >= 200) {
            return;
        }
        keys[e.getKeyCode()] = true;

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() >= 200) {
            return;
        }

        keys[e.getKeyCode()] = true;
        if(e.getKeyCode() == KeyEvent.VK_S) {
            System.out.print("S");
            GameController.paused = !GameController.paused;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() >= 200) {
            return;
        }

        keys[e.getKeyCode()] = false;
    }

    public void restoreSnapshot(InputSnapshot.KeyboardSnapshot snapshot) {
        this.up = snapshot.up();
        this.down = snapshot.down();
        this.left = snapshot.left();
        this.right = snapshot.right();
        this.s = snapshot.s();
    }

    public InputSnapshot.KeyboardSnapshot takeSnapshot() {
        return new InputSnapshot.KeyboardSnapshot(up, down, left, right, s);
    }

}
