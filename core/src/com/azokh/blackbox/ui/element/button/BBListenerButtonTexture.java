package com.azokh.blackbox.ui.element.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BBListenerButtonTexture extends BBButtonTexture{
    private final BBListener ffListener;

    public BBListenerButtonTexture(int id, float x, float y, Texture texture1, Texture texture2, BBListener ffListener) {
        super(id, x, y, texture1, texture2);
        this.ffListener = ffListener;
    }

    public BBListenerButtonTexture(int id, float x, float y, Texture texture, BBListener ffListener) {
        super(id, x, y, texture);
        this.ffListener = ffListener;
    }

    @Override
    protected void action() {
    ffListener.onClick(this);
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {

    }
}
