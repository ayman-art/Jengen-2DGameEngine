package com.ayman.fightEnemies.Input;

import com.ayman.fightEnemies.level.snapshots.InputCareTaker;
import com.ayman.fightEnemies.level.snapshots.InputSnapshot;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

    private int mouseX = -1;
    private int mouseY = -1;
    private int mouseButton = -1;
    public volatile boolean  responsive = true;

    public int getX() {
        return mouseX;
    }

    public int getY() {
        return mouseY;
    }

    public int getButton() {
        return mouseButton;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(responsive)mouseButton = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(responsive)mouseButton = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (responsive) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse moved_________________________________________________________________________");
        if (responsive) {
            System.out.println("Mouse moved|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

    public void update() {
        System.out.println(responsive);
    }

    public void restoreSnapshot(InputSnapshot.MouseSnapshot snapshot) {
        mouseX = snapshot.x();
        mouseY = snapshot.y();
        mouseButton = snapshot.button();
    }

    public InputSnapshot.MouseSnapshot takeSnapshot() {
        System.out.println("Mouse snapshot taken   ________________________________________________________");
        return new InputSnapshot.MouseSnapshot(mouseX, mouseY, mouseButton);
    }
}