package com.tdproject.graphics;

import java.awt.Font;
import java.awt.Graphics;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// This class has specific PC functionality
public class Text {

    public final static int DEFAULT_FONT_SIZE = 30;
    public final static Color DEFAULT_COLOR = Color.WHITE;
    public final static Integer DEFAULT_FONT_STYLE = Font.PLAIN;

    private String string;
    private int fontSize;
    private Color color;

    private int x, y;

    public Text(String string) {
        this(string, DEFAULT_FONT_SIZE, DEFAULT_COLOR, 0, 0);
    }

    public Text(String string, int x, int y) {
        this(string, DEFAULT_FONT_SIZE, DEFAULT_COLOR, x, y);
    }

    public Text(String string, int fontSize, Color color) {
        this(string, fontSize, color, 0, 0);
    }

    public Text(String string, int fontSize, Color color, int x, int y) {
        this.string = string;
        this.fontSize = fontSize;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public void draw(Object g) {
        setFont(g);
        ((Graphics)g).drawString(string, x, y);
    }

    private void setFont(Object o) {
        Graphics g = (Graphics) o;
        g.setFont(new Font("TimesRoman", DEFAULT_FONT_STYLE, fontSize));
        g.setColor(switch (color) {
            case WHITE -> java.awt.Color.WHITE;
            case RED -> java.awt.Color.RED;
        });
    }

    public enum Color {
        WHITE,
        RED
    }

}
