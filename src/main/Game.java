package main;

import enemies.EnemyManager;
import enemies.Pathfinding;
import towers.TowerManager;
import ui.Button;
import ui.ButtonManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static main.FieldParameters.*;

public class Game implements Runnable {

    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 60;
    public static final int UPS_SET = 60;
    public int updates;
    public static int gameSpeed = 1;
    private int money = Integer.MAX_VALUE;
    public boolean[][] collisionMap = new boolean[X_FIELDS][Y_FIELDS];
    private ButtonManager buttonManager;
    private EnemyManager enemyManager;
    private TowerManager towerManager;
    private Pathfinding pathfinding;

    private BufferedImage backgroundImg;

    public Game() {
        loadBackgroundImg();
        buttonManager = new ButtonManager(this);
        pathfinding = new Pathfinding(this);
        pathfinding.buildDistanceField();
        enemyManager = new EnemyManager(this);
        towerManager = new TowerManager(this);
        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
        gamePanel.requestFocusInWindow();
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(int u) {
        //gamePanel.updateGame();
        enemyManager.update();
        towerManager.update(u);
    }

    public void render(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, null);
        buttonManager.draw(g);
        enemyManager.draw(g);
        towerManager.draw(g);
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
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (deltaU >= 1) {
                update(updates);
                updates++;
                deltaU--;
            }
        }
    }

    public void adjustMoney(int value) {
        money += value;
    }

    private void loadBackgroundImg() {
        InputStream is = getClass().getResourceAsStream("/background.png");
        try {
            backgroundImg = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ButtonManager getButtonManager() {
        return buttonManager;
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public Pathfinding getPathfinding() {
        return pathfinding;
    }
}
