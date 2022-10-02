package com.azokh.blackbox.ui;

import com.badlogic.gdx.graphics.Texture;

public class BBListenerButtonTexture extends BBButtonTexture{
    private BBListener ffListener;

    public BBListenerButtonTexture(int id, int x, int y, Texture texture, BBListener ffListener) {
        super(id, x, y, texture);
        this.ffListener = ffListener;
    }

    @Override
    protected void action() {
    ffListener.onClick(this);
    }
}
