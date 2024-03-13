package com.tdproject.graphics;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import lombok.Setter;

public abstract class Sprite {

    public enum Type { BACKGROUND, TOWER, MISSILE, ENEMY, BUTTON, ITEM }

    private final static String BUTTONS_PATH = "/com/tdproject/buttons/";
    public final static String BUTTON_MAIN_MENU_PLAY = BUTTONS_PATH + "play.png";
    public final static String BUTTON_MAIN_MENU_INVENTORY = BUTTONS_PATH + "inventory.png";
    public final static String BUTTON_MAIN_MENU_SETTINGS = BUTTONS_PATH + "settings.png";
    public final static String BUTTON_MAIN_MENU_QUIT = BUTTONS_PATH + "quit.png";
    public final static String BUTTON_FAST_FORWARD = BUTTONS_PATH + "fast_forward.png";
    public final static String BUTTON_SKIP = BUTTONS_PATH + "skip.png";
    public final static String BUTTON_CANCEL_BUILD = BUTTONS_PATH + "cancel_building.png";
    public final static String BUTTON_BUILD_TOWER_1 = BUTTONS_PATH + "build_tower_1.png";

    private final static String TOWERS_PATH = "/com/tdproject/towers/";
    public final static String TOWER_1 = TOWERS_PATH + "tower_blue_0.png";
    public final static String UPGRADES_PATH = "/com/tdproject/upgrades/";
    private final static String MISSING_SPRITE = "/com/tdproject/missing_sprite.png";
    private String[] paths = {
            "/com/tdproject/background.png",
            "/com/tdproject/towers/tower_blue_",
            "/com/tdproject/towers/homing_missile.png",
            "/com/tdproject/enemies/enemy_",
            "/com/tdproject/buttons/",
            "/com/tdproject/items/",
    };

    @Setter
    protected Vector2d position;    // position is bottom left for enemy, tower, item, but is the center of button, homing missile, background
    protected BufferedImage sprite;

//    public void draw(Object g) {
//        ((Graphics)g).drawImage(sprite, (int) centerPosition.x, (int) centerPosition.y, null);
//    }

    public void drawCentered(Object g) {
        // TODO: fix drawing
        int x = (int) (position.x - sprite.getWidth() / 2);
        int y = (int) (position.y - sprite.getHeight() / 2);
        System.out.printf("Drawing %s at position (%d, %d)\n", this.getClass(), x, y);
        ((Graphics)g).drawImage(sprite, x, y, null);
    }

    public void draw(Object g) {
        ((Graphics)g).drawImage(sprite, (int) position.x, (int) position.y, null);
    }

    @Deprecated
    public void loadSprite(Type type, String suffix) {
        InputStream is;
        //InputStream is = getClass().getResourceAsStream(path);
        try {
            switch (type) {
                case BACKGROUND:
                case MISSILE:
                    is = getClass().getResourceAsStream(paths[type.ordinal()]);
                    break;
                case ITEM:
                    is = getClass().getResourceAsStream(paths[type.ordinal()] + "all_towers.png");
                    break;
                default:
                    is = getClass().getResourceAsStream(paths[type.ordinal()] + suffix + ".png");
            }
            if (is == null) {
                System.err.printf("Could not load image: %s, %s%n", type, suffix);
                is = getClass().getResourceAsStream(MISSING_SPRITE);
            }
            sprite = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadSprite(String imagePath) {
        //InputStream is = getClass().getResourceAsStream(imagePath);
        System.out.println(imagePath);
        InputStream is;
        try {
            is = getClass().getResourceAsStream(imagePath);
            if (is == null) {
                System.err.printf("Could not load image: %s%n", imagePath);
                is = getClass().getResourceAsStream(MISSING_SPRITE);
            }
            sprite = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
