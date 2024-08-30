package com.tdproject.towers;

import com.tdproject.enemies.Pathfinding;
import com.tdproject.gamestates.Playing;
import com.tdproject.graphics.ButtonPanel;
import com.tdproject.graphics.Text;
import com.tdproject.graphics.TextPanel;
import com.tdproject.inputs.MyEvent;
import com.tdproject.main.Square;
import com.tdproject.ui.BuildingButtons;
import com.tdproject.ui.Button;

import java.awt.Color;
import java.awt.Font;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import javax.vecmath.Vector2d;
import lombok.Getter;
import lombok.Setter;

import static com.tdproject.main.FieldParameters.*;
import static com.tdproject.towers.TowerParameters.COST;

public class TowerManager {

    private static TowerManager instance;
    private final int maxTowers = 50;

    private final ButtonPanel buildingButtons;
    private final ButtonPanel upgradeButtons;
    private final Map<UpgradeType, Text> upgradeLevels = new EnumMap<>(UpgradeType.class);
    private final TextPanel towerInfo;

    //private BufferedImage[] towerImgs = new BufferedImage[3];
    //private BufferedImage[] missileImgs = new BufferedImage[1];
    private final Tower[] towers = new Tower[maxTowers];
    private int towerNr;

    @Getter
    @Setter
    private Tower selectedTower;
    //private JButton cancelButton;

    @Setter
    private TowerManagerMode mode = TowerManagerMode.DEFAULT;
    //private boolean buildMode = false;

    private TowerManager() {
        //loadTowerImgs();
        //cancelButton = new JButton();
        buildingButtons = new ButtonPanel(1720, 490, 100, 980, BuildingButtons.buttons);
        Button[] upgradeButtons = new Button[UpgradeType.values().length];
        Text[] towerInfo = new Text[UpgradeType.values().length];
        int i = 0;
        for (UpgradeType upgradeType : UpgradeType.values()) {
            upgradeButtons[i] = new Button(
                    true,
                    upgradeType.imagePath,
                    b -> selectedTower.upgrade(upgradeType)
                    //u -> upgradeTower(upgradeType)
            );
            towerInfo[i] = new Text(upgradeType.toString(), 0, 0);
            i++;
        }
        this.upgradeButtons = new ButtonPanel(1720, 490, 100, 980, upgradeButtons);
        i = 0;
        for (UpgradeType upgradeType : UpgradeType.values()) {
            Vector2d position = upgradeButtons[i].getPosition();
            upgradeLevels.put(upgradeType, new Text(
                    "0",
                    15,
                    Color.RED,
                    Font.BOLD,
                    (int) position.x + (upgradeButtons[i].getSprite().getWidth() / 2),
                    (int) position.y
            ));
            i++;
        }
        this.towerInfo = new TextPanel(1845, 490, 150, 980, towerInfo);
    }

    public static TowerManager getInstance() {
        if (instance == null) {
            instance = new TowerManager();
        }
        return instance;
    }

    public void update(int u) {
        // tmp
        Playing.getInstance().getInfos()[3].setString("Mode: " + mode.toString());
        // ---
        if (selectedTower != null) {
            mode = TowerManagerMode.UPGRADING;
        }
        for (Tower t : towers) {
            if (t != null && t.active) {
                t.update(u);
            }
        }
        if (mode == TowerManagerMode.UPGRADING) {
            for (UpgradeType upgradeType : UpgradeType.values()) {
                int level = Objects.requireNonNullElse(selectedTower.getUpgrades().get(upgradeType), 0);
                upgradeLevels.get(upgradeType).setString(String.valueOf(level));
            }
        }
    }

    public void upgradeTower(UpgradeType upgradeType) {
        // TODO
        selectedTower.getUpgrades().computeIfPresent(upgradeType, (ut, i) -> i + 1);
        selectedTower.getUpgrades().putIfAbsent(upgradeType, 1);
    }

    public void enterBuildMode(int towerType) {
        mode = TowerManagerMode.BUILDING;
        //hide dropped main.tdproject.items
        //hide time buttons
//        ButtonManager.getInstance().setBuildButtons(false);
//        ButtonManager.getInstance().setCancelButton(true);
        BuildingButtons.setBuildMode(true);
        towers[towerNr] = new Tower(towerType);

    }

    public void cancelBuild() {
        //show dropped main.tdproject.items
        //show time buttons
//        ButtonManager.getInstance().setBuildButtons(true);
//        ButtonManager.getInstance().setCancelButton(false);
        BuildingButtons.setBuildMode(false);
        towers[towerNr] = null;
        mode = TowerManagerMode.DEFAULT;
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
        // TODO ghost tower is out of position

        //TODO: check if tower can be placed, (no enemy in the way,) impossible path or enemy getting caugth
        Square square = towers[towerNr].getSquare();
        if (square == null) {
            return;
        }
        if (Playing.getInstance().getMoney() < COST[towers[towerNr].getTowerType()]) {
            // Not enough money to build tower
            // TODO give visual feedback
            return;
        }
        if (!checkSquare(square)) {
            return;
        }
        Playing.getInstance().getCollisionMap()[square.getX()][square.getY()] = true;
        if (!Pathfinding.getInstance().buildDistanceField()) {
            //Can't build tower here
            Playing.getInstance().getCollisionMap()[square.getX()][square.getY()] = false;
            return;
        }
        Playing.getInstance().adjustMoney(-COST[towers[towerNr].getTowerType()]);
        towers[towerNr].initBounds();
        towers[towerNr].active = true;
        //show dropped main.tdproject.items
        //show time buttons
//        ButtonManager.getInstance().setBuildButtons(true);
//        ButtonManager.getInstance().setCancelButton(false);
        for (Button button : BuildingButtons.buttons) {
            button.setActive(true);
        }
        BuildingButtons.CANCEL_BUILDING_BUTTON.setActive(false);
        mode = TowerManagerMode.DEFAULT;
        towerNr++;
    }

    private boolean checkSquare(Square square) {
        int x = square.getX();
        int y = square.getY();
        if (x >= 0 && x < X_FIELDS && y >= 0 && y < Y_FIELDS)
        {
            return !Playing.getInstance().getCollisionMap()[x][y];
        }
        return false;
    }

    public void mouseReleased(MyEvent e) {
        switch (mode) {
            case DEFAULT -> {
                buildingButtons.mouseReleased(e);
                for (Tower tower : towers) {
                    tower.mouseReleased(e);
                }
            }
            case BUILDING -> {
                buildingButtons.mouseReleased(e);
                moveTower(new Vector2d(e.getX(), e.getY()));
                buildTower();
            }
            case UPGRADING -> {
                if (!upgradeButtons.mouseReleased(e)) {
                    selectedTower = null;
                    setMode(TowerManagerMode.DEFAULT);
                }
            }
        }
    }

    public void mouseMoved(MyEvent e) {
        switch (mode) {
            case DEFAULT -> buildingButtons.mouseMoved(e);
            case BUILDING -> {
                buildingButtons.mouseMoved(e);
                moveTower(new Vector2d(e.getX(), e.getY()));
            }
            case UPGRADING -> upgradeButtons.mouseMoved(e);
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
        for (Tower tower : towers) {
            if (tower != null && tower.visible) {
                tower.drawCentered(o);
                //g.drawImage(tower.getImg(), (int) tower.getPosition().x, (int) tower.getPosition().y, null);
                for (HomingMissile missile : tower.missiles) {
                    if (missile != null) {
                        missile.drawCentered(o);
                        //g.drawImage(missileImgs[0], (int) missile.getPosition().x + FIELD_SIZE / 2 - missileImgs[0].getWidth() / 2, (int) missile.getPosition().y + FIELD_SIZE / 2 - missileImgs[0].getHeight() / 2, null);
                    }
                }
            }
        }
        switch (mode) {
            case DEFAULT -> buildingButtons.draw(o);
            case BUILDING -> buildingButtons.draw(o);
            case UPGRADING -> {
                upgradeButtons.draw(o);
                towerInfo.draw(o);
                upgradeLevels.forEach((u, t) -> t.draw(o));
            }
        }
    }
}
