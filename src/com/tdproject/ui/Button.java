package com.tdproject.ui;

import com.tdproject.graphics.Sprite;

import com.tdproject.inputs.MyEvent;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.Setter;

public class Button extends Sprite {

    private final Map<ButtonVariant, BufferedImage> sprites = new EnumMap<>(ButtonVariant.class);
    private BufferedImage hoverSprite;
    private BufferedImage inactiveSprite;
    protected Consumer<Button> action;
    private boolean mouseOver;
    private boolean mousePressed;
    private Rectangle bounds;
    @Getter
    private boolean visible = false;
    @Getter
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
        boolean mouseOver = visible && isIn(e);
        if (this.mouseOver != mouseOver) {
            this.mouseOver = mouseOver;
            updateSprite();
        }
    }

/*    @Override
    public void drawCentered(Object o) {
        if (visible && active && mouseOver) {
            sprite = sprites.get(ButtonVariant.HOVER);
        } else if (visible && active) {
            sprite = sprites.get(ButtonVariant.DEFAULT);
        } else {
            sprite = sprites.get(ButtonVariant.INACTIVE);
        }
        super.drawCentered(o);
    }*/

    @Override
    protected void loadSprite(String imagePath) {
        for (ButtonVariant variant : ButtonVariant.values()) {
            InputStream is;
            try {
                is = getClass().getResourceAsStream(imagePath + variant.path);
                if (is == null) {
                    is = getClass().getResourceAsStream(Sprite.MISSING_SPRITE);
                }
                sprites.put(variant, ImageIO.read(is));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        sprite = sprites.get(ButtonVariant.DEFAULT);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        updateSprite();
    }

    public void setActive(boolean active) {
        this.active = active;
        updateSprite();
    }

    private void updateSprite() {
        if (visible && active && mouseOver) {
            System.out.println("Use sprite " + ButtonVariant.HOVER);
            sprite = sprites.get(ButtonVariant.HOVER);
        } else if (visible && active) {
            System.out.println("Use sprite " + ButtonVariant.DEFAULT);
            sprite = sprites.get(ButtonVariant.DEFAULT);
        } else {
            System.out.println("Use sprite " + ButtonVariant.INACTIVE);
            sprite = sprites.get(ButtonVariant.INACTIVE);
        }
    }

    private enum ButtonVariant {

        DEFAULT("_default.png"),
        HOVER("_hover.png"),
        INACTIVE("_inactive.png");

        final String path;

        ButtonVariant(String path) {
            this.path = path;
        }
    }

}
