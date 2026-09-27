package com.azkh.blackbox.mainscreen;

import com.azkh.blackbox.BBScreen;
import com.azkh.blackbox.Resources;
import com.azkh.blackbox.ScreenSelectionInterface;
import com.azkh.blackbox.effects.FadeInEffect;
import com.azkh.blackbox.effects.FadeOutEffect;
import com.azkh.blackbox.ui.mainscreen.MainMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
        fadeIn.update(delta);
        fadeOut.update(delta);

        if (fadeOut.isStarted()) {
            Resources.screenAlpha = fadeOut.getAlpha();
        } else {
            Resources.screenAlpha = fadeIn.getAlpha();
        }

        beamMainMenu.update(delta);
        Resources.game.getCamera().update();
        Resources.game.getShapeRenderer().setProjectionMatrix(Resources.game.getCamera().combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        beamMainMenu.render();
        Resources.game.getShapeRenderer().end();

        Resources.game.getBatch().begin();
        Resources.game.getBatch().setColor(1.0f, 1.0f, 1.0f, Resources.screenAlpha);
        titleElement.render();
        Resources.game.getBatch().end();

        menu.render();

        fadeIn.render();
        fadeOut.render();

        Gdx.gl.glDisable(GL20.GL_BLEND);

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
