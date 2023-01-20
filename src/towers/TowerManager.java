package towers;

import enemies.Enemy;
import enemies.HomingMissile;
import enemies.Pathfinding;
import main.Game;
import main.Square;
import ui.ButtonManager;

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

    private static TowerManager instance;
    private int maxTowers = 50;
    private BufferedImage[] towerImgs = new BufferedImage[3];
    private BufferedImage[] missileImgs = new BufferedImage[1];
    private Tower[] towers = new Tower[maxTowers];
    private int towerNr;
    private JButton cancelButton;
    private boolean buildMode = false;

    private TowerManager() {
        loadTowerImgs();
        cancelButton = new JButton();
    }

    public static TowerManager getInstance() {
        if (instance == null) {
            instance = new TowerManager();
        }
        return instance;
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
        ButtonManager.getInstance().setBuildButtons(false);
        ButtonManager.getInstance().setCancelButton(true);
        towers[towerNr] = new Tower(towerType, towerImgs[towerType]);
    }

    public void cancelBuild() {
        //show dropped items
        //show time buttons
        ButtonManager.getInstance().setBuildButtons(true);
        ButtonManager.getInstance().setCancelButton(false);
        towers[towerNr] = null;
        buildMode = false;
    }

    private void moveTower(Vector2d mousePos) {
        towers[towerNr].setSquare(Square.positionToSquare(mousePos));
        if (checkSquare(towers[towerNr].getSquare())) {
            Vector2d pos = towers[towerNr].getSquare().squareToPosition();
            towers[towerNr].setPosition(pos);
            //towers[towerNr].position.x = pos[0];
            //towers[towerNr].position.y = pos[1];
            towers[towerNr].visible = true;
        } else {
            towers[towerNr].visible = false;
        }
    }

    private void buildTower() {
        //TODO: check if tower can be placed, (no enemy in the way,) impossible path or enemy getting caugth
        Square square = towers[towerNr].getSquare();
        if (square == null) {
            return;
        }
        if (!checkSquare(square)) {
            return;
        }
        Game.getInstance().getCollisionMap()[square.getX()][square.getY()] = true;
        if (Pathfinding.getInstance().buildDistanceField() == false) {
            //Can't build tower here
            Game.getInstance().getCollisionMap()[square.getX()][square.getY()] = false;
            return;
        }
        Game.getInstance().adjustMoney(-COST[towers[towerNr].getTowerType()]);
        towers[towerNr].active = true;
        //show dropped items
        //show time buttons
        ButtonManager.getInstance().setBuildButtons(true);
        ButtonManager.getInstance().setCancelButton(false);
        buildMode = false;
        towerNr++;
    }

    boolean checkSquare(Square square) {
        int x = square.getX();
        int y = square.getY();
        if (x >= 0 && x < X_FIELDS && y >= 0 && y < Y_FIELDS)
        {
            return !Game.getInstance().getCollisionMap()[x][y];
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
        InputStream is = getClass().getResourceAsStream("/towers/Tower_blue1.png");
        try {
            towerImgs[0] = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        is = getClass().getResourceAsStream("/towers/HomingMissile.png");
        try {
            missileImgs[0] = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        for (Tower t : towers) {
            if (t != null && t.visible) {
                g.drawImage(t.getImg(), (int) t.getPosition().x, (int) t.getPosition().y, null);
                for (HomingMissile m : t.missiles) {
                    if (m != null) {
                        g.drawImage(missileImgs[0], (int) m.getPosition().x + FIELD_SIZE / 2 - missileImgs[0].getWidth() / 2, (int) m.getPosition().y + FIELD_SIZE / 2 - missileImgs[0].getHeight() / 2, null);
                    }
                }
            }
        }
    }
}
