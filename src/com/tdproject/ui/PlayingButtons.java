package com.tdproject.ui;

import com.tdproject.enemies.EnemyManager;
import com.tdproject.gamestates.Playing;
import com.tdproject.towers.TowerManager;

import java.lang.reflect.Array;
import java.util.function.Consumer;

public class PlayingButtons {

    private PlayingButtons() {

    }
    public enum ButtonID {CANCEL_BUILDING, BUILD_TOWER_1, FAST_FORW_BUTTON, SKIP_BUTTON};
    public static boolean[] defaultState = {
            false,
            true,
            true,
            false
    };
    public static int[][] posistion = {
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

}
