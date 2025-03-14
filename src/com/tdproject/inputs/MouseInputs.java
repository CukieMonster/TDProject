package com.tdproject.inputs;

import com.tdproject.gamestates.MainMenu;
import com.tdproject.gamestates.Playing;

import com.tdproject.main.Game;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;

// This class has specific PC functionality
public class MouseInputs implements MouseListener, MouseMotionListener {

    private static MouseInputs instance;

    private MouseInputs() {

    }

    public static MouseInputs getInstance() {
        if (instance == null) {
            instance = new MouseInputs();
        }
        return instance;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
//        ButtonManager.getInstance().mousePressed(new MyEvent(e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Game.getInstance().getCurrentGameState()) {
            case MAIN_MENU:
                MainMenu.getInstance().mouseReleased(new MyEvent(e));
                break;
            case SETTINGS:
                break;
            case INVENTORY:
                break;
            case PLAYING:
                Playing.getInstance().mouseReleased(new MyEvent(e));
                break;
            case PAUSED:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (Game.getInstance().getCurrentGameState()) {
            case MAIN_MENU:
                MainMenu.getInstance().mouseMoved(new MyEvent(e));
                break;
            case SETTINGS:
                break;
            case INVENTORY:
                break;
            case PLAYING:
                Playing.getInstance().mouseMoved(new MyEvent(e));
                break;
            case PAUSED:
                break;
        }
    }
}
