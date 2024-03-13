package com.tdproject.ui;

import com.tdproject.graphics.Sprite;
import com.tdproject.towers.TowerManager;

public class BuildingButtons {

    public static final Button CANCEL_BUILDING_BUTTON = new Button(
            false,
            Sprite.BUTTON_CANCEL_BUILD,
            i -> TowerManager.getInstance().cancelBuild()
    );

    public static final Button[] buttons = {
            CANCEL_BUILDING_BUTTON,
            new Button(
                    true,
                    Sprite.BUTTON_BUILD_TOWER_1,
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
