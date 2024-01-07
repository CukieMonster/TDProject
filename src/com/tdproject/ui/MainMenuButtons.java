package com.tdproject.ui;

import com.tdproject.gamestates.GameState;
import com.tdproject.main.FieldParameters;
import com.tdproject.main.Game;

import java.util.function.Consumer;

public class MainMenuButtons extends ButtonTemplate {

    public static final Button[] buttons = {
            new Button(
                    true,
                    FieldParameters.X_RESOLUTION / 2,
                    300,
                    "play",
                    i -> Game.getInstance().setCurrentGameState(GameState.States.PLAYING)
            ),
            new Button(
                    true,
                    FieldParameters.X_RESOLUTION / 2,
                    400,
                    "inventory",
                    i -> Game.getInstance().setCurrentGameState(GameState.States.INVENTORY)
            ),
            new Button(
                    true,
                    FieldParameters.X_RESOLUTION / 2,
                    500,
                    "settings",
                    i -> Game.getInstance().setCurrentGameState(GameState.States.SETTINGS)
            ),
            new Button(
                    true,
                    FieldParameters.X_RESOLUTION / 2,
                    600,
                    "quit",
                    i -> System.exit(0)
            )
    };

    private static MainMenuButtons instance;

    private GameState.States gameState = GameState.States.MAIN_MENU;

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
            i -> Game.getInstance().setCurrentGameState(GameState.States.PLAYING),
            i -> Game.getInstance().setCurrentGameState(GameState.States.INVENTORY),
            i -> Game.getInstance().setCurrentGameState(GameState.States.SETTINGS),
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
    public GameState.States getGameState() {
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
