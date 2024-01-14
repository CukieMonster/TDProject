package com.tdproject.gamestates;

import com.tdproject.enemies.EnemyManager;
import com.tdproject.graphics.Background;
import com.tdproject.graphics.Sprite;
import com.tdproject.graphics.Text;
import com.tdproject.inputs.MyEvent;
import com.tdproject.items.Item;
import com.tdproject.towers.TowerManager;
import com.tdproject.ui.ButtonManager;
import com.tdproject.ui.PlayingButtons;
import lombok.Getter;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

import static com.tdproject.main.FieldParameters.X_FIELDS;
import static com.tdproject.main.FieldParameters.Y_FIELDS;

public class Playing extends GameState {

    private static Playing instance;
    @Getter
    private ButtonManager buttonManager;
    @Getter
    private int gameSpeed = 1;
    private Text[] infos = {new Text("Round: 0/10", 0, 30), new Text("Health: 100", 200, 30), new Text("Gold: 500", 400, 30)};
    private int money = Integer.MAX_VALUE;
    private int health = 100;
    @Getter
    private boolean[][] collisionMap = new boolean[X_FIELDS][Y_FIELDS];
    @Getter
    private LinkedList<Item> droppedItems = new LinkedList<>();
    private Sprite background;

    private Playing() {
        buttonManager = new ButtonManager(PlayingButtons.buttons);
        background = new Background();
    }

    public static Playing getInstance() {
        if (instance == null) {
            instance = new Playing();
        }
        return instance;
    }

    @Override
    public void update(int u) {
        EnemyManager.getInstance().update();
        TowerManager.getInstance().update(u);
    }

    @Override
    public void render(Object o) {
        background.drawCentered(o);
        drawInfos(o);
        buttonManager.draw(o);
        EnemyManager.getInstance().draw(o);
        TowerManager.getInstance().draw(o);
        drawDroppedItems(o);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttonManager.mouseReleased(new MyEvent(e));
        TowerManager.getInstance().mouseReleased(new MyEvent(e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        buttonManager.mouseMoved(new MyEvent(e));
        TowerManager.getInstance().mouseMoved(new MyEvent(e));
    }


    public void adjustMoney(int value) {
        money += value;
        infos[2].setString("Gold: " + money);
    }

    public void reduceHealth(int value) {
        health -= value;
        infos[1].setString("Health: "+ health);
    }

    public void updateRound(int value) {
        infos[0].setString(String.format("Round: %d/10", value));
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

    private void drawInfos(Object o) {
        Text.setFont(o);
        for (Text t : infos) {
            t.draw(o);
        }
    }

}
