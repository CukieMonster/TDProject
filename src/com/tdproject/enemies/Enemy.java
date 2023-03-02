package com.tdproject.enemies;

import com.tdproject.graphics.Sprite;
import com.tdproject.items.Item;
import com.tdproject.items.ItemParameters;
import com.tdproject.main.Game;
import com.tdproject.main.Modifiers;
import com.tdproject.main.Square;

import javax.vecmath.Vector2d;

import java.util.Random;

import static com.tdproject.main.FieldParameters.*;

public class Enemy extends Sprite {

    private double distanceToTarget;

    private int enemyType, maxHP, HP, value, progress, index;
    private float speed;


    private boolean active = true;
    private Square square;
    private Vector2d position;
    //public int x;
    //public int y;
    private boolean xAxisLocked = false;
    private boolean yAxisLocked = false;
    private Random random = new Random();

    public Enemy(Square spawn, int enemyType) {
        this.enemyType = enemyType;
        maxHP = EnemyParameters.MAX_HP[enemyType];
        HP = maxHP;
        speed = EnemyParameters.SPEEDS[EnemyParameters.SPEED_TIER[enemyType]];
        value = EnemyParameters.VALUE[enemyType];
        progress = EnemyParameters.PROGRESS[enemyType];
        square = spawn;
        distanceToTarget = Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()];
        Vector2d pos = spawn.squareToPosition();
        position = pos;
        loadSprite(Type.ENEMY, this.enemyType);
    }

    public void update() {
        moveInDirection();
    }

    public void damage(int amount) {
        HP -= amount;
        //TODO: healthbar
        if (HP <= 0) {
            Game.getInstance().adjustMoney(value);
            active = false;

            if (random.nextInt(100) < Modifiers.getDroprate() * progress) {
                Item newItem;
                int rar = random.nextInt(100);
                if (rar < Modifiers.getLegendaryDroprate()) {
                    newItem = new Item(5, ItemParameters.Rarity.Legendary);
                }
                else if (rar < Modifiers.getRareDroprate()) {
                    newItem = new Item(5, ItemParameters.Rarity.Rare);
                }
                else {
                    newItem = new Item(5, ItemParameters.Rarity.Common);
                }
                dropItem(newItem);
            }
        }
    }

    void dropItem(Item newItem) {
        Game.getInstance().getDroppedItems().add(newItem);
    }

    private void moveInDirection() {
        square = Square.positionToSquare(position);
        //enemy is in the center of a field
        if (Math.abs((position.x - X_OFFSET) % FIELD_SIZE) < (speed * Game.getInstance().getGameSpeed()) && Math.abs((position.y - Y_OFFSET) % FIELD_SIZE) < speed * Game.getInstance().getGameSpeed()) {
            System.out.printf("x: %f, y: %f%n", position.x, position.y);
            xAxisLocked = yAxisLocked = false;
            if (Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()] == 1) {
                //base reached
                active = false;
                return;
            }
            distanceToTarget = Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()];
            //decide direction
            if (square.getX() + 1 < X_FIELDS && Pathfinding.getInstance().getDistanceField()[square.getX() + 1][square.getY()] < Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()])
            {
                roundYValue();
                yAxisLocked = true;
                position.x += speed * Game.getInstance().getGameSpeed();
            }
            else if (square.getY() + 1 < Y_FIELDS && Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY() + 1] < Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()])
            {
                roundXValue();
                xAxisLocked = true;
                position.y += speed * Game.getInstance().getGameSpeed();
            }
            else if (square.getY() > 0 && Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY() - 1] < Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()])
            {
                roundXValue();
                xAxisLocked = true;
                position.y -= speed * Game.getInstance().getGameSpeed();
            }
            else if (square.getX() > 0 && Pathfinding.getInstance().getDistanceField()[square.getX() - 1][square.getY()] < Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()])
            {
                roundYValue();
                yAxisLocked = true;
                position.x -= speed * Game.getInstance().getGameSpeed();
            }
        }
        //enemy is moving between two fields
        else if (position.x % FIELD_SIZE != X_OFFSET || position.y % FIELD_SIZE != Y_OFFSET) {
            Square[] neighbors = Square.getNeighbors(position);
            if (neighbors[0].getX() == 25 || neighbors[1].getX() == 25) {

                System.out.printf("OutOfBoundsError: %s 1: X: %d; 2: X: %d; XPos: %f; Speed: %f; GameSpeed: %d%n", this, neighbors[0].getX(), neighbors[1].getX(), position.x, speed, Game.getInstance().getGameSpeed());
            }
            distanceToTarget(neighbors);

            //decide direction
            if (!xAxisLocked) {
                if (Pathfinding.getInstance().getDistanceField()[neighbors[0].getX()][neighbors[0].getY()] < Pathfinding.getInstance().getDistanceField()[neighbors[1].getX()][neighbors[1].getY()]) {
                    position.x += (neighbors[0].getX() - neighbors[1].getX()) * speed * Game.getInstance().getGameSpeed();
                }
                else if (Pathfinding.getInstance().getDistanceField()[neighbors[0].getX()][neighbors[0].getY()] > Pathfinding.getInstance().getDistanceField()[neighbors[1].getX()][neighbors[1].getY()]) {
                    position.x += (neighbors[1].getX() - neighbors[0].getX()) * speed * Game.getInstance().getGameSpeed();
                }
            }
            if (!yAxisLocked) {
                if (Pathfinding.getInstance().getDistanceField()[neighbors[0].getX()][neighbors[0].getY()] < Pathfinding.getInstance().getDistanceField()[neighbors[1].getX()][neighbors[1].getY()]) {
                    position.y += (neighbors[0].getY() - neighbors[1].getY()) * speed * Game.getInstance().getGameSpeed();
                }
                else if (Pathfinding.getInstance().getDistanceField()[neighbors[0].getX()][neighbors[0].getY()] > Pathfinding.getInstance().getDistanceField()[neighbors[1].getX()][neighbors[1].getY()]) {
                    position.y += (neighbors[1].getY() - neighbors[0].getY()) * speed * Game.getInstance().getGameSpeed();
                }
            }
        }
        System.out.println(this + " x: " + position.x + " y: " + position.y);
    }

    //calculates distanceToTarget in between fields
    private void distanceToTarget(Square[] neighbors) {

        if (!xAxisLocked) {
            distanceToTarget = Pathfinding.getInstance().getDistanceField()[neighbors[0].getX()][neighbors[0].getY()]
                    * (Math.abs(neighbors[1].getX() * FIELD_SIZE - position.x) / (float)FIELD_SIZE)
                    + Pathfinding.getInstance().getDistanceField()[neighbors[1].getX()][neighbors[1].getY()]
                    * (Math.abs(neighbors[0].getX() * FIELD_SIZE - position.x) / (float)FIELD_SIZE);
        }
        if (!yAxisLocked) {
            distanceToTarget = Pathfinding.getInstance().getDistanceField()[neighbors[0].getX()][neighbors[0].getY()]
                    * (Math.abs(neighbors[1].getY() * FIELD_SIZE - position.y) / (float)FIELD_SIZE)
                    + Pathfinding.getInstance().getDistanceField()[neighbors[1].getX()][neighbors[1].getY()]
                    * (Math.abs(neighbors[0].getY() * FIELD_SIZE - position.y) / (float)FIELD_SIZE);
        }
    }

    private void roundXValue() {
        double delta = position.x - (square.getX() * FIELD_SIZE + X_OFFSET);
        if (delta < FIELD_SIZE / 2) {
            position.x -= delta;
        }
        else {
            position.x += FIELD_SIZE - delta;
        }
    }

    private void roundYValue() {
        double delta = position.y - (square.getY() * FIELD_SIZE + Y_OFFSET);
        if (delta < FIELD_SIZE / 2) {
            position.y -= delta;
        }
        else {
            position.y += FIELD_SIZE - delta;
        }
    }

    // Setters and getters
    public double getDistanceToTarget() {
        return distanceToTarget;
    }

    public boolean isActive() {
        return active;
    }

    public Vector2d getPosition() {
        return position;
    }
}
