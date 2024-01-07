package com.tdproject.graphics;

import com.tdproject.inputs.MyEvent;
import com.tdproject.ui.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class ButtonPanel {

    private final int xCenterPos;
    private final int yCenterPos;
    private final int width;
    private final int height;
    private final Button[] buttons;
    private final int columns;
    private final int[][] positions;

    public ButtonPanel(int xCenterPos, int yCenterPos, int width, int height, Button[] buttons) {
        this.xCenterPos = xCenterPos;
        this.yCenterPos = yCenterPos;
        this.width = width;
        this.height = height;
        this.buttons = buttons;
        columns = 1;
        positions = calculateButtonPositions();
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

    public void draw(Object o) {
        Graphics g = (Graphics) o;
        g.setColor(new Color(0, 0, 0, 100));
        drawCentered(g);

        for (Button button : buttons) {
            if (button.isActive()) {
                button.drawCentered(o, button.getxPos(), button.getyPos());
            }
        }
    }

    private int[][] calculateButtonPositions() {
        int maxHeight = Arrays.stream(buttons).max(Comparator.comparingInt(b -> b.getBounds().getHeight())).get().getBounds().getHeight();
        return calculateButtonPositions(buttons.length, columns, 0, maxHeight);
    }

    public int[][] calculateButtonPositions(int buttonsAmount, int columns, int maxWidth, int maxHeight) {
        int[][] result = new int[buttonsAmount][2];
        int rows = Math.round(buttonsAmount / 2F);

        int yGap = height - (maxHeight * rows) / (rows + 1);
        if (yGap > 0) {
            yGap = 0;
        }
        int currentYPos = yCenterPos - height / 2;
        for (int i = 0; i < columns; i++) {
            currentYPos += yGap;
            for (int j = i; j < buttonsAmount; j+= rows) {
                result[i][1] = currentYPos;
            }
            currentYPos += maxHeight;
        }
        return result;
    }

    private void drawCentered(Graphics g) {
        g.fillRect(
                xCenterPos - (width / 2),
                yCenterPos - (height / 2),
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
