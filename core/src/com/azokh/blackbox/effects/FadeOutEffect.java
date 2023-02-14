package com.azokh.blackbox.effects;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ui.Element;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class FadeOutEffect extends Effect implements Element {

    Color fadeColor;

    boolean start = false;

    public FadeOutEffect() {
        super(0.5f);
    }

    @Override
    public void render() {
        if (!this.isFinished()) {
            fadeColor = Resources.background.cpy().mul(1, 1, 1, this.getModifier());

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Resources.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Resources.shapeRenderer.setColor(fadeColor);
            Resources.shapeRenderer.rect(0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Resources.shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void start() {
        this.start = true;
    }

    @Override
    public void update(float delta) {
        if (start)
            super.update(delta);
    }

    @Override
    public void reset() {
        super.reset();
        start = false;
    }

    @Override
    public void dispose() {

    }
}
