package com.azokh.blackbox.gamescreen.elements;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ui.element.Element;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

import java.util.Locale;

public class TimerElement implements Element {

    private float timer = 0.0f;
    private boolean stop;
    private boolean lock = false;

    private final BitmapFont font;
    private final float x, y;

    private TimerParse parser;

    private final int glyphOffset;

    public TimerElement(float x, float y, BitmapFont font, boolean stopped) {
        this.x = x;
        this.y = y;
        this.font = font;
        this.glyphOffset = font.getData().getFirstGlyph().xadvance;
        this.stop = stopped;
        this.parser = new TimerParse(0.0f);
    }

    @Override
    public void render() {
        font.draw(Resources.game.getBatch(), parser.millis, x + glyphOffset, y, 2, Align.center, false);
        font.draw(Resources.game.getBatch(), ":", x + glyphOffset/2.0f, y, 1, Align.center, false);
        font.draw(Resources.game.getBatch(), parser.seconds, x, y, 2, Align.center, false);
        font.draw(Resources.game.getBatch(), ":", x - glyphOffset/2.0f, y, 1, Align.center, false);
        font.draw(Resources.game.getBatch(), parser.minutes, x - glyphOffset, y, 2, Align.center, false);
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
        return timer;
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

    private static class TimerParse {
        public String millis;
        public String seconds;
        public String minutes;

        public TimerParse(float time) {
            int intTimer = (int) time;
            this.millis = String.format(Locale.UK,"%02d", (intTimer)%100);
            this.seconds = String.format(Locale.UK, "%02d", (intTimer/100)%60);
            this.minutes = String.format(Locale.UK, "%02d", (intTimer/6000)%60);
        }
    }

}
