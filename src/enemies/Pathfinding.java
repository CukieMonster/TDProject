package enemies;

import main.Square;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static main.FieldParameters.*;

public final class Pathfinding {

    public static int[][] distanceField = new int[X_FIELDS][Y_FIELDS];
    private static List<Square> shortestPath = new LinkedList<>();
    private static int shortestPathLength;
    private static final int blockedField = X_FIELDS * Y_FIELDS;
    private static Square dest = new Square(24, 8);

    public static boolean buildDistanceField() {
        int[][] newField = new int[X_FIELDS][Y_FIELDS];
        for (int x = 0; x < X_FIELDS; x++) {
            for (int y = 0; y < Y_FIELDS; y++) {
                if (main.Game.collisionMap[x][y] == true) {
                    newField[x][y] = blockedField;
                }
            }
        }

        Queue<Square> frontier = new ConcurrentLinkedQueue<>();
        frontier.add(dest);
        newField[dest.x][dest.y] = 1;

        while (!frontier.isEmpty()) {
            Square current = frontier.remove();
            if (current.x + 1 < X_FIELDS && newField[current.x + 1][current.y] == 0) {
                Square next = new Square(current.x + 1, current.y);
                newField[next.x][next.y] = newField[current.x][current.y] + 1;
                frontier.add(next);
            }
            if (current.y + 1 < Y_FIELDS && newField[current.x][current.y + 1] == 0) {
                Square next = new Square(current.x, current.y + 1);
                newField[next.x][next.y] = newField[current.x][current.y] + 1;
                frontier.add(next);
            }
            if (current.y - 1 >= 0 && newField[current.x][current.y - 1] == 0) {
                Square next = new Square(current.x, current.y - 1);
                newField[next.x][next.y] = newField[current.x][current.y] + 1;
                frontier.add(next);
            }
            if (current.x - 1 >= 0 && newField[current.x - 1][current.y] == 0) {
                Square next = new Square(current.x - 1, current.y);
                newField[next.x][next.y] = newField[current.x][current.y] + 1;
                frontier.add(next);
            }
        }

        //check if distanceField is valid
        for (int x = 0; x < X_FIELDS; x++) {
            for (int y = 0; y < Y_FIELDS; y++) {
                if (newField[x][y] == 0) {
                    return false;
                }
            }
        }
        distanceField = newField;
        return true;
    }
}
