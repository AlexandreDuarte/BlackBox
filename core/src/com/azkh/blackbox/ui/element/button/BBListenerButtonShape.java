package com.azkh.blackbox.ui.element.button;

public abstract class BBListenerButtonShape extends BBButtonShape
{
    private final BBListener ffListener;


    public BBListenerButtonShape(int id, float x, float y, float width, float height, int halign, int valign, BBListener ffListener) {
        super(id, x, y, width, height, halign, valign);
        this.ffListener = ffListener;

    }

    @Override
    protected void action()
    {
        ffListener.onClick(this);
    }
}
