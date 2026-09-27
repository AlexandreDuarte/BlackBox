package com.azkh.blackbox.effects;

import com.azkh.blackbox.Resources;
import com.azkh.blackbox.ui.element.Element;

public class FadeInEffect extends Effect implements Element {

    public FadeInEffect() {
        super(0.5f);
    }

    public float getAlpha() {
        if (this.isFinished()) {
            return 1.0f;
        }
        return this.getModifier();
    }

    @Override
    public void render() {
        Resources.game.getBatch().setColor(1.0f, 1.0f, 1.0f, Resources.screenAlpha);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void dispose() {

    }
}
