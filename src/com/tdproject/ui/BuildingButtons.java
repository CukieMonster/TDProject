package com.tdproject.ui;

import com.tdproject.graphics.Sprite;
import com.tdproject.towers.TowerManager;

public class BuildingButtons {

    public static final Button CANCEL_BUILDING_BUTTON = new Button(
            false,
            Sprite.BUTTONS_PATH + "cancel_building.png",
            i -> TowerManager.getInstance().cancelBuild()
    );

    public static final Button[] buttons = {
            CANCEL_BUILDING_BUTTON,
            new Button(
                    true,
                    Sprite.BUTTONS_PATH + "build_tower_1.png",
                    i -> TowerManager.getInstance().enterBuildMode(0)
            )
    };

    public static void setBuildMode(boolean buildMode) {
        for (Button button : buttons) {
            button.setActive(!buildMode);
        }
        CANCEL_BUILDING_BUTTON.setActive(buildMode);
    }

}
