package com.tdproject.gamestates;

import java.awt.event.MouseEvent;

public abstract class GameState {

    abstract void update(int u);

    abstract void render(Object o);

//    void mouseClicked();
//    void mousedPressed();

    abstract void mouseReleased(MouseEvent e);

    abstract void mouseMoved(MouseEvent e);

//    void keyPressed();
//    void keyReleased();

    public enum States {

        MAIN_MENU, SETTINGS, INVENTORY, PLAYING, PAUSED;

    }

}
