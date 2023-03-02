package com.tdproject.inputs;

import com.tdproject.towers.TowerManager;
import com.tdproject.ui.ButtonManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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
        ButtonManager.getInstance().mouseReleased(new MyEvent(e));
        TowerManager.getInstance().mouseReleased(new MyEvent(e));
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
        ButtonManager.getInstance().mouseMoved(new MyEvent(e));
        TowerManager.getInstance().mouseMoved(new MyEvent(e));
    }
}
