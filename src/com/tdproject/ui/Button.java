package com.tdproject.ui;

import com.tdproject.graphics.Sprite;

import java.util.function.Consumer;

public class Button extends Sprite {

    private final PlayingButtons.ButtonID button;
    private Consumer action;
    private int value;
    private int xPos, yPos;
    private int xOffsetCenter;
//    private BufferedImage img;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;
    private boolean active = false;

    public Button(PlayingButtons.ButtonID b) {
        button = b;
        action = PlayingButtons.action[button.ordinal()];
        this.xPos = PlayingButtons.posistion[button.ordinal()][0];
        this.yPos = PlayingButtons.posistion[button.ordinal()][1];
        initBounds();
        loadSprite(Type.BUTTON, button.ordinal());
        active = PlayingButtons.defaultState[button.ordinal()];
    }

    private void initBounds() {
        bounds = new Rectangle(xPos, yPos, 64, 64);
    }

//    private void loadImg() {
//        InputStream is = getClass().getResourceAsStream("/CANCEL_BUILDING.png");
//        try {
//            img = ImageIO.read(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void activate() {
        action.accept(null);
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
//    public PlayingButtons getButton() {
//        return button;
//    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

//    public  BufferedImage getImg() {
//        return img;
//    }
//
//    public void setImg(BufferedImage img) {
//        this.img = img;
//    }

    public boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
