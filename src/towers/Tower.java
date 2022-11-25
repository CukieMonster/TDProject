package towers;

import enemies.Enemy;
import enemies.EnemyManager;
import enemies.HomingMissile;
import main.Game;
import main.GamePanel;
import main.Square;

import javax.vecmath.Vector2d;
import java.awt.image.BufferedImage;
import java.util.*;

import static main.FieldParameters.FIELD_SIZE;
import static towers.TowerParameters.*;

public class Tower {

    private EnemyManager enemyManager;

    public BufferedImage img;
    private List<Enemy> enemiesInRange = new LinkedList<>();
    public int towerType;
    private int damage;
    private float attackSpeed;
    private int range;
    private int radius;
    private int cost;
    public Square square = new Square(-1, -1);
    public Vector2d position = new Vector2d(0, 0);
    //public int x;
    //public int y;

    public Set<HomingMissile> missiles = new HashSet<>();
    private int lastShot;
    public boolean active = false;
    public boolean visible = false;

    public Tower(int towerType, BufferedImage img, EnemyManager em) {
        this.towerType = towerType;
        damage = DAMAGE[towerType];
        attackSpeed = ATTACK_SPEED[towerType];
        range = RANGE[towerType];
        radius = (FIELD_SIZE / 2) + (FIELD_SIZE * range);
        cost = COST[towerType];
        //this.x = x;
        //this.y = y;
        this.img = img;
        enemyManager = em;
    }

    public void update(int u) {
        getEnemiesInRange();
        attemptShot(u);
        for (HomingMissile m : missiles) {
            if (m != null) {
                m.update();
            }
        }
    }

    private void attemptShot(int u) {
        if (lastShot + attackSpeed * Game.UPS_SET <= u) {
            if (findTarget()) {
                lastShot = u;
            }
        }
    }

    private boolean findTarget() {
        if (enemiesInRange.isEmpty()) {
            return false;
        }
        Enemy furthest = enemiesInRange.get(0);
        for (Enemy e : enemiesInRange) {
            if (e.distanceToTarget < furthest.distanceToTarget) {
                furthest = e;
            }
        }
        shoot(furthest);
        return true;
    }

    private void shoot(Enemy e) {
        //homingMissile
        missiles.add(new HomingMissile(position, e, this, MISSILESPEED[towerType], damage));
    }

    private void getEnemiesInRange() {
        enemiesInRange.clear();
        for (Enemy e : enemyManager.enemies) {
            if (e != null) {
                if (distanceToEnemy(e) < radius) {
                    enemiesInRange.add(e);
                }
            }
        }
    }

    private double distanceToEnemy(Enemy e) {
        return Math.sqrt(Math.pow((position.x - e.position.x), 2) + Math.pow((position.y - e.position.y), 2));
    }
}
