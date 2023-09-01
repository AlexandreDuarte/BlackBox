package com.azokh.blackbox;

import com.badlogic.gdx.Screen;

public class BBScreen implements Screen {
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        Resources.game.getCamera().viewportWidth = width;
        Resources.game.getCamera().viewportHeight = height;
        Resources.game.getCamera().update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Resources.game.getBatch().flush();
    }

    @Override
    public void dispose() {

    }
}
