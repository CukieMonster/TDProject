package com.tdproject.gamestates;

import com.tdproject.enemies.EnemyManager;
import com.tdproject.graphics.Background;
import com.tdproject.graphics.Sprite;
import com.tdproject.inputs.MyEvent;
import com.tdproject.items.Item;
import com.tdproject.towers.TowerManager;
import com.tdproject.ui.ButtonManager;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

import static com.tdproject.main.FieldParameters.X_FIELDS;
import static com.tdproject.main.FieldParameters.Y_FIELDS;

public class Playing implements Statemethods{

    private static Playing instance;
    private int gameSpeed = 1;
    private int money = Integer.MAX_VALUE;
    private boolean[][] collisionMap = new boolean[X_FIELDS][Y_FIELDS];
    private LinkedList<Item> droppedItems = new LinkedList<>();
    private Sprite background;

    private Playing() {
        background = new Background();
    }

    @Override
    public void update(int u) {
        EnemyManager.getInstance().update();
        TowerManager.getInstance().update(u);
    }

    @Override
    public void render(Object o) {
        background.draw(o, 0,0);
        ButtonManager.getInstance().draw(o);
        EnemyManager.getInstance().draw(o);
        TowerManager.getInstance().draw(o);
        drawDroppedItems(o);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ButtonManager.getInstance().mouseReleased(new MyEvent(e));
        TowerManager.getInstance().mouseReleased(new MyEvent(e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        ButtonManager.getInstance().mouseMoved(new MyEvent(e));
        TowerManager.getInstance().mouseMoved(new MyEvent(e));
    }


    public void adjustMoney(int value) {
        money += value;
    }

    public void changeGameSpeed() {
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

    public static Playing getInstance() {
        if (instance == null) {
            instance = new Playing();
        }
        return instance;
    }

    // Getters and setters

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
