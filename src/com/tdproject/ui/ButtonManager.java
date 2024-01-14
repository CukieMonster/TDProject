package com.tdproject.ui;

import com.tdproject.inputs.MyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//enum Buttons {CANCEL_BUILDING, BUILD_TOWER_1, FAST_FORW_BUTTON, SKIP_BUTTON}

public class ButtonManager {

    public enum MainMenuButtonID {PLAY, INVENTORY, SETTINGS, QUIT};
    public enum SettingsButtonID {};
    public enum InventoryButtonID {};
    public enum PlayingButtonID {CANCEL_BUILDING, BUILD_TOWER_1, FAST_FORWARD, SKIP};
    public enum PausedButtonID {};

    //private static ButtonManager instance;
    //private int buttonNr = 4;
    //private Map<String, Button> buttons = new HashMap<>();
    private List<Button> buttons = new ArrayList<>();

    public ButtonManager(Button[] buttons) {
//        for (var entry : PlayingButtons.buttonID.entrySet()) {
//            buttons.add(new Button(entry.getValue()));
//        }
        this.buttons.addAll(Arrays.asList(buttons));
//        for (int i = 0; i < template.getDefaultState().length; i++) {
//            buttons.add(new Button(template, i));
//        }
//        buttons[0] = new Button(PlayingButtons.ButtonID.CANCEL_BUILDING, 1800, 900);
//        buttons[1] = new Button(PlayingButtons.ButtonID.BUILD_TOWER_1, 1800, 100);
//        buttons[2] = new Button(PlayingButtons.ButtonID.FAST_FORW_BUTTON, 1600, 900);
//        buttons[3] = new Button(PlayingButtons.ButtonID.SKIP_BUTTON, 1800, 900);
        //loadButtonImgs();
//        buttons[1].setActive(true);
//        buttons[2].setActive(true);
    }

//    public static ButtonManager getInstance() {
//        if (instance == null) {
//            instance = new ButtonManager();
//        }
//        return instance;
//    }

//    private void buttonAction(PlayingButtons button) {
//        switch (button) {
//            case CANCEL_BUILDING:
//                TowerManager.getInstance().cancelBuild();
//                break;
//            case BUILD_TOWER_1:
//                TowerManager.getInstance().enterBuildMode(0);
//                break;
//            case FAST_FORW_BUTTON:
//                Playing.getInstance().changeGameSpeed();
//                break;
//            case SKIP_BUTTON:
//                EnemyManager.getInstance().spawnWave();
//                buttons[PlayingButtons.SKIP_BUTTON.ordinal()].setActive(false);
//        }
//    }


    public void setButton(int i, boolean b) {
        buttons.get(i).setActive(b);
    }
//    public void setCancelButton(boolean b) {
//        buttons.get(PlayingButtons.ButtonID.CANCEL_BUILDING.ordinal()).setActive(b);
//    }
//
//    public void setBuildButtons(boolean b) {
//        buttons.get(PlayingButtons.ButtonID.BUILD_TOWER_1.ordinal()).setActive(b);
//    }

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
                        //buttonAction(b.getButton());
                        b.activate();
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
                b.drawCentered(o);
                //g.drawImage(b.getImg(), b.getxPos(), b.getyPos(), null);
            }
        }
    }

    // Getters and setters
//    public Button[] getButtons() {
//        return buttons;
//    }
}
