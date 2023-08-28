package com.azokh.blackbox.gameoverscreen;

import com.azokh.blackbox.BBScreen;
import com.azokh.blackbox.GameType;
import com.azokh.blackbox.Resources;
import com.azokh.blackbox.effects.FadeInEffect;
import com.azokh.blackbox.effects.FadeOutEffect;
import com.azokh.blackbox.gamescreen.GameBoardObserver;
import com.azokh.blackbox.gamescreen.elements.TimerElement;
import com.azokh.blackbox.ui.gameoverscreen.GameOver;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen extends BBScreen {

    GameOver gameOver;

    TimerElement timer;
    FadeInEffect fadeIn;
    FadeOutEffect fadeOut;

    public GameOverScreen(GameType gameType, TimerElement timer, GameBoardObserver gameRealBoard) {
        gameOver = new GameOver(gameType, timer.getScore(), gameRealBoard);
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

        if (gameOver.exit) {
            fadeOut.start();
        }

        if (fadeOut.isFinished()) {
            Resources.game.setScreen(Resources.mainMenuScreen);
        }
    }
}
