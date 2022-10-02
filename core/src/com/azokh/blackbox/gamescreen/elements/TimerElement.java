package com.azokh.blackbox.gamescreen.elements;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ui.Element;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

public class TimerElement implements Element {

    private float timer = 0.0f;
    private boolean stop = false;
    private boolean lock = false;

    private CharSequence text;
    private final BitmapFont font;
    private final int x, y;

    private TimerParse parser;

    private int glyphOffset;

    public TimerElement(int x, int y, BitmapFont font) {
        this.x = x;
        this.y = y;
        this.font = font;
        this.glyphOffset = font.getData().getFirstGlyph().xadvance;
        this.stop = false;
        this.parser = new TimerParse(0.0f);
    }

    @Override
    public void render() {
        font.draw(Resources.batch, parser.millis, x + glyphOffset, y, 2, Align.center, false);
        font.draw(Resources.batch, ":", x + glyphOffset/2, y, 1, Align.center, false);
        font.draw(Resources.batch, parser.seconds, x, y, 2, Align.center, false);
        font.draw(Resources.batch, ":", x - glyphOffset/2, y, 1, Align.center, false);
        font.draw(Resources.batch, parser.minutes, x - glyphOffset, y, 2, Align.center, false);
    }

    @Override
    public void update(float delta) {
        if(!stop) {
            timer+=delta*100;
            parser = new TimerParse(timer);
        }
    }

    @Override
    public void dispose() {

    }

    public float getScore() {
        return (int) timer;
    }

    public void stopTimer() {
        if (!lock) this.stop = true;
    }

    public void resumeTimer() {
        if (!lock) this.stop = false;
    }

    public void lock() {
        stopTimer();
        lock = true;
    }

    private class TimerParse {
        public String millis;
        public String seconds;
        public String minutes;

        public TimerParse(float time) {
            int intTimer = (int) time;
            this.millis = String.format("%02d", (intTimer)%100);
            this.seconds = String.format("%02d", (intTimer/100)%60);
            this.minutes = String.format("%02d", (intTimer/6000)%60);
        }
    }

}
