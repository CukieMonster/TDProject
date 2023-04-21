package com.tdproject.gamestates;

import java.awt.event.MouseEvent;

public interface Statemethods {

    void update(int u);
    void render(Object o);
//    void mouseClicked();
//    void mousedPressed();
    void mouseReleased(MouseEvent e);
    void mouseMoved(MouseEvent e);
//    void keyPressed();
//    void keyReleased();
}
