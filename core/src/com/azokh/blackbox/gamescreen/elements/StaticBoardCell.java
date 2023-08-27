package com.azokh.blackbox.gamescreen.elements;

import com.azokh.blackbox.ui.Element;
import com.badlogic.gdx.math.Rectangle;

public abstract class StaticBoardCell implements Element {

    Rectangle bounds;

    boolean active;

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void render() {

    }

    @Override
    public void update(float delta) {

    }
}
