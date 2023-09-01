package com.azokh.blackbox.mainscreen;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ui.element.Element;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class TitleElement implements Element {


    GlyphLayout title1, title2;

    private final int titleDelta;

    public TitleElement() {
        title1 = new GlyphLayout(Resources.titleFont, "BLACK");
        title2 = new GlyphLayout(Resources.titleFont, "BOX");

        titleDelta = (int)((title1.width+title2.width)/2 - title1.width);

    }

    @Override
    public void render() {
        Resources.titleFont.draw(Resources.game.getBatch(), title1, Gdx.graphics.getWidth()/2.0f - (title1.width+title2.width)/2 - 40, 3.0f*Resources.game.getGameHeight()/4);
        Resources.titleFont.draw(Resources.game.getBatch(), title2, Gdx.graphics.getWidth()/2.0f - (title1.width+title2.width)/2 + title1.width + 40, 3.0f*Resources.game.getGameHeight()/4);
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
        return title1.height;
    }

}
