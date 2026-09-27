package com.azkh.blackbox.gameselectionscreen;

import com.azkh.blackbox.BBScreen;
import com.azkh.blackbox.GameType;
import com.azkh.blackbox.Resources;
import com.azkh.blackbox.ScreenSelectionInterface;
import com.azkh.blackbox.effects.FadeInEffect;
import com.azkh.blackbox.effects.FadeOutEffect;
import com.azkh.blackbox.ui.gameselectionscreen.GameSelection;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameSelectionScreen extends BBScreen implements ScreenSelectionInterface {

    GameSelection gameSelection;
    BeamGameSelectionScreen beamSelectionScreen;
    FadeInEffect fadeIn;
    FadeOutEffect fadeOut;

    Screen nextScreen = null;

    public GameSelectionScreen(GameType.Mode mode) {
        gameSelection = new GameSelection(this, mode);
        Gdx.input.setInputProcessor(gameSelection);
        this.fadeIn = new FadeInEffect();
        this.fadeOut = new FadeOutEffect();
    }

    public GameSelectionScreen() {
        this(GameType.Mode.DEFAULT);
    }

    public void setNextScreen(Screen screen) {
        nextScreen = screen;
        fadeOut.start();
    }

    @Override
    public void show() {
        Resources.game.getBatch().flush();
        Gdx.input.setInputProcessor(gameSelection);
        beamSelectionScreen = new BeamGameSelectionScreen(Resources.beamColor1, Resources.beamColor2);
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

        beamSelectionScreen.update(delta);
        Resources.game.getCamera().update();
        Resources.game.getShapeRenderer().setProjectionMatrix(Resources.game.getCamera().combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        beamSelectionScreen.render();
        Resources.game.getShapeRenderer().end();

        gameSelection.render();

        fadeIn.render();
        fadeOut.render();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        if (fadeOut.isFinished()) {
            Resources.game.setScreen(nextScreen);
        }
    }

    @Override
    public void dispose() {
        gameSelection.dispose();
    }
}
