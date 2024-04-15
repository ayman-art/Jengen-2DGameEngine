package com.ayman.fightEnemies.Input;

import com.ayman.fightEnemies.GameController;
import com.ayman.fightEnemies.level.snapshots.InputSnapshot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

        keys[e.getKeyCode()] = true;
//        System.out.println("y");

    }

    @Override
    public void keyPressed(KeyEvent e) {

        keys[e.getKeyCode()] = true;
        if(e.getKeyCode() == KeyEvent.VK_S) {
            System.out.print("S");
            GameController.paused = !GameController.paused;

        }
//        System.out.println("key pressed" + e.getKeyCode());

    }

    @Override
    public void keyReleased(KeyEvent e) {

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
