package com.azokh.blackbox.ui.element.button;

import com.azokh.blackbox.Resources;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public abstract class BBButtonText extends BBButton {

    private final BitmapFont font;
    private CharSequence text;
    private final GlyphLayout unselectedText;
    private final GlyphLayout selectedText;

    private final float text_x, text_y;

    public BBButtonText(int id, float x, float y, CharSequence text, BitmapFont font, int align) {
        super(id);

        //this.x = Resources.

        this.text = text;

        this.font = font;


        unselectedText = new GlyphLayout(font, text, 0, text.length(), font.getColor(), 0, align,
                false, null);
        selectedText = new GlyphLayout(font, text, 0, text.length(), Resources.beamColor1, 0, align,
                false, null);


        float offsetx = -10;
        float offsety = unselectedText.height;

        float offsetx2 = 0.0f;
        switch (align) {
            case Align.center:
                offsetx2 = unselectedText.width/2;
                break;
            case Align.right:
                offsetx2 = unselectedText.width;
                break;
        }

        setBounds(x + offsetx - offsetx2, y + 3*offsety/2.0f, unselectedText.width+20, unselectedText.height+20);

        text_x = getBounds().getX() + offsetx2;
        text_y = getBounds().getY()+offsety;
    }

    protected abstract void action();

    @Override
    public void draw(SpriteBatch batch) {
        if(!hidden)
        {
            if (selected) {
                font.draw(batch, selectedText, text_x+10, text_y);

            } else {
                font.draw(batch, unselectedText, text_x+10, text_y+10);
            }

        }
    }
}
