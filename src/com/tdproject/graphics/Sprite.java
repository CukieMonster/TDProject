package com.tdproject.graphics;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public abstract class Sprite {

    public enum Type { BACKGROUND, TOWER, MISSILE, ENEMY, BUTTON, ITEM }
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
        int x = (int) (centerPosition.x - sprite.getWidth() / 2);
        int y = (int) (centerPosition.y - sprite.getHeight() / 2);
        System.out.printf("Drawing %s at position (%d, %d)\n", this.getClass(), x, y);
        ((Graphics)g).drawImage(sprite, x, y, null);
    }

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
            sprite = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
