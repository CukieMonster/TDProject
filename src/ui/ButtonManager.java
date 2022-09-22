package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

enum Buttons {CANCEL_BUILDING}

public class ButtonManager {

    public Button[] buttons = new Button[1];

    public ButtonManager() {
        buttons[0] = new Button(Buttons.CANCEL_BUILDING, 1800, 900);
    }

    private void buttonAction(Buttons button) {
        switch (button) {
            case CANCEL_BUILDING:
                System.out.println("button pressed");
                break;
        }
    }

    public void mousePressed(MouseEvent e) {
        for (Button b : buttons) {
            if (isIn(e, b)) {
                b.setMousePressed(true);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (Button b : buttons) {
            if (isIn(e, b)) {
                if (b.isMousePressed()) {
                    buttonAction(b.button);
                }
                break;
            }
        }
        resetButtons();
    }

    public void mouseMoved(MouseEvent e) {
        for (Button b : buttons) {
            b.setMouseOver(false);
        }
        for (Button b : buttons) {
            if (isIn(e, b)) {
                b.setMouseOver(true);
                break;
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
            g.drawImage(b.img, b.xPos, b.yPos, null);
        }
    }
}
