package com.tdproject.towers;

import com.tdproject.enemies.Pathfinding;
import com.tdproject.gamestates.Playing;
import com.tdproject.inputs.MyEvent;
import com.tdproject.main.Game;
import com.tdproject.main.Square;
import com.tdproject.ui.ButtonManager;
import com.tdproject.ui.PlayingButtons;

import javax.vecmath.Vector2d;

import static com.tdproject.main.FieldParameters.*;
import static com.tdproject.towers.TowerParameters.COST;

public class TowerManager {

    private static TowerManager instance;
    private int maxTowers = 50;
    //private BufferedImage[] towerImgs = new BufferedImage[3];
    //private BufferedImage[] missileImgs = new BufferedImage[1];
    private Tower[] towers = new Tower[maxTowers];
    private int towerNr;
    //private JButton cancelButton;
    private boolean buildMode = false;

    private TowerManager() {
        //loadTowerImgs();
        //cancelButton = new JButton();
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
        //hide dropped main.tdproject.items
        //hide time buttons
//        ButtonManager.getInstance().setBuildButtons(false);
//        ButtonManager.getInstance().setCancelButton(true);
        Playing.getInstance().getButtonManager().setButton(ButtonManager.PlayingButtonID.BUILD_TOWER_1.ordinal(), false);
        Playing.getInstance().getButtonManager().setButton(ButtonManager.PlayingButtonID.CANCEL_BUILDING.ordinal(), true);
        towers[towerNr] = new Tower(towerType);
    }

    public void cancelBuild() {
        //show dropped main.tdproject.items
        //show time buttons
//        ButtonManager.getInstance().setBuildButtons(true);
//        ButtonManager.getInstance().setCancelButton(false);
        Playing.getInstance().getButtonManager().setButton(ButtonManager.PlayingButtonID.BUILD_TOWER_1.ordinal(), true);
        Playing.getInstance().getButtonManager().setButton(ButtonManager.PlayingButtonID.CANCEL_BUILDING.ordinal(), false);
        towers[towerNr] = null;
        buildMode = false;
    }

    private void moveTower(Vector2d mousePos) {
        towers[towerNr].setSquare(Square.positionToSquare(mousePos));
        if (checkSquare(towers[towerNr].getSquare())) {
            Vector2d pos = towers[towerNr].getSquare().squareToPosition();
            towers[towerNr].setPosition(pos);
            //main.tdproject.towers[towerNr].position.x = pos[0];
            //main.tdproject.towers[towerNr].position.y = pos[1];
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
        Playing.getInstance().getCollisionMap()[square.getX()][square.getY()] = true;
        if (Pathfinding.getInstance().buildDistanceField() == false) {
            //Can't build tower here
            Playing.getInstance().getCollisionMap()[square.getX()][square.getY()] = false;
            return;
        }
        Playing.getInstance().adjustMoney(-COST[towers[towerNr].getTowerType()]);
        towers[towerNr].active = true;
        //show dropped main.tdproject.items
        //show time buttons
//        ButtonManager.getInstance().setBuildButtons(true);
//        ButtonManager.getInstance().setCancelButton(false);
        Playing.getInstance().getButtonManager().setButton(ButtonManager.PlayingButtonID.BUILD_TOWER_1.ordinal(), true);
        Playing.getInstance().getButtonManager().setButton(ButtonManager.PlayingButtonID.CANCEL_BUILDING.ordinal(), false);
        buildMode = false;
        towerNr++;
    }

    boolean checkSquare(Square square) {
        int x = square.getX();
        int y = square.getY();
        if (x >= 0 && x < X_FIELDS && y >= 0 && y < Y_FIELDS)
        {
            return !Playing.getInstance().getCollisionMap()[x][y];
        }
        return false;
    }

    public void mouseReleased(MyEvent e) {
        if (buildMode) {
            moveTower(new Vector2d(e.getX(), e.getY()));
            buildTower();
        }
    }

    public void mouseMoved(MyEvent e) {
        if (buildMode) {
            moveTower(new Vector2d(e.getX(), e.getY()));
            //moveTower(e.getX(), e.getY());
        }
    }

//    private void loadTowerImgs() {
//        //tower 0
//        InputStream is = getClass().getResourceAsStream("/main.tdproject.towers/tower_blue_1.png");
//        try {
//            towerImgs[0] = ImageIO.read(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        is = getClass().getResourceAsStream("/main.tdproject.towers/homing_missile.png");
//        try {
//            missileImgs[0] = ImageIO.read(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void draw(Object o) {
        for (Tower t : towers) {
            if (t != null && t.visible) {
                t.draw(o, (int) t.getPosition().x, (int) t.getPosition().y);
                //g.drawImage(t.getImg(), (int) t.getPosition().x, (int) t.getPosition().y, null);
                for (HomingMissile m : t.missiles) {
                    if (m != null) {
                        m.drawCentered(o, (int) m.getPosition().x + FIELD_SIZE / 2, (int) m.getPosition().y + FIELD_SIZE / 2);
                        //g.drawImage(missileImgs[0], (int) m.getPosition().x + FIELD_SIZE / 2 - missileImgs[0].getWidth() / 2, (int) m.getPosition().y + FIELD_SIZE / 2 - missileImgs[0].getHeight() / 2, null);
                    }
                }
            }
        }
    }
}
