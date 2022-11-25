package ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Button {

    public BUTTONS button;
    public int xPos, yPos;
    private int xOffsetCenter;
    public BufferedImage img;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;
    public boolean active = false;

    public Button(BUTTONS b, int xPos, int yPos) {
        button = b;
        this.xPos = xPos;
        this.yPos = yPos;
        //loadImg();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos, yPos, 64, 64);
    }

    private void loadImg() {
        InputStream is = getClass().getResourceAsStream("/CANCEL_BUILDING.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
