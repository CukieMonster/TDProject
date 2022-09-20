package main;

import enemies.EnemyHandler;
import enemies.Pathfinding;

import java.awt.*;

import static main.FieldParameters.*;

public class Game implements Runnable {

    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 60;
    private final int UPS_SET = 60;
    public static int gameSpeed = 1;
    public static boolean[][] collisionMap = new boolean[X_FIELDS][Y_FIELDS];
    private EnemyHandler enemyHandler;

    public Game() {
        Pathfinding.buildDistanceField();
        enemyHandler = new EnemyHandler();
        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
        gamePanel.requestFocusInWindow();
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        //gamePanel.updateGame();
        enemyHandler.update();
    }

    public void render(Graphics g) {
        enemyHandler.draw(g);
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();
        int frames = 0;
        int updates = 0;
        double deltaF = 0;
        double deltaU = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaF += (currentTime - previousTime) / timePerFrame;
            deltaU += (currentTime - previousTime) / timePerUpdate;
            previousTime = currentTime;

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }
        }
    }
}
