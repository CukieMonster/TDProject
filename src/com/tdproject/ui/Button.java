package com.tdproject.ui;

import com.tdproject.graphics.Sprite;

import java.util.function.Consumer;

public class Button extends Sprite {

    private int id;
    private Consumer action;
    private int value;
    private int xPos, yPos;
    private int xOffsetCenter;
//    private BufferedImage img;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;
    private boolean active = false;

    public Button(boolean defaultState, int xPos, int yPos, String imagePath, Consumer action) {
        this.active = defaultState;
        this.xPos = xPos;
        this.yPos = yPos;
        this.action = action;

        loadSprite(Type.BUTTON, imagePath);
        initBounds();
    }

    public Button(ButtonTemplate template, int id) {
        this.id = id;
//        action = PlayingButtons.action[id];
//        this.xPos = PlayingButtons.position[id][0];
//        this.yPos = PlayingButtons.position[id][1];
        action = template.getAction()[id];
        xPos = template.getPosition()[id][0];
        yPos = template.getPosition()[id][1];
        String suffix;
        switch (template.getGameState()) {
            case MAIN_MENU:
                suffix = ButtonManager.MainMenuButtonID.values()[id].toString();
                break;
            case SETTINGS:
                suffix = ButtonManager.SettingsButtonID.values()[id].toString();
                break;
            case INVENTORY:
                suffix = ButtonManager.InventoryButtonID.values()[id].toString();
                break;
            case PLAYING:
                suffix = ButtonManager.PlayingButtonID.values()[id].toString();
                break;
            case PAUSED:
                suffix = ButtonManager.PausedButtonID.values()[id].toString();
                break;
            default:
                suffix = "";
        }
        loadSprite(Type.BUTTON, suffix);
        initBounds();
//        active = PlayingButtons.defaultState[id];
        active = template.getDefaultState()[id];
    }

    private void initBounds() {
        bounds = new Rectangle(xPos, yPos, sprite.getWidth(), sprite.getHeight());
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
