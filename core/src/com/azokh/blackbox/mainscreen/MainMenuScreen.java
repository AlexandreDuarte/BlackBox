package com.azokh.blackbox.mainscreen;

import com.azokh.blackbox.BBScreen;
import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ScreenSelectionInterface;
import com.azokh.blackbox.effects.FadeInEffect;
import com.azokh.blackbox.effects.FadeOutEffect;
import com.azokh.blackbox.ui.mainscreen.MainMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen extends BBScreen implements ScreenSelectionInterface {

    TitleElement titleElement;
    BeamMainMenuScreen beamMainMenu;
    FadeInEffect fadeIn;
    FadeOutEffect fadeOut;

    MainMenu menu;

    Screen nextScreen;

    public MainMenuScreen() {

        titleElement = new TitleElement();

        menu = new MainMenu(this, titleElement.getTitleDelta());
        Gdx.input.setInputProcessor(menu);

        fadeIn = new FadeInEffect();
        fadeOut = new FadeOutEffect();

    }

    @Override
    public void show() {
        Resources.game.getBatch().flush();
        Gdx.input.setInputProcessor(menu);
        beamMainMenu = new BeamMainMenuScreen(titleElement.getTitleDelta(), (int)titleElement.getHeight(), Resources.beamColor1, Resources.beamColor2);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Resources.background);

        beamMainMenu.update(delta);
        Resources.game.getCamera().update();
        Resources.game.getShapeRenderer().setProjectionMatrix(Resources.game.getCamera().combined);

        Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        beamMainMenu.render();
        Resources.game.getShapeRenderer().end();

        Resources.game.getBatch().begin();
        titleElement.render();
        Resources.game.getBatch().end();

        menu.render();

        fadeIn.render();
        fadeIn.update(delta);

        fadeOut.render();
        fadeOut.update(delta);

        if (fadeOut.isFinished()) {
            fadeOut.reset();
            fadeIn.reset();
            Resources.game.setScreen(nextScreen);
        }

    }

    @Override
    public void dispose() {
        menu.dispose();
    }

    @Override
    public void setNextScreen(Screen screen) {
        this.nextScreen = screen;
        fadeOut.start();
    }
}
