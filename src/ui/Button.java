package ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Button {

    private BUTTONS button;
    private int xPos, yPos;
    private int xOffsetCenter;
    private BufferedImage img;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;
    private boolean active = false;

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

    // Getters and setters
    public BUTTONS getButton() {
        return button;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public  BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
