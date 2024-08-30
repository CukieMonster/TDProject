package com.tdproject.graphics;

import com.tdproject.inputs.MyEvent;
import com.tdproject.ui.Button;

import javax.vecmath.Vector2d;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Comparator;

public class ButtonPanel extends AbstractPanel<Button> {

    //private final int[][] positions;

    public ButtonPanel(int xCenterPos, int yCenterPos, int width, int height, Button[] buttons) {
        super(xCenterPos, yCenterPos, width, height, buttons);
    }

    public ButtonPanel(int xCenterPos, int yCenterPos, int width, int height, Button[] buttons, int columns) {
        super(xCenterPos, yCenterPos, width, height, buttons, columns);
    }

    @Override
    protected void setPosition(Button button, int x, int y) {
        button.position = new Vector2d(x, y);
        button.initBounds();
    }

    @Override
    protected int getMaxWidth() {
        return Arrays.stream(content).max(Comparator.comparingInt(b -> b.sprite.getWidth())).get().sprite.getWidth();
    }

    @Override
    protected int getMaxHeight() {
        return Arrays.stream(content).max(Comparator.comparingInt(b -> b.sprite.getHeight())).get().sprite.getHeight();
    }

    public boolean mouseReleased(MyEvent e) {
        boolean anyButtonPressed = false;
        for (Button button : content) {
            anyButtonPressed = anyButtonPressed || button.mouseReleased(e);
        }
        return anyButtonPressed;
    }

    public void mouseMoved(MyEvent e) {
        for (Button b : content) {
            b.mouseMoved(e);
        }
    }

    @Override
    protected void drawContent(Object o, Button button) {
        if (button.isActive()) {
            button.drawCentered(o);
        }
    }

}
