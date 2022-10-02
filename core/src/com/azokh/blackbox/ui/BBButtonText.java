package com.azokh.blackbox.ui;

import com.azokh.blackbox.Resources;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;

public abstract class BBButtonText extends BBButton {

    private BitmapFont font;
    private CharSequence text;
    private GlyphLayout unselectedText;
    private GlyphLayout selectedText;

    private float text_x, text_y;

    private int offsetx, offsety;

    public BBButtonText(int id, int x, int y, CharSequence text, BitmapFont font, int align) {
        super(id);


        this.text = text;

        this.font = font;


        unselectedText = new GlyphLayout(font, text, 0, text.length(), font.getColor(), 0, align,
                false, null);
        selectedText = new GlyphLayout(font, text, 0, text.length(), Resources.beamColor1, 0, align,
                false, null);



        this.offsetx = -10;
        this.offsety = (int)unselectedText.height + 10;

        text_x = x+offsetx;
        text_y = Gdx.graphics.getHeight() - (y +offsety);

        switch (align) {
            case Align.center:
                this.offsetx -= unselectedText.width/2;
                break;
            case Align.right:
                this.offsetx -= unselectedText.width;
                break;
        }

        this.bounds = new Rectangle(x+offsetx, Gdx.graphics.getHeight() - (y +offsety), unselectedText.width+20, unselectedText.height+20);
    }

    protected abstract void action();

    @Override
    public void draw(SpriteBatch batch) {
        if(hidden != true)
        {
            if (selected) {
                font.draw(batch, selectedText, text_x+10, text_y+offsety-10);

            } else {
                font.draw(batch, unselectedText, text_x+10, text_y+offsety);
            }

        }
    }
}
