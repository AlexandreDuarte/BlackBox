package com.azokh.blackbox.ui.element.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class BBButtonShape extends BBButton {

    private final Rectangle bounds;
    private boolean selected;
    private boolean hidden;
    private boolean active;
    private boolean disabled;


    public BBButtonShape(int id, int x, int y, int width, int height) {
        super(id);
        this.hidden = false;
        this.active = false;
        this.disabled = false;



        this.bounds = new Rectangle(x, Gdx.graphics.getHeight() - (y), width, height);
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

    public float x()
    {
        return bounds.x;
    }

    public float y()
    {
        return bounds.y;
    }

    public float width()
    {
        return bounds.width;
    }

    public float height()
    {
        return bounds.height;
    }

    public void draw(ShapeRenderer shapeRenderer)
    {
        if(!hidden)
        {
            if (selected) {
                drawSelected(shapeRenderer);
            } else {
                drawUnselected(shapeRenderer);
            }
        }
    }

    public abstract void drawSelected(ShapeRenderer shapeRenderer);

    public abstract void drawUnselected(ShapeRenderer shapeRenderer);

    public void draw(SpriteBatch spriteBatch)
    {
    }

    public boolean getSelected()
    {
        return selected;
    }

    public BBButtonShape setSelected(boolean selected)
    {
        this.selected = selected;
        return this;
    }

    public boolean isActive()
    {
        return active;
    }

    public BBButtonShape setActive(boolean active)
    {
        this.active = active;
        return this;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public BBButtonShape setHidden(boolean hidden)
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
