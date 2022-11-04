package enemies;

import main.Game;
import main.Square;

import javax.vecmath.Vector2d;

import static enemies.EnemyParameters.*;
import static main.FieldParameters.*;

public class Enemy {

    public double distanceToTarget;

    private int enemyType;
    private int maxHP;
    private int HP;
    private float speed;
    private int value;
    private int progress;
    public boolean active = true;
    private Square square;
    public Vector2d position;
    //public int x;
    //public int y;
    private boolean xAxisLocked = false;
    private boolean yAxisLocked = false;
    private Pathfinding pathfinding;

    public Enemy(Square spawn, int enemyType, Pathfinding p) {
        pathfinding = p;
        this.enemyType = enemyType;
        maxHP = MAXHP[enemyType];
        HP = maxHP;
        speed = SPEEDS[SPEEDTIER[enemyType]];
        value = VALUE[enemyType];
        progress = PROGRESS[enemyType];
        square = spawn;
        distanceToTarget = pathfinding.distanceField[square.x][square.y];
        Vector2d pos = spawn.squareToPosition();
        position = pos;
    }

    public void update() {
        moveInDirection();
    }

    private void moveInDirection() {
        square = main.Square.positionToSquare(position);
        //enemy is in the center of a field
        if (Math.abs(position.x % FIELD_SIZE - X_OFFSET) < (speed * Game.gameSpeed) && Math.abs(position.y % FIELD_SIZE - Y_OFFSET) < speed * Game.gameSpeed) {
            xAxisLocked = yAxisLocked = false;
            if (pathfinding.distanceField[square.x][square.y] == 1) {
                //base reached
                active = false;
                return;
            }
            distanceToTarget = pathfinding.distanceField[square.x][square.y];
            //decide direction
            if (square.x + 1 < X_FIELDS && pathfinding.distanceField[square.x + 1][square.y] < pathfinding.distanceField[square.x][square.y])
            {
                position.y = square.y * FIELD_SIZE +Y_OFFSET;
                yAxisLocked = true;
                position.x += speed * Game.gameSpeed;
            }
            else if (square.y + 1 < Y_FIELDS && pathfinding.distanceField[square.x][square.y + 1] < pathfinding.distanceField[square.x][square.y])
            {
                position.x = square.x * FIELD_SIZE + X_OFFSET;
                xAxisLocked = true;
                position.y += speed * Game.gameSpeed;
            }
            else if (square.y > 0 && pathfinding.distanceField[square.x][square.y - 1] < pathfinding.distanceField[square.x][square.y])
            {
                position.x = square.x * FIELD_SIZE + X_OFFSET;
                xAxisLocked = true;
                position.y -= speed * Game.gameSpeed;
            }
            else if (square.x > 0 && pathfinding.distanceField[square.x - 1][square.y] < pathfinding.distanceField[square.x][square.y])
            {
                position.y = square.y * FIELD_SIZE + Y_OFFSET;
                yAxisLocked = true;
                position.x -= speed * Game.gameSpeed;
            }
        }
        //enemy is moving between two fields
        if (position.x % 64 != X_OFFSET || position.y % 64 != Y_OFFSET) {
            Square[] neighbors = Square.getNeighbors(position);
            distanceToTarget(neighbors);

            //decide direction
            if (!xAxisLocked) {
                if (pathfinding.distanceField[neighbors[0].x][neighbors[0].y] < pathfinding.distanceField[neighbors[1].x][neighbors[1].y]) {
                    position.x += (neighbors[0].x - neighbors[1].x) * speed * Game.gameSpeed;
                }
                else if (pathfinding.distanceField[neighbors[0].x][neighbors[0].y] > pathfinding.distanceField[neighbors[1].x][neighbors[1].y]) {
                    position.x += (neighbors[1].x - neighbors[0].x) * speed * Game.gameSpeed;
                }
            }
            if (!yAxisLocked) {
                if (pathfinding.distanceField[neighbors[0].x][neighbors[0].y] < pathfinding.distanceField[neighbors[1].x][neighbors[1].y]) {
                    position.y += (neighbors[0].y - neighbors[1].y) * speed * Game.gameSpeed;
                }
                else if (pathfinding.distanceField[neighbors[0].x][neighbors[0].y] > pathfinding.distanceField[neighbors[1].x][neighbors[1].y]) {
                    position.y += (neighbors[1].y - neighbors[0].y) * speed * Game.gameSpeed;
                }
            }
        }
    }

    //calculates distanceToTarget in between fields
    private void distanceToTarget(Square[] neighbors) {
        if (!xAxisLocked) {
            distanceToTarget = pathfinding.distanceField[neighbors[0].x][neighbors[0].y] * (Math.abs(neighbors[1].x * FIELD_SIZE - position.x) / (float)FIELD_SIZE) + pathfinding.distanceField[neighbors[1].x][neighbors[1].y] * (Math.abs(neighbors[0].x * FIELD_SIZE - position.x) / (float)FIELD_SIZE);
        }
        if (!yAxisLocked) {
            distanceToTarget = pathfinding.distanceField[neighbors[0].x][neighbors[0].y] * (Math.abs(neighbors[1].y * FIELD_SIZE - position.y) / (float)FIELD_SIZE) + pathfinding.distanceField[neighbors[1].x][neighbors[1].y] * (Math.abs(neighbors[0].y * FIELD_SIZE - position.y) / (float)FIELD_SIZE);
        }
    }
}
