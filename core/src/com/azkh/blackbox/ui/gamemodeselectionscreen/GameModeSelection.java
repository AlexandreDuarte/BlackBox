package com.azkh.blackbox.ui.gamemodeselectionscreen;

import com.azkh.blackbox.GameType;
import com.azkh.blackbox.Resources;
import com.azkh.blackbox.ScreenSelectionInterface;
import com.azkh.blackbox.gameselectionscreen.GameSelectionScreen;
import com.azkh.blackbox.ui.element.Element;
import com.azkh.blackbox.ui.element.button.BBButton;
import com.azkh.blackbox.ui.element.button.BBListener;
import com.azkh.blackbox.ui.element.button.BBListenerButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

public class GameModeSelection implements Element, BBListener, InputProcessor {

    List<BBButton> buttons;
    public static final int DEFAULT_BUTTON = 0;
    public static final int DYNAMIC_BUTTON = 1;
    public static final int BACK_BUTTON = 2;
    ScreenSelectionInterface gameModeSelectionInterface;

    public GameModeSelection(ScreenSelectionInterface gameModeSelectionInterface) {
        buttons = new ArrayList<>();
        BBButton defaultButton = new BBListenerButton(DEFAULT_BUTTON, Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f + Resources.menuFont.getCapHeight(), "DEFAULT", Resources.menuFont, this, Align.center, Align.bottom);
        BBButton dynamicButton = new BBListenerButton(DYNAMIC_BUTTON, Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f - Resources.menuFont.getCapHeight(), "DYNAMIC", Resources.menuFont, this, Align.center, Align.top);
        BBButton backButton = new BBListenerButton(BACK_BUTTON, 40, Gdx.graphics.getHeight() - 40 - Gdx.graphics.getSafeInsetTop(), "BACK", Resources.textFont, this);

        buttons.add(defaultButton);
        buttons.add(dynamicButton);
        buttons.add(backButton);

        this.gameModeSelectionInterface = gameModeSelectionInterface;
    }

    @Override
    public void onClick(BBButton flb) {
        switch (flb.getId()) {
            case DEFAULT_BUTTON:
                gameModeSelectionInterface.setNextScreen(new GameSelectionScreen(GameType.Mode.DEFAULT));
                break;
            case DYNAMIC_BUTTON:
                gameModeSelectionInterface.setNextScreen(new GameSelectionScreen(GameType.Mode.DYNAMIC));
                break;
            case BACK_BUTTON:
                gameModeSelectionInterface.setNextScreen(Resources.game.getMainMenuScreen());
                break;
        }
    }

    @Override
    public void render() {
        Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < buttons.size(); i++) {
            BBButton b = buttons.get(i);
            if (!b.isHidden()) {
                // b.drawBounds(Resources.game.getShapeRenderer());
            }
        }
        Resources.game.getShapeRenderer().end();

        Resources.game.getBatch().begin();
        for (int i = 0; i < buttons.size(); i++) {
            BBButton b = buttons.get(i);
            if (!b.isHidden()) {
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
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).contains(pointerX, pointerY)) {
                if (!buttons.get(i).isHidden()) {
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
