package com.azkh.blackbox.effects;

import com.azkh.blackbox.Resources;
import com.azkh.blackbox.ui.element.Element;

public class FadeOutEffect extends Effect implements Element {

    boolean start = false;

    public FadeOutEffect() {
        super(0.5f);
    }

    public float getAlpha() {
        if (!start) {
            return 1.0f;
        }
        if (this.isFinished()) {
            return 0.0f;
        }
        return 1.0f - this.getModifier();
    }

    public void start() {
        this.start = true;
    }

    public boolean isStarted() {
        return start;
    }

    @Override
    public void render() {
        Resources.game.getBatch().setColor(1.0f, 1.0f, 1.0f, Resources.screenAlpha);
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
