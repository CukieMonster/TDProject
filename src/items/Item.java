package items;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import items.ItemParameters.*;
import ui.BUTTONS;

import javax.imageio.ImageIO;

import static items.ItemParameters.MAX_LEVEL;
import static items.ItemParameters.MAX_POSSIBLE_MODIFIER_VALUE;

public class Item {
    private int level;
    private Rarity rarity;
    private ItemType itemType;
    private Attribute[] attributes;
    private int[] maxModifierValues;
    private int[] modifierValues;
    private BufferedImage sprite;

    private Random random = new Random();

    public Item(int lv, Rarity r) {
        level = getLevel(lv);
        rarity = r;
        itemType = ItemType.values()[random.nextInt(ItemType.values().length)];
        attributes = new Attribute[rarity.ordinal() + 1];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = getAttribute();
        }
        maxModifierValues = new int[attributes.length];
        for (int i = 0; i < maxModifierValues.length; i++) {
            maxModifierValues[i] = getMaxModifierValue(i, lv);
        }
        modifierValues = new int[attributes.length];
        for (int i = 0; i < modifierValues.length; i++) {
            modifierValues[i] = rerollAttribute(i);
        }
        loadImage();
    }

    public int rerollAttribute(int index) {
        //rolls value between 0.8 and 1.0 times max value
        if (attributes[index] == Attribute.Cost) {
            return -(int)((1 - random.nextInt(20) / 100F) * maxModifierValues[index]);
        }
        else {
            return (int)((1 - random.nextInt(20) / 100F) * maxModifierValues[index]);
        }
    }

    private int getLevel(int lv) {
        if (lv < 1) {
            return 1;
        }
        else if (lv < MAX_LEVEL) {
            return lv;
        }
        else {
            return MAX_LEVEL;
        }
    }

    private Attribute getAttribute() {
        if (itemType != ItemType.Base) {
            return Attribute.values()[random.nextInt(Attribute.values().length - 1)];
        }
        else {
            return Attribute.Health;
        }
    }

    private int getMaxModifierValue(int index, int lv) {
        return (int)(((float)lv) / MAX_LEVEL * MAX_POSSIBLE_MODIFIER_VALUE[attributes[index].ordinal()]);
    }

    private void loadImage() {
        InputStream is;
        is = getClass().getResourceAsStream("/items/" + itemType + ".png");
        try {
            sprite = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters and setters
    public BufferedImage getSprite() {
        return sprite;
    }
}
