package com.tdproject.towers;

import com.tdproject.graphics.Sprite;

public enum UpgradeType {

    DAMAGE("damage.png"),
    SPEED("speed.png"),
    RANGE("range.png"),
    SPLASH("splash.png"),
    SLOW("slow.png"),
    DAMAGE_OVER_TIME("dot.png"),
    AREA_OF_EFFECT("aoe.png");

    final String imagePath;

    UpgradeType(String imagePath) {
        this.imagePath = Sprite.UPGRADES_PATH + imagePath;
    }

}
