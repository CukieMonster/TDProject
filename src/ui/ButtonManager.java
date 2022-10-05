package ui;

import main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

enum Buttons {CANCEL_BUILDING, BUILD_TOWER_1}

public class ButtonManager {

    private int buttonNr = 2;
    public Button[] buttons = new Button[buttonNr];
    private Game game;

    public ButtonManager(Game g) {
        game = g;
        buttons[0] = new Button(Buttons.CANCEL_BUILDING, 1800, 900);
        buttons[1] = new Button(Buttons.BUILD_TOWER_1, 1800, 100);
        loadButtonImgs();
        buttons[1].active = true;
    }

    private void buttonAction(Buttons button) {
        switch (button) {
            case CANCEL_BUILDING:
                game.getTowerManager().cancelBuild();
                break;
            case BUILD_TOWER_1:
                game.getTowerManager().enterBuildMode(0);
                break;
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
        InputStream is = getClass().getResourceAsStream("/cancel_button.png");
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
