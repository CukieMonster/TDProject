package com.tdproject.enemies;

import com.tdproject.gamestates.Playing;
import com.tdproject.gamestates.GameState;
import com.tdproject.main.Game;
import com.tdproject.main.Square;
import com.tdproject.ui.Button;
import com.tdproject.ui.ButtonManager;

import com.tdproject.ui.PlayingButtons;
import java.util.LinkedList;
import java.util.Random;

public class EnemyManager {

    private static EnemyManager instance;
    //private BufferedImage[] enemyImgs = new BufferedImage[5];
    //private final static int maxWaveSize = 20;
    private Square spawn = new Square(0, 14);
    private int waveNumber = 0;
    private int waveLimit;
    private int currentWaveProgress;
    private int currentWaveSize;
    //public Enemy[] main.tdproject.enemies = new Enemy[maxWaveSize];
    private LinkedList<Enemy> enemies = new LinkedList<>();
    //private int enemyIndex = 0;

    private boolean wavesFinished = false;
    private boolean skip = false;
    private boolean spawning = false;
    private int spawnTime = 0;
    //public List<Enemy> firstList = new LinkedList<>();
    //public List<Item> droppedItems = new LinkedList<>();

    private Random random = new Random();
    private final Button skipButton;

    private EnemyManager(Button skipButton) {
        this.skipButton = skipButton;
        //GameObjectList.enemyManager = this;
        //importImg();
        spawnWave();
    }

    public static EnemyManager getInstance() {
        if (instance == null) {
            instance = new EnemyManager(PlayingButtons.buttons[0]);
        }
        return instance;
    }

    public void update(/*int u*/) {
        if (spawning) {
            spawnTime -= Playing.getInstance().getGameSpeed();
            if (spawnTime <= 0) {
                spawnTime = EnemyParameters.SPAWN_INTERVAL;
                spawnEnemy();
            }
        }
        else {
            spawnTime -= Playing.getInstance().getGameSpeed();
            if (spawnTime <= 0) {
                spawnWave();
            }
        }

        LinkedList<Enemy> toRemove = new LinkedList<>();
        for (Enemy e : enemies) {
            if (e.isActive()) e.update();
            else toRemove.add(e);
            /*if (e != null) {
                e.update();
            }*/
        }
        enemies.removeAll(toRemove);
        if (enemies.isEmpty() && currentWaveProgress >= waveLimit) {
            waveCompleted();
        }
    }

    public void waveCompleted() {
        // game finished
        if (waveNumber >= 10) {
            Game.getInstance().setCurrentGameState(GameState.States.MAIN_MENU);
        }

        // game continues
        spawning = false;
        //ButtonManager.getInstance().getButtons()[PlayingButtons.ButtonID.SKIP_BUTTON.ordinal()].setActive(true);
        PlayingButtons.SKIP_BUTTON.setActive(true);
        spawnTime = EnemyParameters.WAVE_INTERVAL;
    }

    public void spawnWave() {
        //ButtonManager.getInstance().getButtons()[PlayingButtons.ButtonID.SKIP_BUTTON.ordinal()].setActive(false);
        // TODO use ButtonPanel for game control buttons, build tower buttons
        PlayingButtons.SKIP_BUTTON.setActive(false);
        waveNumber++;
        Playing.getInstance().updateRound(waveNumber);
        waveLimit = waveNumber * EnemyParameters.WAVE_GROWTH;
        currentWaveProgress = 0;
        currentWaveSize = 0;
        spawnTime = 0;
        spawning = true;
    }

    private void spawnEnemy() {
        if (currentWaveProgress < waveLimit) {
            //waves when enemies are unlocked
            int highestEnemy = 5;
            if (waveNumber <= 3) {
                highestEnemy = 1;
            }
            else if (waveNumber <= 5) {
                highestEnemy = 2;
            }
            else if (waveNumber <= 10) {
                highestEnemy = 3;
            }
            else if (waveNumber <= 15) {
                highestEnemy = 4;
            }

            int enemyType = random.nextInt(highestEnemy);

            enemies.add(new Enemy(spawn, enemyType));
            currentWaveProgress += EnemyParameters.PROGRESS[enemyType];
            currentWaveSize++;
        }
        else {
            //wave complete
            //GameObjectList.buttonManager.buttons[BUTTONS.SKIP_BUTTON.ordinal()].active = true;
        }
    }

/*    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/main.tdproject.enemies/enemy_1.png");
        try {
            enemyImgs[0] = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void draw(Object o) {
        for (Enemy e : enemies) {
            if (e != null) {
                e.drawCentered(o);
                //g.drawImage(enemyImgs[0], (int) e.getPosition().x, (int) e.getPosition().y, null);
            }
        }
    }

    // Getters and setters
    public LinkedList<Enemy> getEnemies() {
        return enemies;
    }

    public int getWaveNumber() {
        return waveNumber;
    }
}
