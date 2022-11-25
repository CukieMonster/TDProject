package enemies;

import main.Game;
import main.GameObjectList;
import main.Square;
import ui.BUTTONS;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;

import static enemies.EnemyParameters.*;

public class EnemyManager {

    private BufferedImage[] enemyImgs = new BufferedImage[5];
    //private final static int maxWaveSize = 20;
    private Square spawn = new Square(0, 14);
    public int waveNumber = 0;
    public int waveLimit;
    public int currentWaveProgress;
    public int currentWaveSize;
    //public Enemy[] enemies = new Enemy[maxWaveSize];
    public LinkedList<Enemy> enemies = new LinkedList<>();
    //private int enemyIndex = 0;

    public boolean wavesFinished = false;
    public boolean skip = false;
    private boolean spawning = false;
    private int spawnTime = 0;
    private int currentRound = 1;
    //public List<Enemy> firstList = new LinkedList<>();
    //public List<Item> droppedItems = new LinkedList<>();

    private Random random = new Random();

    public EnemyManager() {
        GameObjectList.enemyManager = this;
        importImg();
        spawnWave();
    }

    public void update(/*int u*/) {
        if (spawning) {
            spawnTime -= Game.gameSpeed;
            if (spawnTime <= 0) {
                spawnTime = SPAWN_INTERVAL;
                spawnEnemy();
            }
        }
        else {
            spawnTime -= Game.gameSpeed;
            if (spawnTime <= 0) {
                spawnWave();
            }
        }

        LinkedList<Enemy> toRemove = new LinkedList<>();
        for (Enemy e : enemies) {
            if (e.active) e.update();
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
        spawning = false;
        GameObjectList.buttonManager.buttons[BUTTONS.SKIP_BUTTON.ordinal()].active = true;
        spawnTime = WAVE_INTERVAL;
    }

    public void spawnWave() {
        GameObjectList.buttonManager.buttons[BUTTONS.SKIP_BUTTON.ordinal()].active = false;
        waveNumber++;
        waveLimit = waveNumber * WAVE_GROWTH;
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
            currentWaveProgress += PROGRESS[enemyType];
            currentWaveSize++;
        }
        else {
            //wave complete
            //GameObjectList.buttonManager.buttons[BUTTONS.SKIP_BUTTON.ordinal()].active = true;
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/enemy_1.png");
        try {
            enemyImgs[0] = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            if (e != null) {
                g.drawImage(enemyImgs[0], (int) e.position.x, (int) e.position.y, null);
            }
        }
    }
}
