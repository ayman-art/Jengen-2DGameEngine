package com.ayman.fightEnemies.Input;

import com.ayman.fightEnemies.Game;
import com.ayman.fightEnemies.level.snapshots.InputSnapshot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class Keyboard implements KeyListener {


    private boolean[] keys = new boolean[200];
    public boolean up, down, left, right; //States of the keys
    public boolean s;

    public  void releaseAll() {
        for(int i = 0; i < 200; i++) {
            keys[i] = false;
        }
    }

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
//        System.out.println("y");

    }

    @Override
    public void keyPressed(KeyEvent e) {

        keys[e.getKeyCode()] = true;
        if(e.getKeyCode() == KeyEvent.VK_S) {
            System.out.print("S");
            Game.paused = !Game.paused;

        }
        System.out.println("key pressed" + e.getKeyCode());

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
}
