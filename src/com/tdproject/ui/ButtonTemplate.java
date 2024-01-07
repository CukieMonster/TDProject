package com.tdproject.ui;

import com.tdproject.gamestates.GameState;
import java.util.function.Consumer;

public abstract class ButtonTemplate {

    private GameState.States gameState;
    private boolean[] defaultState;
    private int[][] position;
    private Consumer[] action;
    public GameState.States getGameState() {
        return gameState;
    }

    public boolean[] getDefaultState() {
        return defaultState;
    }

    public int[][] getPosition() {
        return position;
    }

    public Consumer[] getAction() {
        return action;
    }
}
