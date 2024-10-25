package com.tdproject.main;

public class GamePC implements Runnable {

    private static GamePC instance;
    private Thread gameThread;
    private final int FPS_SET = 60;
    private final int UPS_SET = 60;
    private int updates;

    private GamePC() {
        GameWindow.getInstance();
        GamePanel.getInstance().requestFocusInWindow();
    }

    public static GamePC getInstance() {
        if (instance == null) {
            instance = new GamePC();
        }
        return instance;
    }

    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();
        int frames = 0;
        //int updates = 0;
        double deltaF = 0;
        double deltaU = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaF += (currentTime - previousTime) / timePerFrame;
            deltaU += (currentTime - previousTime) / timePerUpdate;
            previousTime = currentTime;

            if (deltaF >= 1) {
                GamePanel.getInstance().repaint();
                frames++;
                deltaF--;
            }

            if (deltaU >= 1) {
                Game.getInstance().update(updates);
                updates++;
                deltaU--;
            }
        }
    }

}
