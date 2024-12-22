package com.tdproject.enemies;

import java.awt.Color;
import java.awt.Graphics;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// This class has specific PC functionality
public class HealthBar {

    private final Enemy enemy;
    private final int width;

    public HealthBar(Enemy enemy) {
        this.enemy = enemy;
        width = enemy.getSprite().getWidth() / 2;
    }

    public void draw(Object o, double healthPercentage) {
        var position = enemy.getPosition();
        Graphics g = (Graphics) o;
        int x = (int) position.x - width / 2;
        int y = (int) position.y - enemy.getSprite().getHeight() / 2;

        g.setColor(Color.RED);
        g.drawRect(x, y, width, 5);
        g.setColor(Color.GREEN);
        g.drawRect(x, y, (int) (width * healthPercentage), 5);
    }

}
