package com.tdproject.graphics;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import lombok.Setter;

// This class has specific PC functionality
public abstract class Sprite {

    //public enum Type { BACKGROUND, TOWER, MISSILE, ENEMY, BUTTON, ITEM }

    public final static int TOWER_WIDTH = 64;
    public final static int TOWER_HEIGHT = 64;
    public final static int MISSILE_WIDTH = 5;
    public final static int MISSILE_HEIGHT = 5;
    public final static int ENEMY_WIDTH = 64;
    public final static int ENEMY_HEIGHT = 64;
    public final static int ITEM_WIDTH = 64;
    public final static int ITEM_HEIGHT = 64;
    public final static int BUTTON_WIDTH = 64;
    public final static int BUTTON_HEIGHT = 64;
    public final static int MENU_BUTTON_WIDTH = 200;
    public final static int MENU_BUTTON_HEIGHT = 100;
    public final static int BACKGROUND_WIDTH = 1920;
    public final static int BACKGROUND_HEIGHT = 1080;

    protected static final String RESOURCES_PATH = "/com/tdproject/";
    private final static String BUTTONS_PATH = RESOURCES_PATH + "buttons/";
    private final static String TOWERS_PATH = RESOURCES_PATH + "towers/";
    public final static String UPGRADES_PATH = RESOURCES_PATH + "upgrades/";
    public final static String ENEMIES_PATH = RESOURCES_PATH + "enemies/";
    public final static String ITEMS_PATH = RESOURCES_PATH + "items/";

    public enum SpriteId {

        BACKGROUND(RESOURCES_PATH + "background.png", null),

        BUTTON_MAIN_MENU_PLAY(BUTTONS_PATH + "play_default.png", null),
        BUTTON_MAIN_MENU_INVENTORY(BUTTONS_PATH + "inventory_default.png", null),
        BUTTON_MAIN_MENU_SETTINGS(BUTTONS_PATH + "settings_default.png", null),
        BUTTON_MAIN_MENU_QUIT(BUTTONS_PATH + "quit_default.png", null),

        BUTTON_FAST_FORWARD(BUTTONS_PATH + "fast_forward_default.png", null),
        BUTTON_SKIP(BUTTONS_PATH + "skip_default.png", null),
        BUTTON_PAUSE(BUTTONS_PATH + "pause_default.png", null),
        BUTTON_CANCEL_BUILD(BUTTONS_PATH + "cancel_building_default.png", null),
        BUTTON_BUILD_TOWER_1(BUTTONS_PATH + "build_tower_1_default.png", null),
        BUTTON_DELETE_TOWER(BUTTONS_PATH + "delete_default.png", null),

        TOWER_1(TOWERS_PATH + "tower_blue_0_default.png", null),
        MISSILE(TOWERS_PATH + "homing_missile.png", null),

        UPGRADE_DAMAGE(BUTTONS_PATH + "damage.png", null),
        UPGRADE_SPEED(BUTTONS_PATH + "speed.png", null),
        UPGRADE_RANGE(BUTTONS_PATH + "range.png", null),
        UPGRADE_SPLASH(BUTTONS_PATH + "splash.png", null),
        UPGRADE_SLOW(BUTTONS_PATH + "slow.png", null),
        UPGRADE_DOT(BUTTONS_PATH + "dot.png", null),
        UPGRADE_AOE(BUTTONS_PATH + "aoe.png", null),

        ENEMY_0(ENEMIES_PATH + "enemy_0.png", null),
        ENEMY_1(ENEMIES_PATH + "enemy_1.png", null),
        ENEMY_2(ENEMIES_PATH + "enemy_2.png", null),
        ENEMY_3(ENEMIES_PATH + "enemy_3.png", null),
        ENEMY_4(ENEMIES_PATH + "enemy_4.png", null),

        ITEM_TOWER(ITEMS_PATH + "tower.png", null),
        ITEM_BASE(ITEMS_PATH + "base.png", null),
        ITEM_ENEMY(ITEMS_PATH + "enemy.png", null),

        MISSING_SPRITE(RESOURCES_PATH + "missing_sprite.png", null);

        final String path;
        final Integer resourceId;

        SpriteId(String path, Integer resourceId) {
            this.path = path;
            this.resourceId = resourceId;
        }
    }

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
        ((Graphics)g).drawImage(sprite, x, y, null);
    }

    protected void loadSprite(SpriteId id, int width, int height) {
        sprite = loadSpriteFromResources(id, width, height);
    }

    protected BufferedImage loadSpriteFromResources(SpriteId id, int width, int height) {
        InputStream is;
        BufferedImage sprite;
        try {
            is = getClass().getResourceAsStream(id.path);
            if (is == null) {
                is = getClass().getResourceAsStream(SpriteId.MISSING_SPRITE.path);
            }
            sprite = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sprite;
    }

}
