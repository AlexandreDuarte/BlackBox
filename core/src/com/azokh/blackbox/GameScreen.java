package com.azokh.blackbox;

import com.azokh.blackbox.effects.Effect;
import com.azokh.blackbox.gamescreen.GameBoard;
import com.azokh.blackbox.ui.gamescreen.GameMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends BBScreen {


    GameMenu gameMenu;
    GameBoard gameBoard;
    InputMultiplexer inputMultiplexer;
    Effect fadeOut;
    Color fadeColor;

    public GameScreen(int boardSize) {
        gameBoard = new GameBoard(boardSize);
        gameMenu = new GameMenu();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gameBoard);
        inputMultiplexer.addProcessor(gameMenu);
        fadeOut = new Effect(1.0f);
    }


    @Override
    public void show() {

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {


        if(!gameBoard.getEndGame()) {

            gameBoard.update(delta);
            gameMenu.update(delta);

            ScreenUtils.clear(Resources.background);

            gameBoard.render();
            gameMenu.render();
        }





        //System.out.println(fadeOut.isFinished());

        if(gameBoard.getEndGame() && !fadeOut.isFinished()) {
            fadeColor = Resources.background.cpy().mul(1, 1, 1, fadeOut.getModifier());
            fadeOut.update(delta);

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Resources.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Resources.shapeRenderer.setColor(fadeColor);
            Resources.shapeRenderer.rect(0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Resources.shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

        if (fadeOut.isFinished()) {
            Resources.game.setScreen(new GameOverScreen(gameMenu.getTimer()));
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


