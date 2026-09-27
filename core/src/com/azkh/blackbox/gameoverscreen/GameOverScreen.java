package com.azkh.blackbox.gameoverscreen;

import com.azkh.blackbox.BBScreen;
import com.azkh.blackbox.GameType;
import com.azkh.blackbox.Resources;
import com.azkh.blackbox.effects.FadeInEffect;
import com.azkh.blackbox.effects.FadeOutEffect;
import com.azkh.blackbox.gamescreen.GameBoardObserver;
import com.azkh.blackbox.gamescreen.elements.TimerElement;
import com.azkh.blackbox.ui.gameoverscreen.GameOver;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

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
        fadeIn.update(delta);
        fadeOut.update(delta);

        if (fadeOut.isStarted()) {
            Resources.screenAlpha = fadeOut.getAlpha();
        } else {
            Resources.screenAlpha = fadeIn.getAlpha();
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Resources.game.getBatch().begin();
        Resources.game.getBatch().setColor(1.0f, 1.0f, 1.0f, Resources.screenAlpha);
        timer.render();
        Resources.game.getBatch().end();

        gameOver.render();

        fadeIn.render();
        fadeOut.render();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        if (gameOver.exit) {
            fadeOut.start();
        }

        if (fadeOut.isFinished()) {
            Resources.game.setScreen(Resources.game.getMainMenuScreen());
        }
    }
}
