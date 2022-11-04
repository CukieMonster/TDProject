package towers;

import enemies.Enemy;
import enemies.HomingMissile;
import main.Game;
import main.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.vecmath.Vector2d;
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
    private BufferedImage[] missileImgs = new BufferedImage[1];
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
        for (Tower t : towers) {
            if (t != null && t.active) {
                t.update(u);
            }
        }
    }

    public void enterBuildMode(int towerType) {
        buildMode = true;
        //hide dropped items
        //hide time buttons
        game.getButtonManager().setBuildButtons(false);
        game.getButtonManager().setCancelButton(true);
        towers[towerNr] = new Tower(towerType, towerImgs[towerType], game.getEnemyManager());
    }

    public void cancelBuild() {
        //show dropped items
        //show time buttons
        game.getButtonManager().setBuildButtons(true);
        game.getButtonManager().setCancelButton(false);
        towers[towerNr] = null;
        buildMode = false;
    }

    private void moveTower(Vector2d mousePos) {
        towers[towerNr].square = Square.positionToSquare(mousePos);
        if (checkSquare(towers[towerNr].square)) {
            Vector2d pos = towers[towerNr].square.squareToPosition();
            towers[towerNr].position = pos;
            //towers[towerNr].position.x = pos[0];
            //towers[towerNr].position.y = pos[1];
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
        if (!checkSquare(square)) {
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
            moveTower(new Vector2d(e.getX(), e.getY()));
            //moveTower(e.getX(), e.getY());
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
        is = getClass().getResourceAsStream("/HomingMissile.png");
        try {
            missileImgs[0] = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        for (Tower t : towers) {
            if (t != null && t.visible) {
                g.drawImage(t.img, (int) t.position.x, (int) t.position.y, null);
                for (HomingMissile m : t.missiles) {
                    if (m != null) {
                        g.drawImage(missileImgs[0], (int) m.position.x + FIELD_SIZE / 2 - missileImgs[0].getWidth() / 2, (int) m.position.y + FIELD_SIZE / 2 - missileImgs[0].getHeight() / 2, null);
                    }
                }
            }
        }
    }
}
