package towers;

import enemies.Enemy;
import enemies.EnemyManager;

import java.util.List;

import static main.FieldParameters.FIELD_SIZE;
import static towers.TowerParameters.*;

public class Tower {

    private EnemyManager enemyManager;

    private List<Enemy> enemiesInRange;
    public int towerType;
    private int damage;
    private float attackSpeed;
    private int range;
    private int radius;
    private int cost;
    private int x;
    private int y;

    private int lastShot;

    public Tower(int towerType, int x, int y) {
        this.towerType = towerType;
        damage = DAMAGE[towerType];
        attackSpeed = ATTACK_SPEED[towerType];
        range = RANGE[towerType];
        radius = (FIELD_SIZE / 2) + (FIELD_SIZE * range);
        cost = COST[towerType];
        this.x = x;
        this.y = y;
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
