package com.tdproject.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Text {

    public final static int DEFAULT_FONT_SIZE = 30;
    public final static Color DEFAULT_COLOR = Color.WHITE;
    public final static int DEFAULT_FONT_STYLE = Font.PLAIN;

    private String string;
    private int fontSize;
    private Color color;
    private int fontStyle;

    private int x, y;

    public Text(String string) {
        this(string, DEFAULT_FONT_SIZE, DEFAULT_COLOR, DEFAULT_FONT_STYLE, 0, 0);
    }

    public Text(String string, int x, int y) {
        this(string, DEFAULT_FONT_SIZE, DEFAULT_COLOR, DEFAULT_FONT_STYLE, x, y);
    }

    public Text(String string, int fontSize, Color color, int fontStyle) {
        this(string, fontSize, color, fontStyle, 0, 0);
    }

    public Text(String string, int fontSize, Color color, int fontStyle, int x, int y) {
        this.string = string;
        this.fontSize = fontSize;
        this.color = color;
        this.fontStyle = fontStyle;
        this.x = x;
        this.y = y;
    }

    public void draw(Object g) {
        setFont(g);
        ((Graphics)g).drawString(string, x, y);
    }

    private void setFont(Object o) {
        Graphics g = (Graphics) o;
        g.setFont(new Font("TimesRoman", fontStyle, fontSize));
        g.setColor(color);
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
