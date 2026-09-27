package com.azkh.blackbox.ui.element.button;

import com.azkh.blackbox.Resources;
import com.azkh.blackbox.ui.element.BBText;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public abstract class BBButtonText extends BBButton {

    private final BBText unselectedBBText;
    private final BBText selectedBBText;

    public BBButtonText(int id, float x, float y, CharSequence text, BitmapFont font, int halign, int valign) {
        super(id);

        unselectedBBText = new BBText(text, font, x, y + 10, Resources.titleColor, halign, valign);
        selectedBBText = new BBText(text, font, x, y, Resources.beamColor1, halign, valign);

        float offsety = 0.0f;
        if ((valign & Align.top) != 0) {
            offsety = unselectedBBText.getHeight();
        } else if ((valign & Align.bottom) != 0) {
            offsety = 0.0f;
        } else if ((valign & Align.center) != 0 || valign == Align.center) {
            offsety = unselectedBBText.getHeight() / 2.0f;
        }

        float offsetx2 = 0.0f;
        if ((halign & Align.right) != 0) {
            offsetx2 = unselectedBBText.getWidth();
        } else if ((halign & Align.center) != 0 || halign == Align.center) {
            offsetx2 = unselectedBBText.getWidth() / 2.0f;
        }

        setBounds(x - offsetx2 - 10, y - offsety - 10, unselectedBBText.getWidth() + 20, unselectedBBText.getHeight() + 20);
    }

    public void setText(CharSequence text) {
        unselectedBBText.setText(text);
        selectedBBText.setText(text);
    }

    protected abstract void action();

    @Override
    public void draw(SpriteBatch batch) {
        if (!hidden) {
            if (selected) {
                selectedBBText.draw(batch);
            } else {
                unselectedBBText.draw(batch);
            }
        }
    }
}
