package com.azokh.blackbox.gameselectionscreen;

import com.azokh.blackbox.BBScreen;
import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ScreenSelectionInterface;
import com.azokh.blackbox.effects.FadeInEffect;
import com.azokh.blackbox.effects.FadeOutEffect;
import com.azokh.blackbox.ui.gameselectionscreen.GameSelection;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameSelectionScreen extends BBScreen implements ScreenSelectionInterface {

    //TitleElement titleElement;
    //BeamMainMenuScreen beamMainMenu;

    GameSelection gameSelection;
    BeamGameSelectionScreen beamSelectionScreen;
    FadeInEffect fadeIn;
    FadeOutEffect fadeOut;

    Screen nextScreen = null;

    public GameSelectionScreen() {

        gameSelection = new GameSelection(this);
        Gdx.input.setInputProcessor(gameSelection);
        this.fadeIn = new FadeInEffect();
        this.fadeOut = new FadeOutEffect();
    }

    public void setNextScreen(Screen screen) {
        nextScreen = screen;
        fadeOut.start();
    }

    @Override
    public void show() {
        Resources.batch.flush();
        Gdx.input.setInputProcessor(gameSelection);
        beamSelectionScreen = new BeamGameSelectionScreen(0, 0, Resources.beamColor1, Resources.beamColor2);

        //beamMainMenu = new BeamMainMenuScreen(titleElement.getTitleDelta(), (int)titleElement.getHeight(), Resources.beamColor1, Resources.beamColor2);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Resources.background);

        beamSelectionScreen.update(delta);
        Resources.camera.update();
        Resources.shapeRenderer.setProjectionMatrix(Resources.camera.combined);

        Resources.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        beamSelectionScreen.render();
        Resources.shapeRenderer.end();
/*
        Resources.batch.begin();
        //titleElement.render();
        Resources.batch.end();
*/
        gameSelection.render();

        fadeIn.render();
        fadeIn.update(delta);

        fadeOut.render();
        fadeOut.update(delta);

        if (fadeOut.isFinished()) {
            Resources.game.setScreen(nextScreen);
        }

    }

    @Override
    public void dispose() {
        gameSelection.dispose();
    }
}
