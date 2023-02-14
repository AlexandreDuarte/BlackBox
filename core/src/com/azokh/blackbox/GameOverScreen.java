package com.azokh.blackbox;

import com.azokh.blackbox.effects.FadeInEffect;
import com.azokh.blackbox.effects.FadeOutEffect;
import com.azokh.blackbox.gamescreen.elements.TimerElement;
import com.azokh.blackbox.ui.gameoverscreen.GameOver;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen extends BBScreen {

    GameOver gameOver;

    TimerElement timer;
    FadeInEffect fadeIn;
    FadeOutEffect fadeOut;

    public GameOverScreen(TimerElement timer) {
        gameOver = new GameOver();
        this.timer = timer;
        this.fadeIn = new FadeInEffect();
        this.fadeOut = new FadeOutEffect();
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(gameOver);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Resources.background);

        Resources.batch.begin();
        timer.render();
        Resources.batch.end();

        gameOver.render();

        fadeIn.render();
        fadeIn.update(delta);

        fadeOut.render();
        fadeOut.update(delta);
    }
}
