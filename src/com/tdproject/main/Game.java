package com.tdproject.main;

import com.tdproject.enemies.EnemyManager;
import com.tdproject.gamestates.GameState;
import com.tdproject.gamestates.Playing;
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

    private Game() {
        //background = new Background();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void update(int u) {
        //gamePanel.updateGame();
        switch (GameState.gameState) {
            case MENU:
                break;
            case SETTINGS:
                break;
            case INVENTORY:
                break;
            case PLAYING:
                Playing.getInstance().update(u);
                break;
            case PAUSED:
                break;
        }
    }

    public void render(Object o) {
        switch (GameState.gameState) {
            case MENU:
                break;
            case SETTINGS:
                break;
            case INVENTORY:
                break;
            case PLAYING:
                Playing.getInstance().render(o);
                break;
            case PAUSED:
                break;
        }
    }



    public int getUpsSet() {
        return UPS_SET;
    }
}
