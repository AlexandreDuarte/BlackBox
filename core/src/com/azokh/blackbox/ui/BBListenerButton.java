package com.azokh.blackbox.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

public class BBListenerButton extends BBButtonText
{
    private BBListener ffListener;

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
}
