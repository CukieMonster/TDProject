package com.tdproject.graphics;

import java.awt.Color;
import java.awt.Graphics;

// This class has specific PC functionality
public abstract class GeneralPanel<C> extends AbstractPanel<C> {

    public GeneralPanel(int xCenterPos, int yCenterPos, int width, int height, C[] content) {
        super(xCenterPos, yCenterPos, width, height, content);
    }

    public GeneralPanel(int xCenterPos, int yCenterPos, int width, int height, C[] content, int columns) {
        super(xCenterPos, yCenterPos, width, height, content, columns);
    }

    public void draw(Object o) {
        Graphics g = (Graphics) o;
        g.setColor(new Color(0, 0, 0, 100));
        drawCentered(g);

        for (C item : content) {
            drawContent(o, item);
        }
    }

    private void drawCentered(Graphics g) {
        int x = (int) centerPosition.x - (width / 2);
        int y = (int) centerPosition.y - (height / 2);
        g.fillRect(
                x,
                y,
                width,
                height
        );
    }

}
