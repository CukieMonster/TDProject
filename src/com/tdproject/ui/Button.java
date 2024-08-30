package com.tdproject.ui;

import com.tdproject.graphics.Sprite;

import com.tdproject.inputs.MyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.function.Consumer;

public class Button extends Sprite {

    private int id;
    protected Consumer<Button> action;
    private int value;

    private int xOffsetCenter;
//    private BufferedImage img;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;
    private boolean active = false;

    public Button(boolean defaultState, String imagePath, Consumer<Button> action) {
        this.active = defaultState;
        this.action = action;

        loadSprite(imagePath);
    }

    public void initBounds() {
        bounds = new Rectangle((int) position.x - sprite.getWidth() / 2, (int) position.y - sprite.getHeight() / 2, sprite.getWidth(), sprite.getHeight());
    }

    public boolean mouseReleased(MyEvent e) {
        if (active && isIn(e)) {
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
        setMouseOver(active && isIn(e));
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
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
