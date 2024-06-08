package com.tdproject.graphics;

import com.tdproject.main.FieldParameters;

import javax.vecmath.Vector2d;

public class Background extends Sprite {

    public Background() {
        loadSprite(Sprite.BACKGROUND);
        position = new Vector2d(FieldParameters.X_RESOLUTION / 2D, FieldParameters.Y_RESOLUTION / 2D);
    }
}
