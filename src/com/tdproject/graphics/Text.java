package com.tdproject.graphics;

import java.awt.*;

public class Text {

    private String string;
    private int x, y;

    public Text(String string, int x, int y) {
        this.string = string;
        this.x = x;
        this.y = y;
    }

    public void draw(Object g) {
        ((Graphics)g).drawString(string, x, y);
    }

    public static void setFont(Object o) {
        Graphics g = (Graphics)o;
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.setColor(Color.WHITE);
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
