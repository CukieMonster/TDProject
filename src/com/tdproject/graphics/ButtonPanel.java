package com.tdproject.graphics;

import com.tdproject.inputs.MyEvent;
import com.tdproject.ui.Button;

import javax.vecmath.Vector2d;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Comparator;

public class ButtonPanel {

    private final Vector2d centerPosition;
    private final int width;
    private final int height;
    private final Button[] buttons;
    private final int columns;
    private final int[][] positions;

    public ButtonPanel(int xCenterPos, int yCenterPos, int width, int height, Button[] buttons) {
        centerPosition = new Vector2d(xCenterPos, yCenterPos);
        this.width = width;
        this.height = height;
        this.buttons = buttons;
        this.columns = 1;
        positions = calculateButtonPositions();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].position = new Vector2d(positions[i][0], positions[i][1]);
            buttons[i].initBounds();
        }
    }

    public ButtonPanel(int xCenterPos, int yCenterPos, int width, int height, Button[] buttons, int columns) {
        centerPosition = new Vector2d(xCenterPos, yCenterPos);
        this.width = width;
        this.height = height;
        this.buttons = buttons;
        this.columns = columns;
        positions = calculateButtonPositions();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].position = new Vector2d(positions[i][0], positions[i][1]);
            buttons[i].initBounds();
        }
    }

    public void mouseReleased(MyEvent e) {
        for (Button b : buttons) {
            if (b.isActive()) {
                if (isIn(e, b)) {
                    b.activate();
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

    public void draw(Object o) {
        Graphics g = (Graphics) o;
        g.setColor(new Color(0, 0, 0, 100));
        drawCentered(g);

        for (Button button : buttons) {
            if (button.isActive()) {
                button.drawCentered(o);
            }
        }
    }

    private int[][] calculateButtonPositions() {
        int maxWidth = Arrays.stream(buttons).max(Comparator.comparingInt(b -> b.sprite.getWidth())).get().sprite.getWidth();
        int maxHeight = Arrays.stream(buttons).max(Comparator.comparingInt(b -> b.sprite.getHeight())).get().sprite.getHeight();
        return calculateButtonPositions(buttons.length, columns, maxWidth, maxHeight);
    }

    public int[][] calculateButtonPositions(int buttonsAmount, int columns, int maxWidth, int maxHeight) {
        int[][] result = new int[buttonsAmount][2];
        int rows = Math.round((float) buttonsAmount / columns);

        int xGap = (width - (maxWidth * columns)) / (columns + 1);
        if (xGap < 0) {
            xGap = 0;
        }
        int currentXPos = (int) centerPosition.x - (width / 2) + (maxWidth / 2);
        for (int i = 0; i < columns; i++) {
            currentXPos += xGap;
            for (int j = i; j < buttonsAmount; j += columns) {
                result[j][0] = currentXPos;
            }
            currentXPos += maxWidth;
        }

        int yGap = (height - (maxHeight * rows)) / (rows + 1);
        if (yGap < 0) {
            yGap = 0;
        }
        int currentYPos = (int) centerPosition.y - (height / 2) + (maxHeight / 2);
        for (int i = 0; i < buttonsAmount; i+= columns) {
            currentYPos += yGap;
            for (int j = i; j < i + columns; j++) {
                if (j >= buttonsAmount) {
                    break;
                }
                result[j][1] = currentYPos;
            }
            currentYPos += maxHeight;
        }
        return result;
    }

    private void drawCentered(Graphics g) {
        int x = (int) centerPosition.x - (width / 2);
        int y = (int) centerPosition.y - (height / 2);
        System.out.printf("Drawing %s at position (%d, %d)\n", this.getClass(), x, y);
        g.fillRect(
                x,
                y,
                width,
                height
        );
    }

    private boolean isIn(MyEvent e, Button b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    private void resetButtons() {
        for (Button b : buttons) {
            b.resetBools();
        }
    }

}
