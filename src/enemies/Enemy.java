package enemies;

import main.Game;
import main.Square;

import static enemies.EnemyParameters.*;
import static enemies.Pathfinding.distanceField;
import static main.FieldParameters.*;

public class Enemy {

    public float distanceToTarget;

    private int enemyType;
    private int maxHP;
    private int HP;
    private float speed;
    private int value;
    private int progress;
    private boolean active = true;
    private Square square;
    public int x;
    public int y;
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
        distanceToTarget = distanceField[square.x][square.y];
        int position[] = spawn.squareToPosition();
        x = position[0];
        y = position[1];
    }

    public void update() {
        moveInDirection();
    }

    private void moveInDirection() {
        square = main.Square.positionToSquare(x, y);
        //enemy is in the center of a field
        if (Math.abs(x % FIELD_SIZE) < (speed * Game.gameSpeed) && Math.abs(y % FIELD_SIZE) < speed * Game.gameSpeed) {
            xAxisLocked = yAxisLocked = false;
            if (distanceField[square.x][square.y] == 1) {
                //base reached
                active = false;
                return;
            }
            distanceToTarget = distanceField[square.x][square.y];
            //decide direction
            if (square.x + 1 < X_FIELDS && distanceField[square.x + 1][square.y] < distanceField[square.x][square.y])
            {
                y = square.y * FIELD_SIZE;
                yAxisLocked = true;
                x += speed * Game.gameSpeed;
            }
            else if (square.y + 1 < Y_FIELDS && distanceField[square.x][square.y + 1] < distanceField[square.x][square.y])
            {
                x = square.x * FIELD_SIZE;
                xAxisLocked = true;
                y += speed * Game.gameSpeed;
            }
            else if (square.y > 0 && distanceField[square.x][square.y - 1] < distanceField[square.x][square.y])
            {
                x = square.x * FIELD_SIZE;
                xAxisLocked = true;
                y -= speed * Game.gameSpeed;
            }
            else if (square.x > 0 && distanceField[square.x - 1][square.y] < distanceField[square.x][square.y])
            {
                y = square.y * FIELD_SIZE;
                yAxisLocked = true;
                x -= speed * Game.gameSpeed;
            }
        }
        //enemy is moving between two fields
        if (x % 64 != 0 || y % 64 != 0) {
            Square[] neighbors = Square.getNeighbors(x, y);
            distanceToTarget(neighbors);

            //decide direction
            if (!xAxisLocked) {
                if (distanceField[neighbors[0].x][neighbors[0].y] < distanceField[neighbors[1].x][neighbors[1].y]) {
                    x += (neighbors[0].x - neighbors[1].x) * speed * Game.gameSpeed;
                }
                else if (distanceField[neighbors[0].x][neighbors[0].y] > distanceField[neighbors[1].x][neighbors[1].y]) {
                    x += (neighbors[1].x - neighbors[0].x) * speed * Game.gameSpeed;
                }
            }
            if (!yAxisLocked) {
                if (distanceField[neighbors[0].x][neighbors[0].y] < distanceField[neighbors[1].x][neighbors[1].y]) {
                    y += (neighbors[0].y - neighbors[1].y) * speed * Game.gameSpeed;
                }
                else if (distanceField[neighbors[0].x][neighbors[0].y] > distanceField[neighbors[1].x][neighbors[1].y]) {
                    y += (neighbors[1].y - neighbors[0].y) * speed * Game.gameSpeed;
                }
            }
        }
    }

    //calculates distanceToTarget in between fields
    private void distanceToTarget(Square[] neighbors) {
        if (!xAxisLocked) {
            distanceToTarget = distanceField[neighbors[0].x][neighbors[0].y] * (Math.abs(neighbors[1].x * FIELD_SIZE - x) / (float)FIELD_SIZE) + distanceField[neighbors[1].x][neighbors[1].y] * (Math.abs(neighbors[0].x * FIELD_SIZE - x) / (float)FIELD_SIZE);
        }
        if (!yAxisLocked) {
            distanceToTarget = distanceField[neighbors[0].x][neighbors[0].y] * (Math.abs(neighbors[1].y * FIELD_SIZE - y) / (float)FIELD_SIZE) + distanceField[neighbors[1].x][neighbors[1].y] * (Math.abs(neighbors[0].y * FIELD_SIZE - y) / (float)FIELD_SIZE);
        }
    }
}
