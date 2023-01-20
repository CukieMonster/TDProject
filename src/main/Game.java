package main;

import enemies.EnemyManager;
import enemies.Pathfinding;
import items.Item;
import towers.TowerManager;
import ui.Button;
import ui.ButtonManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import static main.FieldParameters.*;

public class Game implements Runnable {

    private static Game instance;
    private Thread gameThread;
    private final int FPS_SET = 60;
    private final int UPS_SET = 60;
    private int updates;
    private int gameSpeed = 1;
    private int money = Integer.MAX_VALUE;
    private boolean[][] collisionMap = new boolean[X_FIELDS][Y_FIELDS];
    private LinkedList<Item> droppedItems = new LinkedList<>();

    private BufferedImage backgroundImg;

    private Game() {
        loadBackgroundImg();
//        buttonManager = new ButtonManager(this);
//        pathfinding = new Pathfinding(this);
//        pathfinding.buildDistanceField();
//        enemyManager = new EnemyManager();
//        towerManager = new TowerManager(this);
//        gamePanel = new GamePanel(this);
        GameWindow.getInstance();
        GamePanel.getInstance().requestFocusInWindow();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(int u) {
        //gamePanel.updateGame();
        EnemyManager.getInstance().update();
        TowerManager.getInstance().update(u);
    }

    public void render(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, null);
        ButtonManager.getInstance().draw(g);
        EnemyManager.getInstance().draw(g);
        TowerManager.getInstance().draw(g);
        drawDroppedItems(g);
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
                update(updates);
                updates++;
                deltaU--;
            }
        }
    }

    public void adjustMoney(int value) {
        money += value;
    }

    public void changeGamespeed() {
        if (gameSpeed == 1) gameSpeed = 2;
        else if (gameSpeed == 2) gameSpeed = 1;
    }

    private void drawDroppedItems(Graphics g) {
        for (Item i : droppedItems) {
            if (i != null){
                g.drawImage(i.getSprite(), 0, 0, null);
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

    // Getters and setters
    public int getUpsSet() {
        return UPS_SET;
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    public boolean[][] getCollisionMap() {
        return collisionMap;
    }

    public LinkedList<Item> getDroppedItems() {
        return droppedItems;
    }
}
