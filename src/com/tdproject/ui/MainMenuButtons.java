package com.tdproject.ui;

import com.tdproject.gamestates.GameState;
import com.tdproject.main.FieldParameters;
import com.tdproject.towers.TowerManager;

import java.util.function.Consumer;

public class MainMenuButtons extends ButtonTemplate {

    private static MainMenuButtons instance;

    private GameState gameState = GameState.MAIN_MENU;

    private boolean[] defaultState = {
            true,
            true,
            true,
            true
    };

    private int[][] position = {
            {FieldParameters.X_RESOLUTION / 2, 300},
            {FieldParameters.X_RESOLUTION / 2, 400},
            {FieldParameters.X_RESOLUTION / 2, 500},
            {FieldParameters.X_RESOLUTION / 2, 600}
    };

    private Consumer[] action = {
            i -> GameState.gameState = GameState.PLAYING,
            i -> GameState.gameState = GameState.INVENTORY,
            i -> GameState.gameState = GameState.SETTINGS,
            i -> System.exit(0)
    };

    private MainMenuButtons() {

    }

    public static MainMenuButtons getInstance() {
        if (instance == null) {
            instance = new MainMenuButtons();
        }
        return instance;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public boolean[] getDefaultState() {
        return defaultState;
    }

    @Override
    public int[][] getPosition() {
        return position;
    }

    @Override
    public Consumer[] getAction() {
        return action;
    }
}
