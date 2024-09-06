package com.tdproject.towers;

import com.tdproject.enemies.EnemyManager;
import com.tdproject.enemies.Enemy;
import com.tdproject.main.Game;
import com.tdproject.main.Square;

import com.tdproject.ui.Button;
import java.util.*;
import lombok.Getter;

import static com.tdproject.main.FieldParameters.FIELD_SIZE;
import static com.tdproject.towers.TowerParameters.*;


@Getter
public class Tower extends Button {

    //private BufferedImage img;
    private final List<Enemy> enemiesInRange = new LinkedList<>();

    private final Map<UpgradeType, Integer> upgrades = new EnumMap<>(UpgradeType.class);
    private final int towerType;
    private int damage;
    private double attackSpeed;
    //private float range;
    private int splash;
    private int slow;
    private int damageOverTime;
    private double areaOfEffectMultiplier;
    private int radius;
    private int cost;
    private Square square = new Square(-1, -1);
    //private Vector2d position = new Vector2d(0, 0);
    //public int x;
    //public int y;

    public Set<HomingMissile> missiles = new HashSet<>();
    private int lastShot;
    public boolean active = false;
    public boolean visible = false;

    public Tower(int towerType) {
        super(TOWER_1, null);
        action = b -> {
            TowerManager.getInstance().setSelectedTower(this);
            TowerManager.getInstance().setMode(TowerManagerMode.UPGRADING);
        };
        this.towerType = towerType;
        damage = DAMAGE[towerType];
        attackSpeed = ATTACK_SPEED[towerType];
        radius = (FIELD_SIZE / 2) + (FIELD_SIZE * RANGE[towerType]);
        cost = COST[towerType];
        //this.x = x;
        //this.y = y;
        //this.img = img;
        //loadSprite(Sprite.TOWER_1);
    }

    public void update(int u) {
        calculateEnemiesInRange();
        attemptShot(u);
        for (HomingMissile m : missiles) {
            if (m != null) {
                m.update();
            }
        }
    }

    public void upgrade(UpgradeType upgradeType) {
        // TODO: check if affordable
        upgrades.computeIfPresent(upgradeType, (ut, i) -> i + 1);
        upgrades.putIfAbsent(upgradeType, 1);
        updateStats();
        cost *= 2;
    }

    private void updateStats() {
        damage = DAMAGE[towerType] + upgrades.getOrDefault(UpgradeType.DAMAGE, 0);
        attackSpeed = ATTACK_SPEED[towerType] - (0.02F * upgrades.getOrDefault(UpgradeType.SPEED, 0));
        double range = RANGE[towerType] + (0.1F * upgrades.getOrDefault(UpgradeType.RANGE, 0));
        radius = (FIELD_SIZE / 2) + (int) (FIELD_SIZE * range);
        splash = upgrades.getOrDefault(UpgradeType.SPLASH, 0);
        slow = 10 * upgrades.getOrDefault(UpgradeType.SLOW, 0);
        damageOverTime = upgrades.getOrDefault(UpgradeType.DAMAGE_OVER_TIME, 0);
        areaOfEffectMultiplier = 0.1F * upgrades.getOrDefault(UpgradeType.AREA_OF_EFFECT, 0);
    }

    private void attemptShot(int u) {
        if (lastShot + attackSpeed * Game.getInstance().getUpsSet() <= u) {
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
            if (e.getDistanceToTarget() < furthest.getDistanceToTarget()) {
                furthest = e;
            }
        }
        shoot(furthest);
        return true;
    }

    private void shoot(Enemy e) {
        //homingMissile
        missiles.add(new HomingMissile(position, e, this, MISSILE_SPEED[towerType], damage));
    }

    private void calculateEnemiesInRange() {
        enemiesInRange.clear();
        for (Enemy e : EnemyManager.getInstance().getEnemies()) {
            if (e != null) {
                if (distanceToEnemy(e) < radius) {
                    enemiesInRange.add(e);
                }
            }
        }
    }

    private double distanceToEnemy(Enemy e) {
        return Math.sqrt(Math.pow((position.x - e.getPosition().x), 2) + Math.pow((position.y - e.getPosition().y), 2));
    }

    // Getters and setters
//    public BufferedImage getImg() {
//        return img;
//    }

    public int getTowerType() {
        return towerType;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

}
