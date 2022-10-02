package com.azokh.blackbox;

import com.azokh.blackbox.mainscreen.BeamMainMenuScreen;
import com.azokh.blackbox.ui.mainscreen.MainMenu;
import com.azokh.blackbox.ui.mainscreen.TitleElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen extends BBScreen {

    TitleElement titleElement;
    BeamMainMenuScreen beamMainMenu;

    MainMenu menu;

    public MainMenuScreen() {

        titleElement = new TitleElement();

        menu = new MainMenu(Resources.game, titleElement.getTitleDelta());
        Gdx.input.setInputProcessor(menu);

    }

    @Override
    public void show() {
        Resources.batch.flush();
        Gdx.input.setInputProcessor(menu);
        beamMainMenu = new BeamMainMenuScreen(titleElement.getTitleDelta(), (int)titleElement.getHeight(), Resources.beamColor1, Resources.beamColor2);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Resources.background);

        beamMainMenu.update(delta);
        Resources.camera.update();
        Resources.shapeRenderer.setProjectionMatrix(Resources.camera.combined);

        Resources.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        beamMainMenu.render();
        Resources.shapeRenderer.end();

        Resources.batch.begin();
        titleElement.render();
        Resources.batch.end();

        menu.render();
    }

    @Override
    public void dispose() {
        menu.dispose();
    }
}
