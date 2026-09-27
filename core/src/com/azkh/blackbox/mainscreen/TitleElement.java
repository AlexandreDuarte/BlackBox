package com.azkh.blackbox.mainscreen;

import com.azkh.blackbox.Resources;
import com.azkh.blackbox.ui.element.BBText;
import com.azkh.blackbox.ui.element.Element;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;

public class TitleElement implements Element {

    BBText title1Text, title2Text;
    private final int titleDelta;

    public TitleElement() {



        float y = 3.0f * Gdx.graphics.getHeight() / 4.0f;
        float x1 = Gdx.graphics.getWidth() / 2.0f + 40;
        float x2 = Gdx.graphics.getWidth() / 2.0f + 130;

        title1Text = new BBText("BLACK", Resources.titleFont, x1, y, Resources.titleColor, Align.right, Align.top);
        title2Text = new BBText("BOX", Resources.titleFont, x2, y, Resources.titleColor, Align.left, Align.top);

        titleDelta = (int) (((title1Text.getWidth() + title2Text.getWidth()) / 2.0f) - title1Text.getWidth());
    }

    @Override
    public void render() {
        title1Text.draw(Resources.game.getBatch());
        title2Text.draw(Resources.game.getBatch());
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {

    }

    public int getTitleDelta() {
        return titleDelta;
    }

    public float getHeight() {
        return title1Text.getHeight();
    }
}
