package com.azkh.blackbox.ui.element.button;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

public class BBListenerButton extends BBButtonText
{
    private final BBListener ffListener;

    public BBListenerButton(int id, float x, float y, String text, BitmapFont font, BBListener ffListener) {
        super(id, x, y, text, font, Align.left, Align.center);
        this.ffListener = ffListener;
    }

    public BBListenerButton(int id, float x, float y, String text, BitmapFont font, BBListener ffListener, int halign, int valign) {
        super(id, x, y, text, font, halign, valign);
        this.ffListener = ffListener;
    }

    @Override
    protected void action()
    {
        ffListener.onClick(this);
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {

    }
}
