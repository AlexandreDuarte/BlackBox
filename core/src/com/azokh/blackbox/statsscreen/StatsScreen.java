package com.azokh.blackbox.statsscreen;

import com.azokh.blackbox.BBScreen;
import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ScreenSelectionInterface;
import com.azokh.blackbox.effects.FadeInEffect;
import com.azokh.blackbox.effects.FadeOutEffect;
import com.azokh.blackbox.ui.statsscreen.Stats;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class StatsScreen extends BBScreen implements ScreenSelectionInterface {


    FadeInEffect fadeIn;
    FadeOutEffect fadeOut;

    Stats stats;

    BBScreen nextScreen = Resources.game.getMainMenuScreen();

    public StatsScreen() {

        stats = new Stats(this);
        Gdx.input.setInputProcessor(stats);

        this.fadeIn = new FadeInEffect();
        this.fadeOut = new FadeOutEffect();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Resources.background);

        Resources.game.getCamera().update();

        stats.render();

        fadeIn.render();
        fadeIn.update(delta);

        fadeOut.render();
        fadeOut.update(delta);

        if (fadeOut.isFinished()) {
            Resources.game.setScreen(nextScreen);
        }
    }

    @Override
    public void setNextScreen(Screen screen) {
        fadeOut.start();
    }

    @Override
    public void dispose() {
        stats.dispose();
    }
}
