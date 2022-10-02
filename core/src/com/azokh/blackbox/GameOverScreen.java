package com.azokh.blackbox;

import com.azokh.blackbox.gamescreen.elements.TimerElement;
import com.azokh.blackbox.ui.gameoverscreen.GameOver;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen extends BBScreen {

    GameOver gameOver;

    TimerElement timer;

    public GameOverScreen(TimerElement timer) {
        gameOver = new GameOver();
        this.timer = timer;
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
    }
}
