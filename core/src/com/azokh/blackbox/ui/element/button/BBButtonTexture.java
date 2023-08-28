package com.azokh.blackbox.ui.element.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class BBButtonTexture extends BBButton {

    private final Rectangle[] bounds;
    private boolean selected;
    private boolean hidden;
    private boolean active;
    private boolean disabled;

    private final Texture[] texture;

    public BBButtonTexture(int id, int x, int y, Texture texture) {
        this(id, x, y, texture, texture);
    }

    public BBButtonTexture(int id, int x, int y, Texture texture1, Texture texture2) {
        super(id);
        this.hidden = false;
        this.active = false;
        this.disabled = false;

        int[] offsetx = new int[]{-texture1.getWidth() / 2, -texture2.getWidth() / 2};
        int[] offsety = new int[]{texture1.getHeight() / 2, texture2.getHeight() / 2};

        this.texture = new Texture[]{texture1, texture2};

        this.bounds = new Rectangle[]{
                new Rectangle(x+ offsetx[0], Gdx.graphics.getHeight() - (y + offsety[0]), texture[0].getWidth(), texture[0].getHeight()),
                new Rectangle(x+ offsetx[1], Gdx.graphics.getHeight() - (y + offsety[1]), texture[1].getWidth(), texture[1].getHeight())
        };
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
        return bounds[selected? 1 : 0].contains(x, y);
    }

    public float x()
    {
        return bounds[selected? 1 : 0].x;
    }

    public float y()
    {
        return bounds[selected? 1 : 0].y;
    }

    public float width()
    {
        return bounds[selected? 1 : 0].width;
    }

    public float height()
    {
        return bounds[selected? 1 : 0].height;
    }

    public void draw(SpriteBatch batch)
    {
        if(!hidden)
        {
            if (selected) {
                batch.draw(texture[1], bounds[1].x, bounds[1].y, bounds[1].width, bounds[1].height, 0, 0, texture[1].getWidth(), texture[1].getHeight(), false, true);
            }
            batch.draw(texture[0], bounds[0].x, bounds[0].y, bounds[0].width, bounds[0].height, 0, 0, texture[0].getWidth(), texture[0].getHeight(), false, true);


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
        return bounds[selected? 1 : 0];
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
