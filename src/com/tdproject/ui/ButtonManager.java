package com.tdproject.ui;

import com.tdproject.enemies.EnemyManager;
import com.tdproject.inputs.MyEvent;
import com.tdproject.main.Game;
import com.tdproject.towers.TowerManager;

//enum Buttons {CANCEL_BUILDING, BUILD_TOWER_1, FAST_FORW_BUTTON, SKIP_BUTTON}

public class ButtonManager {

    private static ButtonManager instance;
    private int buttonNr = 4;
    private Button[] buttons = new Button[buttonNr];

    private ButtonManager() {
        buttons[0] = new Button(Buttons.CANCEL_BUILDING, 1800, 900);
        buttons[1] = new Button(Buttons.BUILD_TOWER_1, 1800, 100);
        buttons[2] = new Button(Buttons.FAST_FORW_BUTTON, 1600, 900);
        buttons[3] = new Button(Buttons.SKIP_BUTTON, 1800, 900);
        //loadButtonImgs();
        buttons[1].setActive(true);
        buttons[2].setActive(true);
    }

    public static ButtonManager getInstance() {
        if (instance == null) {
            instance = new ButtonManager();
        }
        return instance;
    }

    private void buttonAction(Buttons button) {
        switch (button) {
            case CANCEL_BUILDING:
                TowerManager.getInstance().cancelBuild();
                break;
            case BUILD_TOWER_1:
                TowerManager.getInstance().enterBuildMode(0);
                break;
            case FAST_FORW_BUTTON:
                Game.getInstance().changeGamespeed();
                break;
            case SKIP_BUTTON:
                EnemyManager.getInstance().spawnWave();
                buttons[Buttons.SKIP_BUTTON.ordinal()].setActive(false);
        }
    }

    public void setCancelButton(boolean b) {
        buttons[0].setActive(b);
    }

    public void setBuildButtons(boolean b) {
        buttons[1].setActive(b);
    }

//    private void loadButtonImgs() {
//        //button 0
//        /*InputStream is = getClass().getResourceAsStream("/CANCEL_BUILDING.png");
//        try {
//            buttons[0].img = ImageIO.read(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //button 1
//        is = getClass().getResourceAsStream("/tower_blue_1.png");
//        try {
//            buttons[1].img = ImageIO.read(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }*/
//        InputStream is;
//        for (BUTTONS b : BUTTONS.values()) {
//            is = getClass().getResourceAsStream("/buttons/" + b + ".png");
//            try {
//                buttons[b.ordinal()].setImg(ImageIO.read(is));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public void mousePressed(MyEvent e) {
//        for (Button b : buttons) {
//            if (b.isActive()) {
//                if (isIn(e, b)) {
//                    b.setMousePressed(true);
//                }
//            }
//        }
//    }

    public void mouseReleased(MyEvent e) {
        for (Button b : buttons) {
            if (b.isActive()) {
                if (isIn(e, b)) {
//                    if (b.isMousePressed()) {
                        buttonAction(b.getButton());
//                    }
                    break;
                }
            }
        }
        resetButtons();
    }

    public void mouseMoved(MyEvent e) {
        for (Button b : buttons) {
            b.setMouseOver(false);
        }
        for (Button b : buttons) {
            if (b.isActive()) {
                if (isIn(e, b)) {
                    b.setMouseOver(true);
                    break;
                }
            }
        }
    }

    private void resetButtons() {
        for (Button b : buttons) {
            b.resetBools();
        }
    }

    private boolean isIn(MyEvent e, Button b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void draw(Object o) {
        for (Button b : buttons) {
            if (b.isActive()) {
                b.draw(o, b.getxPos(), b.getyPos());
                //g.drawImage(b.getImg(), b.getxPos(), b.getyPos(), null);
            }
        }
    }

    // Getters and setters
    public Button[] getButtons() {
        return buttons;
    }
}
