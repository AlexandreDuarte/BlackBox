package com.azkh.blackbox.leaderboardscreen;

import com.azkh.blackbox.BBScreen;
import com.azkh.blackbox.Resources;
import com.azkh.blackbox.ScreenSelectionInterface;
import com.azkh.blackbox.effects.FadeInEffect;
import com.azkh.blackbox.effects.FadeOutEffect;
import com.azkh.blackbox.ui.leaderboardscreen.LeaderboardUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class LeaderboardScreen extends BBScreen implements ScreenSelectionInterface {

    FadeInEffect fadeIn;
    FadeOutEffect fadeOut;

    LeaderboardUI leaderboardUI;

    BBScreen nextScreen = Resources.game.getMainMenuScreen();


    public LeaderboardScreen() {
        leaderboardUI = new LeaderboardUI(this);
        Gdx.input.setInputProcessor(leaderboardUI);

        this.fadeIn = new FadeInEffect();
        this.fadeOut = new FadeOutEffect();
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

        Resources.game.getCamera().update();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        leaderboardUI.render();

        fadeIn.render();
        fadeOut.render();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        if (fadeOut.isFinished()) {
            Resources.game.setScreen(nextScreen);
        }
    }

    @Override
    public void setNextScreen(Screen screen) {
        this.nextScreen = (BBScreen) screen;
        fadeOut.start();
    }

    @Override
    public void dispose() {
        leaderboardUI.dispose();
    }
}
