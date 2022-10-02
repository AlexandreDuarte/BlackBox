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
        Resources.camera.viewportWidth = width;
        Resources.camera.viewportHeight = height;
        Resources.camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Resources.batch.flush();
    }

    @Override
    public void dispose() {

    }
}
