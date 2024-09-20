package com.tdproject.towers;

import com.tdproject.graphics.Sprite;

public enum UpgradeType {

    // TODO: do not allow splash and aoe
    DAMAGE("damage.png"),
    SPEED("speed.png"),
    RANGE("range.png"),
    SPLASH("splash.png"),
    SLOW("slow.png"),
    DAMAGE_OVER_TIME("dot.png"),
    AREA_OF_EFFECT("aoe.png");
    // MORE_MONEY_PER_KILL ???

    final String imagePath;

    UpgradeType(String imagePath) {
        this.imagePath = Sprite.UPGRADES_PATH + imagePath;
    }

}
