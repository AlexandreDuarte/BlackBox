package com.azokh.blackbox.ui.element.button;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

public class BBListenerButton extends BBButtonText
{
    private final BBListener ffListener;

    public BBListenerButton(int id, int x, int y, String text, BitmapFont font, BBListener ffListener) {
        super(id, x, y, text, font, Align.left);
        this.ffListener = ffListener;
    }

    public BBListenerButton(int id, int x, int y, String text, BitmapFont font, BBListener ffListener, int align) {
        super(id, x, y, text, font, align);
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
