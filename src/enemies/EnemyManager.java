package enemies;

import main.Square;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static enemies.EnemyParameters.PROGRESS;

public class EnemyManager {

    private BufferedImage[] enemyImgs = new BufferedImage[5];
    private final static int maxWaveSize = 20;
    private Square spawn = new Square(0, 14);
    public int waveNumber;
    public int waveLimit = 10;
    public int currentWaveProgress = 0;
    public int currentWaveSize = 0;
    public Enemy[] enemies = new Enemy[maxWaveSize];
    private int enemyIndex = 0;

    public boolean wavesFinished = false;
    public boolean skip = false;
    private int currentRound = 1;
    //public List<Enemy> firstList = new LinkedList<>();
    //public List<Item> droppedItems = new LinkedList<>();

    private Random random = new Random();

    public EnemyManager() {
        importImg();
        spawnEnemy();
    }

    public void update() {
        for (Enemy e : enemies) {
            if (e != null && e.active) {
                e.update();
            }
        }
    }

    //do not try to spawn more enemies than supported!
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

            //firstList.add(new Enemy(spawn, enemyType));
            while (true) {
                if (enemies[enemyIndex] == null || !enemies[enemyIndex].active) {
                    enemies[enemyIndex] = new Enemy(spawn, enemyType);
                    break;
                }
            }

            currentWaveProgress += PROGRESS[enemyType];
            currentWaveSize++;
        }
        else {
            //wave complete
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
            if (e != null && e.active) {
                g.drawImage(enemyImgs[0], e.x, e.y, null);
            }
        }
    }
}
