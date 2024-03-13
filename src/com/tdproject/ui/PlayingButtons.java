package com.tdproject.ui;

import com.tdproject.enemies.EnemyManager;
import com.tdproject.gamestates.Playing;
import com.tdproject.gamestates.GameState;
import com.tdproject.graphics.Sprite;
import com.tdproject.towers.TowerManager;

import java.util.function.Consumer;

public class PlayingButtons extends ButtonTemplate {

    public static final Button SKIP_BUTTON = new Button(
            false,
            Sprite.BUTTON_SKIP,
            i -> EnemyManager.getInstance().spawnWave()
    );

    public static final Button[] buttons = {
            SKIP_BUTTON,
            new Button(
                    true,
                    Sprite.BUTTON_FAST_FORWARD,
                    i -> Playing.getInstance().changeGameSpeed()
            )
    };

    private static PlayingButtons instance;

    public static GameState.States gameState = GameState.States.PLAYING;
    //public enum ButtonID {CANCEL_BUILDING, BUILD_TOWER_1, FAST_FORWARD, SKIP};
//    public static Map<String, Integer> buttonID = new HashMap<String, Integer>()
//    {
//        {
//            put("cancelBuilding", 0);
//            put("buildTower1", 1);
//            put("fastForward", 2);
//            put("skip", 3);
//        }
//    };
    //public static final String[] ButtonID = {"cancelBuilding", "buildTower1", "fastForward", "skip"};
    public static boolean[] defaultState = {
            false,
            true,
            true,
            false
    };

    public static int[][] position = {
            {1800, 900},
            {1800, 100},
            {1600, 900},
            {1800, 900}
    };
    //public Consumer<Integer>[] action = new Consumer[2];
    //public Consumer<Integer>[] action = (Consumer<Integer>[])Array.newInstance(Consumer.class, 2);

    public static Consumer[] action = {
            i -> TowerManager.getInstance().cancelBuild(),
            i -> TowerManager.getInstance().enterBuildMode(0),
            i -> Playing.getInstance().changeGameSpeed(),
            i -> EnemyManager.getInstance().spawnWave()
    };

    private PlayingButtons() {

    }

    public static PlayingButtons getInstance() {
        if (instance == null) {
            instance = new PlayingButtons();
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
