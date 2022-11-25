package ui;

import main.Game;
import main.GameObjectList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

//enum Buttons {CANCEL_BUILDING, BUILD_TOWER_1, FAST_FORW_BUTTON, SKIP_BUTTON}

public class ButtonManager {

    private int buttonNr = 4;
    public Button[] buttons = new Button[buttonNr];

    public ButtonManager(Game g) {
        GameObjectList.buttonManager = this;
        buttons[0] = new Button(BUTTONS.CANCEL_BUILDING, 1800, 900);
        buttons[1] = new Button(BUTTONS.BUILD_TOWER_1, 1800, 100);
        buttons[2] = new Button(BUTTONS.FAST_FORW_BUTTON, 1600, 900);
        buttons[3] = new Button(BUTTONS.SKIP_BUTTON, 1800, 900);
        loadButtonImgs();
        buttons[1].active = true;
        buttons[2].active = true;
    }

    private void buttonAction(BUTTONS button) {
        switch (button) {
            case CANCEL_BUILDING:
                GameObjectList.game.getTowerManager().cancelBuild();
                break;
            case BUILD_TOWER_1:
                GameObjectList.game.getTowerManager().enterBuildMode(0);
                break;
            case FAST_FORW_BUTTON:
                Game.changeGamespeed();
                break;
            case SKIP_BUTTON:
                GameObjectList.enemyManager.spawnWave();
                buttons[BUTTONS.SKIP_BUTTON.ordinal()].active = false;
        }
    }

    public void setCancelButton(boolean b) {
        buttons[0].active = b;
    }

    public void setBuildButtons(boolean b) {
        buttons[1].active = b;
    }

    private void loadButtonImgs() {
        //button 0
        /*InputStream is = getClass().getResourceAsStream("/CANCEL_BUILDING.png");
        try {
            buttons[0].img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //button 1
        is = getClass().getResourceAsStream("/Tower_blue1.png");
        try {
            buttons[1].img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        InputStream is;
        for (BUTTONS b : BUTTONS.values()) {
            is = getClass().getResourceAsStream("/" + b + ".png");
            try {
                buttons[b.ordinal()].img = ImageIO.read(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        for (Button b : buttons) {
            if (b.active) {
                if (isIn(e, b)) {
                    b.setMousePressed(true);
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (Button b : buttons) {
            if (b.active) {
                if (isIn(e, b)) {
                    if (b.isMousePressed()) {
                        buttonAction(b.button);
                    }
                    break;
                }
            }
        }
        resetButtons();
    }

    public void mouseMoved(MouseEvent e) {
        for (Button b : buttons) {
            b.setMouseOver(false);
        }
        for (Button b : buttons) {
            if (b.active) {
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

    private boolean isIn(MouseEvent e, Button b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void draw(Graphics g) {
        for (Button b : buttons) {
            if (b.active) {
                g.drawImage(b.img, b.xPos, b.yPos, null);
            }
        }
    }
}
