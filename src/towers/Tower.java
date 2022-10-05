package towers;

import enemies.Enemy;
import enemies.EnemyManager;
import main.Game;
import main.Square;

import java.awt.image.BufferedImage;
import java.util.List;

import static main.FieldParameters.FIELD_SIZE;
import static towers.TowerParameters.*;

public class Tower {

    private EnemyManager enemyManager;

    public BufferedImage img;
    private List<Enemy> enemiesInRange;
    public int towerType;
    private int damage;
    private float attackSpeed;
    private int range;
    private int radius;
    private int cost;
    public Square square;
    public int x;
    public int y;

    private int lastShot;
    public boolean active = false;
    public boolean visible = false;

    public Tower(int towerType, int x, int y, BufferedImage img) {
        this.towerType = towerType;
        damage = DAMAGE[towerType];
        attackSpeed = ATTACK_SPEED[towerType];
        range = RANGE[towerType];
        radius = (FIELD_SIZE / 2) + (FIELD_SIZE * range);
        cost = COST[towerType];
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public void update(int u) {
        attemptShot(u);
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
    }

    private void getEnemiesInRange() {
        enemiesInRange.clear();
        for (Enemy e : enemyManager.enemies) {
            if (distanceToEnemy(e) < radius) {
                enemiesInRange.add(e);
            }
        }
    }

    private double distanceToEnemy(Enemy e) {
        return Math.sqrt(Math.pow((x - e.x), 2) + Math.pow((y - e.y), 2));
    }
}
