package com.tdproject.inputs;

import java.awt.event.MouseEvent;

public class MyEvent {
    private MouseEvent event;

    public MyEvent(MouseEvent event) {
        this.event = event;
    }

    public int getX() {
        return event.getX();
    }

    public int getY() {
        return event.getY();
    }
}
