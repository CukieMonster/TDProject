package com.tdproject.towers;

import com.tdproject.enemies.EnemyManager;
import com.tdproject.graphics.Sprite;
import com.tdproject.enemies.Enemy;
import com.tdproject.main.Game;
import com.tdproject.main.Square;

import javax.vecmath.Vector2d;
import java.util.*;

import static com.tdproject.main.FieldParameters.FIELD_SIZE;
import static com.tdproject.towers.TowerParameters.*;

public class Tower extends Sprite {

    //private BufferedImage img;
    private List<Enemy> enemiesInRange = new LinkedList<>();
    private int towerType;
    private int damage;
    private float attackSpeed;
    private int range;
    private int radius;
    private int cost;
    private Square square = new Square(-1, -1);
    private Vector2d position = new Vector2d(0, 0);
    //public int x;
    //public int y;

    public Set<HomingMissile> missiles = new HashSet<>();
    private int lastShot;
    public boolean active = false;
    public boolean visible = false;

    public Tower(int towerType) {
        this.towerType = towerType;
        damage = DAMAGE[towerType];
        attackSpeed = ATTACK_SPEED[towerType];
        range = RANGE[towerType];
        radius = (FIELD_SIZE / 2) + (FIELD_SIZE * range);
        cost = COST[towerType];
        //this.x = x;
        //this.y = y;
        //this.img = img;
        loadSprite(Type.TOWER, "" + this.towerType);
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
        missiles.add(new HomingMissile(position, e, this, MISSILESPEED[towerType], damage));
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

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }
}
