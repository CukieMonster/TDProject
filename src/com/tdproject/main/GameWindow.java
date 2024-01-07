package com.tdproject.main;

import java.awt.GraphicsEnvironment;
import javax.swing.*;

public class GameWindow extends JFrame {

    private static GameWindow instance;

    private GameWindow() {

        //setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        add(GamePanel.getInstance());
        //setLocationRelativeTo(null);
        setResizable(false);
        var device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        device.setFullScreenWindow(this);
        pack();
        setVisible(true);
    }

    public static GameWindow getInstance() {
        if (instance == null) {
            instance = new GameWindow();
        }
        return instance;
    }
}
