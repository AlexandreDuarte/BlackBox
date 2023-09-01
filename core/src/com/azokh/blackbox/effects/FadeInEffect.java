package com.azokh.blackbox.effects;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ui.element.Element;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class FadeInEffect extends Effect implements Element {

    Color fadeColor;

    public FadeInEffect() {
        super(0.5f);
    }

    @Override
    public void render() {
        if (!this.isFinished()) {
            fadeColor = Resources.background.cpy().mul(1, 1, 1, 1.0f - this.getModifier());

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
            Resources.game.getShapeRenderer().setColor(fadeColor);
            Resources.game.getShapeRenderer().rect(0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Resources.game.getShapeRenderer().end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void dispose() {

    }
}
