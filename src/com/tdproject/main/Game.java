package com.tdproject.main;

import com.tdproject.gamestates.GameState;
import com.tdproject.gamestates.MainMenu;
import com.tdproject.gamestates.Playing;

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
            case MAIN_MENU:
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
            case MAIN_MENU:
                MainMenu.getInstance().render(o);
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
