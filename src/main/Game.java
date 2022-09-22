package main;

import enemies.EnemyManager;
import enemies.Pathfinding;
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
    private final int UPS_SET = 60;
    public static int gameSpeed = 1;
    public static boolean[][] collisionMap = new boolean[X_FIELDS][Y_FIELDS];
    private ButtonManager buttonManager;
    private EnemyManager enemyManager;

    private BufferedImage backgroundImg;

    public Game() {
        loadBackgroundImg();
        buttonManager = new ButtonManager();
        Pathfinding.buildDistanceField();
        enemyManager = new EnemyManager();
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
        enemyManager.update();
    }

    public void render(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, null);
        buttonManager.draw(g);
        enemyManager.draw(g);
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
}
