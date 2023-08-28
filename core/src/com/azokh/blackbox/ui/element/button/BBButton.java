package com.azokh.blackbox.ui.element.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class BBButton {

    protected Rectangle bounds;
    protected boolean selected;
    protected boolean hidden;
    protected boolean active;
    protected boolean disabled;



    private final int id;

    public BBButton(int id) {

        this.hidden = false;
        this.active = false;
        this.disabled = false;
        this.id = id;

    }

    protected abstract void action();

    public int getId() {
        return id;
    }

    public void execute()
    {
        if(!disabled)
        {
            action();
        }
    }


    public boolean contains(float x, float y)
    {
        return bounds.contains(x, y);
    }

    public float width()
    {
        return bounds.width;
    }

    public float height()
    {
        return bounds.height;
    }

    public abstract void draw(SpriteBatch batch);

    public abstract void draw(ShapeRenderer shapeRenderer);

    public boolean getSelected()
    {
        return selected;
    }

    public BBButton setSelected(boolean selected)
    {
        this.selected = selected;
        return this;
    }

    public boolean isActive()
    {
        return active;
    }

    public BBButton setActive(boolean active)
    {
        this.active = active;
        return this;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public BBButton setHidden(boolean hidden)
    {
        this.hidden = hidden;
        return this;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public boolean isDisabled()
    {
        return disabled;
    }

    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }
}
