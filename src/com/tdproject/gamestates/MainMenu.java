package com.tdproject.gamestates;

import com.tdproject.inputs.MyEvent;
import com.tdproject.ui.ButtonManager;
import com.tdproject.ui.MainMenuButtons;

import java.awt.event.MouseEvent;

public class MainMenu implements Statemethods {

    private static MainMenu instance;
    private ButtonManager buttonManager;

    private MainMenu() {
        buttonManager = new ButtonManager(MainMenuButtons.getInstance());
    }

    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    @Override
    public void update(int u) {

    }

    @Override
    public void render(Object o) {
        buttonManager.draw(o);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttonManager.mouseReleased(new MyEvent(e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
