package com.azokh.blackbox;

import com.azokh.blackbox.effects.FadeInEffect;
import com.azokh.blackbox.effects.FadeOutEffect;
import com.azokh.blackbox.gamescreen.GameBoard;
import com.azokh.blackbox.gamescreen.GameBoardObserver;
import com.azokh.blackbox.ui.gamescreen.GameMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.ScreenUtils;

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
        gameBoard = new GameBoard(gameType.getBoardSize()+2, gameMenu);
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

        gameBoard.update(delta);
        gameMenu.update(delta);

        ScreenUtils.clear(Resources.background);

        gameMenu.render();

        fadeOut.render();
        fadeOut.update(delta);

        fadeIn.render();
        fadeIn.update(delta);

        gameBoard.render();

        if(gameBoard.getEndGame()) {
            Gdx.input.setInputProcessor(null);
            gameMenu.getTimer().stopTimer();
            fadeOut.start();
        }
        //System.out.println(fadeOut.isFinished());

        if (fadeOut.isFinished()) {
            Resources.game.setScreen(new GameOverScreen(gameMenu.getTimer(), new GameBoardObserver(this.gameBoard)));
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


