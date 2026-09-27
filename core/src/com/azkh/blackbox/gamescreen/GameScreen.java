package com.azkh.blackbox.gamescreen;

import com.azkh.blackbox.BBScreen;
import com.azkh.blackbox.gameoverscreen.GameOverScreen;
import com.azkh.blackbox.GameType;
import com.azkh.blackbox.Resources;
import com.azkh.blackbox.effects.FadeInEffect;
import com.azkh.blackbox.effects.FadeOutEffect;
import com.azkh.blackbox.ui.gamescreen.GameMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen extends BBScreen {

    GameMenu gameMenu;
    GameBoard gameBoard;
    InputMultiplexer inputMultiplexer;
    FadeOutEffect fadeOut;
    FadeInEffect fadeIn;

    GameType gameType;

    public GameScreen(GameType gameType) {
        this.gameType = gameType;
        gameMenu = new GameMenu();
        gameBoard = new GameBoard(gameType.getBoardSize()+2, gameMenu.getTimer());
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gameBoard);
        inputMultiplexer.addProcessor(gameMenu);
        fadeOut = new FadeOutEffect();
        fadeIn = new FadeInEffect();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
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

        gameBoard.update(delta);
        gameMenu.update(delta);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        gameMenu.render();
        gameBoard.render();

        fadeIn.render();
        fadeOut.render();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        if(gameBoard.getEndGame()) {
            Gdx.input.setInputProcessor(null);
            gameMenu.getTimer().stopTimer();
            fadeOut.start();
        }

        if (fadeOut.isFinished()) {
            Resources.game.setScreen(new GameOverScreen(gameType, gameMenu.getTimer(), new GameBoardObserver(this.gameBoard)));
        }
    }

    @Override
    public void pause() {
        super.pause();
        gameMenu.pause();
    }

    @Override
    public void resume() {
        super.resume();
        gameMenu.resume();
    }
}
