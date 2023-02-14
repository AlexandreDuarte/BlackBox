package com.azokh.blackbox;

import com.azokh.blackbox.gameselectionscreen.BeamGameSelectionScreen;
import com.azokh.blackbox.ui.gameselectionscreen.GameSelection;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameSelectionScreen extends BBScreen {

    //TitleElement titleElement;
    //BeamMainMenuScreen beamMainMenu;

    GameSelection gameSelection;
    BeamGameSelectionScreen beamSelectionScreen;

    public GameSelectionScreen() {

        gameSelection = new GameSelection(Resources.game);
        Gdx.input.setInputProcessor(gameSelection);

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
    }

    @Override
    public void dispose() {
        gameSelection.dispose();
    }
}
