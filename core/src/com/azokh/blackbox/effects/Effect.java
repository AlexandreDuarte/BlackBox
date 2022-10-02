package com.azokh.blackbox.effects;

public class Effect {
    private float length;
    private float time = 0.0f;
    private boolean finished = false;

    private float modifier = 0.0f;

    public Effect(float length) {
        this.length = length;
    }

    public void update(float delta) {
        if (finished) return;

        time += delta;
        if (time >= length) {
            finished = true;
            modifier = 1.0f;
            return;
        }
        modifier = time / length;
    }

    public float getModifier() {
        return modifier;
    }

    public boolean isFinished() {
        return finished;
    }
}