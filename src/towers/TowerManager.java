package towers;

import enemies.Enemy;
import main.Game;
import main.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static main.FieldParameters.*;
import static towers.TowerParameters.COST;

public class TowerManager {

    private Game game;
    private int maxTowers = 50;
    private BufferedImage[] towerImgs = new BufferedImage[3];
    public Tower[] towers = new Tower[maxTowers];
    private int towerNr;
    private JButton cancelButton;
    private boolean buildMode = false;

    public TowerManager(Game g) {
        game = g;
        loadTowerImgs();
        cancelButton = new JButton();
    }

    public void update(int u) {

    }

    public void enterBuildMode(int towerType) {
        buildMode = true;
        //hide dropped items
        //hide time buttons
        game.getButtonManager().setBuildButtons(false);
        game.getButtonManager().setCancelButton(true);
        towers[towerNr] = new Tower(towerType, 0, 0, towerImgs[towerType]);
    }

    public void cancelBuild() {
        //show dropped items
        //show time buttons
        game.getButtonManager().setBuildButtons(true);
        game.getButtonManager().setCancelButton(false);
        towers[towerNr] = null;
        buildMode = false;
    }

    private void moveTower(int x, int y) {
        towers[towerNr].square = Square.positionToSquare(x, y);
        if (checkSquare(towers[towerNr].square)) {
            int[] pos = towers[towerNr].square.squareToPosition();
            towers[towerNr].x = pos[0];
            towers[towerNr].y = pos[1];
            towers[towerNr].visible = true;
        } else {
            towers[towerNr].visible = false;
        }
    }

    private void buildTower() {
        //TODO: check if tower can be placed, (no enemy in the way,) impossible path or enemy getting caugth
        Square square = towers[towerNr].square;
        if (square == null) {
            return;
        }
        game.collisionMap[square.x][square.y] = true;
        if (game.getPathfinding().buildDistanceField() == false) {
            //Can't build tower here
            game.collisionMap[square.x][square.y] = false;
            return;
        }
        game.adjustMoney(-COST[towers[towerNr].towerType]);
        towers[towerNr].active = true;
        //show dropped items
        //show time buttons
        game.getButtonManager().setBuildButtons(true);
        game.getButtonManager().setCancelButton(false);
        buildMode = false;
        towerNr++;
    }

    boolean checkSquare(Square square) {
        int x = square.x;
        int y = square.y;
        if (x >= 0 && x < X_FIELDS && y >= 0 && y < Y_FIELDS)
        {
            return !game.collisionMap[x][y];
        }
        return false;
    }

    public void mouseReleased(MouseEvent e) {
        if (buildMode) {
            buildTower();
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (buildMode) {
            moveTower(e.getX(), e.getY());
        }
    }

    private void loadTowerImgs() {
        //tower 0
        InputStream is = getClass().getResourceAsStream("/Tower_blue1.png");
        try {
            towerImgs[0] = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        for (Tower t : towers) {
            if (t != null && t.visible) {
                g.drawImage(t.img, t.x, t.y, null);
            }
        }
    }
}
