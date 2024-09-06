package com.tdproject.ui;

import com.tdproject.graphics.Sprite;

import com.tdproject.inputs.MyEvent;

import java.util.function.Consumer;

public class Button extends Sprite {

    private int id;
    protected Consumer<Button> action;
    private int value;

    private int xOffsetCenter;
//    private BufferedImage img;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;
    private boolean visible = false;
    private boolean active = true;

    public Button(String imagePath, Consumer<Button> action) {
        this(imagePath, action, true);
    }

    public Button(String imagePath, Consumer<Button> action, boolean defaultState) {
        this.visible = defaultState;
        this.action = action;

        loadSprite(imagePath);
    }

    public void initBounds() {
        bounds = new Rectangle((int) position.x - sprite.getWidth() / 2, (int) position.y - sprite.getHeight() / 2, sprite.getWidth(), sprite.getHeight());
    }

    public boolean mouseReleased(MyEvent e) {
        if (visible && isIn(e)) {
            activate();
            return true;
        }
        return false;
    }

    private boolean isIn(MyEvent e) {
        return bounds.contains(e.getX(), e.getY());
    }

    private void activate() {
        action.accept(null);
    }

    public void mouseMoved(MyEvent e) {
        setMouseOver(visible && isIn(e));
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    private void setMouseOver(boolean mouseOver) {
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

    public boolean isActive() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
