package com.tdproject.graphics;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public abstract class Sprite {

    public enum Type { BACKGROUND, TOWER, MISSILE, ENEMY, BUTTON, ITEM }

    public final static String BUTTONS_PATH = "/com/tdproject/buttons/";
    public final static String TOWERS_PATH = "/com/tdproject/towers/";
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

    protected Vector2d centerPosition;
    protected BufferedImage sprite;

//    public void draw(Object g) {
//        ((Graphics)g).drawImage(sprite, (int) centerPosition.x, (int) centerPosition.y, null);
//    }

    public void drawCentered(Object g) {
        // TODO: fix drawing
        int x = (int) (centerPosition.x - sprite.getWidth() / 2);
        int y = (int) (centerPosition.y - sprite.getHeight() / 2);
        System.out.printf("Drawing %s at position (%d, %d)\n", this.getClass(), x, y);
        ((Graphics)g).drawImage(sprite, x, y, null);
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
                System.err.printf("Could not load image: %s, %s", type, suffix);
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
                System.err.printf("Could not load image: %s", imagePath);
                is = getClass().getResourceAsStream(MISSING_SPRITE);
            }
            sprite = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
