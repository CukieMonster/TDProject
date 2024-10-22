package com.tdproject.graphics;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import lombok.Setter;

public abstract class Sprite {

    public enum Type { BACKGROUND, TOWER, MISSILE, ENEMY, BUTTON, ITEM }

    public static final String RESOURCES_PATH = "/com/tdproject/";
    public final static String BACKGROUND = RESOURCES_PATH + "background.png";

    private final static String BUTTONS_PATH = RESOURCES_PATH + "buttons/";
    public final static String BUTTON_MAIN_MENU_PLAY = BUTTONS_PATH + "play";
    public final static String BUTTON_MAIN_MENU_INVENTORY = BUTTONS_PATH + "inventory";
    public final static String BUTTON_MAIN_MENU_SETTINGS = BUTTONS_PATH + "settings";
    public final static String BUTTON_MAIN_MENU_QUIT = BUTTONS_PATH + "quit";
    public final static String BUTTON_FAST_FORWARD = BUTTONS_PATH + "fast_forward";
    public final static String BUTTON_SKIP = BUTTONS_PATH + "skip";
    public final static String BUTTON_CANCEL_BUILD = BUTTONS_PATH + "cancel_building";
    public final static String BUTTON_BUILD_TOWER_1 = BUTTONS_PATH + "build_tower_1";

    private final static String TOWERS_PATH = RESOURCES_PATH + "towers/";
    public final static String TOWER_1 = TOWERS_PATH + "tower_blue_0";
    public final static String MISSILE = TOWERS_PATH + "homing_missile.png";

    public final static String UPGRADES_PATH = RESOURCES_PATH + "upgrades/";

    public final static String ENEMIES_PATH = RESOURCES_PATH + "enemies/";
    public final static String ENEMY0 = ENEMIES_PATH + "enemy_0.png";
    public final static String ENEMY1 = ENEMIES_PATH + "enemy_1.png";
    public final static String ENEMY2 = ENEMIES_PATH + "enemy_2.png";
    public final static String ENEMY3 = ENEMIES_PATH + "enemy_3.png";
    public final static String ENEMY4 = ENEMIES_PATH + "enemy_4.png";

    public final static String ITEMS_PATH = RESOURCES_PATH + "items/";
    public final static String ITEM_TOWER = ITEMS_PATH + "tower.png";
    public final static String ITEM_BASE = ITEMS_PATH + "base.png";
    public final static String ITEM_ENEMY = ITEMS_PATH + "enemy.png";

    protected final static String MISSING_SPRITE = RESOURCES_PATH + "missing_sprite.png";
    private String[] paths = {
            RESOURCES_PATH + "background.png",
            RESOURCES_PATH + "towers/tower_blue_",
            RESOURCES_PATH + "towers/homing_missile.png",
            RESOURCES_PATH + "enemies/enemy_",
            RESOURCES_PATH + "buttons/",
            RESOURCES_PATH + "items/",
    };

    @Setter
    @Getter
    protected Vector2d position;    // position is bottom left for enemy, tower, item, but is the center of button, homing missile, background
    @Getter
    protected BufferedImage sprite;

//    public void draw(Object g) {
//        ((Graphics)g).drawImage(sprite, (int) centerPosition.x, (int) centerPosition.y, null);
//    }

    public void drawCentered(Object g) {
        // TODO: fix drawing
        int x = (int) (position.x - sprite.getWidth() / 2);
        int y = (int) (position.y - sprite.getHeight() / 2);
        //System.out.printf("Drawing %s at position (%d, %d)\n", this.getClass(), x, y);
        ((Graphics)g).drawImage(sprite, x, y, null);
    }

//    @Deprecated
//    public void loadSprite(Type type, String suffix) {
//        InputStream is;
//        //InputStream is = getClass().getResourceAsStream(path);
//        try {
//            switch (type) {
//                case BACKGROUND:
//                case MISSILE:
//                    is = getClass().getResourceAsStream(paths[type.ordinal()]);
//                    break;
//                case ITEM:
//                    is = getClass().getResourceAsStream(paths[type.ordinal()] + "tower.png");
//                    break;
//                default:
//                    is = getClass().getResourceAsStream(paths[type.ordinal()] + suffix + ".png");
//            }
//            if (is == null) {
//                //System.err.printf("Could not load image: %s, %s%n", type, suffix);
//                is = getClass().getResourceAsStream(MISSING_SPRITE);
//            }
//            //sprite = ImageIO.read(is);
//            sprite = BitmapFactory.decodeFile(MISSING_SPRITE);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    protected void loadSprite(String imagePath) {
        //InputStream is = getClass().getResourceAsStream(imagePath);
        //System.out.println(imagePath);
        InputStream is;
        try {
            is = getClass().getResourceAsStream(imagePath);
            if (is == null) {
                //System.err.printf("Could not load image: %s%n", imagePath);
                is = getClass().getResourceAsStream(MISSING_SPRITE);
            }
            sprite = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
