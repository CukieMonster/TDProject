package com.tdproject.main;

import com.tdproject.enemies.EnemyManager;
import com.tdproject.graphics.Background;
import com.tdproject.graphics.Sprite;
import com.tdproject.items.Item;
import com.tdproject.towers.TowerManager;
import com.tdproject.ui.ButtonManager;

import java.util.LinkedList;

import static com.tdproject.main.FieldParameters.X_FIELDS;
import static com.tdproject.main.FieldParameters.Y_FIELDS;

public class Game {

    private static Game instance;
//    private final int FPS_SET = 60;
    private final int UPS_SET = 60;
//    private int updates;
    private int gameSpeed = 1;
    private int money = Integer.MAX_VALUE;
    private boolean[][] collisionMap = new boolean[X_FIELDS][Y_FIELDS];
    private LinkedList<Item> droppedItems = new LinkedList<>();
    private Sprite background;

    private Game() {
        background = new Background();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void update(int u) {
        //gamePanel.updateGame();
        EnemyManager.getInstance().update();
        TowerManager.getInstance().update(u);
    }

    public void render(Object o) {
        background.draw(o, 0,0);
        ButtonManager.getInstance().draw(o);
        EnemyManager.getInstance().draw(o);
        TowerManager.getInstance().draw(o);
        drawDroppedItems(o);
    }

    public void adjustMoney(int value) {
        money += value;
    }

    public void changeGamespeed() {
        if (gameSpeed == 1) gameSpeed = 2;
        else if (gameSpeed == 2) gameSpeed = 1;
    }

    private void drawDroppedItems(Object o) {
        for (Item i : droppedItems) {
            if (i != null){
                // TODO
                //g.drawImage(i.getSprite(), 0, 0, null);
            }
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
