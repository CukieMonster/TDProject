package enemies;

import items.Item;
import items.ItemParameters;
import main.Game;
import main.Square;
import main.Modifiers;

import javax.vecmath.Vector2d;

import java.util.Random;

import static enemies.EnemyParameters.*;
import static main.FieldParameters.*;

public class Enemy {

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
        maxHP = MAXHP[enemyType];
        HP = maxHP;
        speed = SPEEDS[SPEEDTIER[enemyType]];
        value = VALUE[enemyType];
        progress = PROGRESS[enemyType];
        square = spawn;
        distanceToTarget = Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()];
        Vector2d pos = spawn.squareToPosition();
        position = pos;
    }

    public void update() {
        moveInDirection();
    }

    public void damage(int amount) {
        HP -= amount;
        //TODO: healthbar
        if (HP <= 0) {
            Game.getInstance().adjustMoney(value);
            //GameObjectList.enemyManager.enemies.remove(this);
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
        if (Math.abs(position.x % FIELD_SIZE - X_OFFSET) < (speed * Game.getInstance().getGameSpeed()) && Math.abs(position.y % FIELD_SIZE - Y_OFFSET) < speed * Game.getInstance().getGameSpeed()) {
            xAxisLocked = yAxisLocked = false;
            if (Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()] == 1) {
                //base reached
                active = false;
                //GameObjectList.enemyManager.enemies.remove(this);
                return;
            }
            distanceToTarget = Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()];
            //decide direction
            if (square.getX() + 1 < X_FIELDS && Pathfinding.getInstance().getDistanceField()[square.getX() + 1][square.getY()] < Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()])
            {
                position.y = square.getY() * FIELD_SIZE +Y_OFFSET;
                yAxisLocked = true;
                position.x += speed * Game.getInstance().getGameSpeed();
            }
            else if (square.getY() + 1 < Y_FIELDS && Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY() + 1] < Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()])
            {
                position.x = square.getX() * FIELD_SIZE + X_OFFSET;
                xAxisLocked = true;
                position.y += speed * Game.getInstance().getGameSpeed();
            }
            else if (square.getY() > 0 && Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY() - 1] < Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()])
            {
                position.x = square.getX() * FIELD_SIZE + X_OFFSET;
                xAxisLocked = true;
                position.y -= speed * Game.getInstance().getGameSpeed();
            }
            else if (square.getX() > 0 && Pathfinding.getInstance().getDistanceField()[square.getX() - 1][square.getY()] < Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()])
            {
                position.y = square.getY() * FIELD_SIZE + Y_OFFSET;
                yAxisLocked = true;
                position.x -= speed * Game.getInstance().getGameSpeed();
            }
        }
        //enemy is moving between two fields
        else if (position.x % 64 != X_OFFSET || position.y % 64 != Y_OFFSET) {
            Square[] neighbors = Square.getNeighbors(position);
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
    }

    //calculates distanceToTarget in between fields
    private void distanceToTarget(Square[] neighbors) {

        if (!xAxisLocked) {
            //System.err.printf("%d\n", GameObjectList.pathfinding.distanceField[neighbors[0].x][neighbors[0].y]);
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
