package com.azokh.blackbox.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class BBButtonTexture extends BBButton {

    private Rectangle bounds;
    private boolean selected;
    private boolean hidden;
    private boolean active;
    private boolean disabled;

    private Texture texture;

    private float text_x, text_y;

    private int offsetx, offsety;

    public BBButtonTexture(int id, int x, int y, Texture texture) {
        super(id);
        this.hidden = false;
        this.active = false;
        this.disabled = false;

        this.offsetx = -texture.getWidth()/2;
        this.offsety = texture.getHeight()/2;

        this.texture = texture;

        this.bounds = new Rectangle(x+offsetx, Gdx.graphics.getHeight() - (y +offsety), texture.getWidth(), texture.getHeight());
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

    public void draw(SpriteBatch batch)
    {
        if(hidden != true)
        {
            //TODO
            //if (selected) {
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height, 0, 0, texture.getWidth(), texture.getHeight(), false, true);


        }
    }

    public boolean getSelected()
    {
        return selected;
    }

    public BBButtonTexture setSelected(boolean selected)
    {
        this.selected = selected;
        return this;
    }

    public boolean isActive()
    {
        return active;
    }

    public BBButtonTexture setActive(boolean active)
    {
        this.active = active;
        return this;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public BBButtonTexture setHidden(boolean hidden)
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
