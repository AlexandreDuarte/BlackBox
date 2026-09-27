package com.azkh.blackbox.ui.mainscreen;

import com.azkh.blackbox.Resources;
import com.azkh.blackbox.ScreenSelectionInterface;
import com.azkh.blackbox.gamemodeselectionscreen.GameModeSelectionScreen;
import com.azkh.blackbox.leaderboardscreen.LeaderboardScreen;
import com.azkh.blackbox.ui.element.Element;
import com.azkh.blackbox.ui.element.button.BBButton;
import com.azkh.blackbox.ui.element.button.BBListener;
import com.azkh.blackbox.ui.element.button.BBListenerButton;
import com.azkh.blackbox.ui.element.button.BBListenerButtonTexture;
import com.azkh.blackbox.ui.util.VectorDrawable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Element, BBListener, InputProcessor {

    ScreenSelectionInterface screenSelectionInterface;
    List<BBButton> buttons;
    public static final int START_BUTTON = 0;
    public static final int LEADERBOARD_BUTTON = 4;
    public static final int ABOUT_BUTTON = 2;
    public static final int GOOGLE_PLAY_BUTTON = 3;

    Texture googlePlayTexture, googlePlayTextureSelected;

    public MainMenu(ScreenSelectionInterface screenSelectionInterface, int offset) {
        this.screenSelectionInterface = screenSelectionInterface;
        buttons = new ArrayList<>();
        BBButton startButton = new BBListenerButton(START_BUTTON, Gdx.graphics.getWidth() / 2.0f - offset - 60, Gdx.graphics.getHeight() / 2.0f, "START", Resources.menuFont, this, Align.right, Align.center);
        BBButton leaderboardButton = new BBListenerButton(LEADERBOARD_BUTTON, Gdx.graphics.getWidth() / 2.0f - offset - 60, Gdx.graphics.getHeight() / 2.0f - 3*startButton.height()/2, "LEADERBOARD", Resources.textFont, this, Align.right, Align.center);

        int btnSize = (((Gdx.graphics.getWidth() / 16) * 16) / 20);
        if (btnSize <= 0) btnSize = 64;

        googlePlayTexture = VectorDrawable.createTextureFromFile(Gdx.files.internal("ic_google_play.xml"), btnSize, btnSize);
        googlePlayTextureSelected = VectorDrawable.createTextureFromFile(Gdx.files.internal("ic_google_play_selected.xml"), btnSize, btnSize);

        BBButton googlePlayButton = new BBListenerButtonTexture(GOOGLE_PLAY_BUTTON, Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight() - 100, googlePlayTexture, googlePlayTextureSelected, this);

        buttons.add(startButton);
        buttons.add(leaderboardButton);
        buttons.add(googlePlayButton);
    }

    @Override
    public void onClick(BBButton flb) {
        switch (flb.getId()) {
            case START_BUTTON:
                screenSelectionInterface.setNextScreen(new GameModeSelectionScreen());
                break;
            case LEADERBOARD_BUTTON:
                screenSelectionInterface.setNextScreen(new LeaderboardScreen());
                break;
            case ABOUT_BUTTON:
                break;
            case GOOGLE_PLAY_BUTTON:
                Gdx.app.log("GPGS", "Attempted to start logIn sequence");
                Resources.gsClient.auth();
                break;
        }
    }

    @Override
    public void render() {
        Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        for(int i = 0; i < buttons.size(); i++) {
            BBButton b = buttons.get(i);
            if(!b.isHidden()) {
                //b.drawBounds(Resources.game.getShapeRenderer());
            }
        }
        Resources.game.getShapeRenderer().end();

        Resources.game.getBatch().begin();
        for(int i = 0; i < buttons.size(); i++) {
            BBButton b = buttons.get(i);
            if(!b.isHidden()) {
                b.draw(Resources.game.getBatch());
            }
        }
        Resources.game.getBatch().end();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        if (googlePlayTexture != null) googlePlayTexture.dispose();
        if (googlePlayTextureSelected != null) googlePlayTextureSelected.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float pointerX = screenX;
        float pointerY = Gdx.graphics.getHeight() - screenY;
        for(int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).contains(pointerX, pointerY)) {
                if(!buttons.get(i).isHidden()) {
                    buttons.get(i).setSelected(true);
                }
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        float pointerX = screenX;
        float pointerY = Gdx.graphics.getHeight() - screenY;
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).contains(pointerX, pointerY) && buttons.get(i).getSelected()) {
                buttons.get(i).execute();
            }
            buttons.get(i).setSelected(false);
        }
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
