package com.azokh.blackbox.ui.element.button;

public abstract class BBListenerButtonShape extends BBButtonShape
{
    private final BBListener ffListener;


    public BBListenerButtonShape(int id, float x, float y, float width, float height, BBListener ffListener) {
        super(id, x, y, width, height);
        this.ffListener = ffListener;

    }

    @Override
    protected void action()
    {
        ffListener.onClick(this);
    }
}
