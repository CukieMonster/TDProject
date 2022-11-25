package enemies;

import main.Game;
import main.GameObjectList;
import main.Square;
import ui.BUTTONS;

import javax.vecmath.Vector2d;

import static enemies.EnemyParameters.*;
import static main.FieldParameters.*;

public class Enemy {

    public double distanceToTarget;

    private int enemyType, maxHP, HP, value, progress, index;
    private float speed;
    public boolean active = true;
    private Square square;
    public Vector2d position;
    //public int x;
    //public int y;
    private boolean xAxisLocked = false;
    private boolean yAxisLocked = false;

    public Enemy(Square spawn, int enemyType) {
        this.enemyType = enemyType;
        maxHP = MAXHP[enemyType];
        HP = maxHP;
        speed = SPEEDS[SPEEDTIER[enemyType]];
        value = VALUE[enemyType];
        progress = PROGRESS[enemyType];
        square = spawn;
        distanceToTarget = GameObjectList.pathfinding.distanceField[square.x][square.y];
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
            GameObjectList.game.adjustMoney(value);
            //GameObjectList.enemyManager.enemies.remove(this);
            active = false;
        }
    }

    private void moveInDirection() {
        square = Square.positionToSquare(position);
        //enemy is in the center of a field
        if (Math.abs(position.x % FIELD_SIZE - X_OFFSET) < (speed * Game.gameSpeed) && Math.abs(position.y % FIELD_SIZE - Y_OFFSET) < speed * Game.gameSpeed) {
            xAxisLocked = yAxisLocked = false;
            if (GameObjectList.pathfinding.distanceField[square.x][square.y] == 1) {
                //base reached
                active = false;
                //GameObjectList.enemyManager.enemies.remove(this);
                return;
            }
            distanceToTarget = GameObjectList.pathfinding.distanceField[square.x][square.y];
            //decide direction
            if (square.x + 1 < X_FIELDS && GameObjectList.pathfinding.distanceField[square.x + 1][square.y] < GameObjectList.pathfinding.distanceField[square.x][square.y])
            {
                position.y = square.y * FIELD_SIZE +Y_OFFSET;
                yAxisLocked = true;
                position.x += speed * Game.gameSpeed;
            }
            else if (square.y + 1 < Y_FIELDS && GameObjectList.pathfinding.distanceField[square.x][square.y + 1] < GameObjectList.pathfinding.distanceField[square.x][square.y])
            {
                position.x = square.x * FIELD_SIZE + X_OFFSET;
                xAxisLocked = true;
                position.y += speed * Game.gameSpeed;
            }
            else if (square.y > 0 && GameObjectList.pathfinding.distanceField[square.x][square.y - 1] < GameObjectList.pathfinding.distanceField[square.x][square.y])
            {
                position.x = square.x * FIELD_SIZE + X_OFFSET;
                xAxisLocked = true;
                position.y -= speed * Game.gameSpeed;
            }
            else if (square.x > 0 && GameObjectList.pathfinding.distanceField[square.x - 1][square.y] < GameObjectList.pathfinding.distanceField[square.x][square.y])
            {
                position.y = square.y * FIELD_SIZE + Y_OFFSET;
                yAxisLocked = true;
                position.x -= speed * Game.gameSpeed;
            }
        }
        //enemy is moving between two fields
        else if (position.x % 64 != X_OFFSET || position.y % 64 != Y_OFFSET) {
            Square[] neighbors = Square.getNeighbors(position);
            distanceToTarget(neighbors);

            //decide direction
            if (!xAxisLocked) {
                if (GameObjectList.pathfinding.distanceField[neighbors[0].x][neighbors[0].y] < GameObjectList.pathfinding.distanceField[neighbors[1].x][neighbors[1].y]) {
                    position.x += (neighbors[0].x - neighbors[1].x) * speed * Game.gameSpeed;
                }
                else if (GameObjectList.pathfinding.distanceField[neighbors[0].x][neighbors[0].y] > GameObjectList.pathfinding.distanceField[neighbors[1].x][neighbors[1].y]) {
                    position.x += (neighbors[1].x - neighbors[0].x) * speed * Game.gameSpeed;
                }
            }
            if (!yAxisLocked) {
                if (GameObjectList.pathfinding.distanceField[neighbors[0].x][neighbors[0].y] < GameObjectList.pathfinding.distanceField[neighbors[1].x][neighbors[1].y]) {
                    position.y += (neighbors[0].y - neighbors[1].y) * speed * Game.gameSpeed;
                }
                else if (GameObjectList.pathfinding.distanceField[neighbors[0].x][neighbors[0].y] > GameObjectList.pathfinding.distanceField[neighbors[1].x][neighbors[1].y]) {
                    position.y += (neighbors[1].y - neighbors[0].y) * speed * Game.gameSpeed;
                }
            }
        }
    }

    //calculates distanceToTarget in between fields
    private void distanceToTarget(Square[] neighbors) {

        if (!xAxisLocked) {
            //System.err.printf("%d\n", GameObjectList.pathfinding.distanceField[neighbors[0].x][neighbors[0].y]);
            distanceToTarget = GameObjectList.pathfinding.distanceField[neighbors[0].x][neighbors[0].y]
                    * (Math.abs(neighbors[1].x * FIELD_SIZE - position.x) / (float)FIELD_SIZE)
                    + GameObjectList.pathfinding.distanceField[neighbors[1].x][neighbors[1].y]
                    * (Math.abs(neighbors[0].x * FIELD_SIZE - position.x) / (float)FIELD_SIZE);
        }
        if (!yAxisLocked) {
            distanceToTarget = GameObjectList.pathfinding.distanceField[neighbors[0].x][neighbors[0].y]
                    * (Math.abs(neighbors[1].y * FIELD_SIZE - position.y) / (float)FIELD_SIZE)
                    + GameObjectList.pathfinding.distanceField[neighbors[1].x][neighbors[1].y]
                    * (Math.abs(neighbors[0].y * FIELD_SIZE - position.y) / (float)FIELD_SIZE);
        }
    }
}
