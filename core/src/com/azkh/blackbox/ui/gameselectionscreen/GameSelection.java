package com.azkh.blackbox.ui.gameselectionscreen;

import com.azkh.blackbox.GameType;
import com.azkh.blackbox.GameTypes;
import com.azkh.blackbox.Resources;
import com.azkh.blackbox.ScreenSelectionInterface;
import com.azkh.blackbox.gamemodeselectionscreen.GameModeSelectionScreen;
import com.azkh.blackbox.gamescreen.GameScreen;
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

public class GameSelection implements Element, BBListener, InputProcessor {

    List<BBButton> buttons;
    public static final int FOUR_BUTTON = 0;
    public static final int FIVE_BUTTON = 1;
    public static final int SIX_BUTTON = 2;
    public static final int BACK_BUTTON = 3;
    ScreenSelectionInterface gameSelectionInterface;
    GameType.Mode mode;

    public GameSelection(ScreenSelectionInterface gameSelectionInterface, GameType.Mode mode) {
        this.gameSelectionInterface = gameSelectionInterface;
        this.mode = mode;

        buttons = new ArrayList<>();
        BBButton backButton = new BBListenerButton(BACK_BUTTON, 40, Gdx.graphics.getHeight() - 40 - Gdx.graphics.getSafeInsetTop(), "BACK", Resources.textFont, this);
        BBButton sixButton = new BBListenerButton(FIVE_BUTTON, Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, "5x5", Resources.menuFont, this, Align.center, Align.center);
        BBButton fourButton = new BBListenerButton(FOUR_BUTTON, Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f + (int) (3 * sixButton.height() / 2), "4x4", Resources.menuFont, this, Align.center, Align.center);
        BBButton eightButton = new BBListenerButton(SIX_BUTTON, Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f - (int) (3 * sixButton.height() / 2), "6x6", Resources.menuFont, this, Align.center, Align.center);

        buttons.add(fourButton);
        buttons.add(sixButton);
        buttons.add(eightButton);
        buttons.add(backButton);
    }

    public GameSelection(ScreenSelectionInterface gameSelectionInterface) {
        this(gameSelectionInterface, GameType.Mode.DEFAULT);
    }

    @Override
    public void onClick(BBButton flb) {
        switch (flb.getId()) {
            case FOUR_BUTTON:
                gameSelectionInterface.setNextScreen(new GameScreen(mode == GameType.Mode.DYNAMIC ? GameTypes.dynamic44 : GameTypes.fourfour));
                break;
            case FIVE_BUTTON:
                gameSelectionInterface.setNextScreen(new GameScreen(mode == GameType.Mode.DYNAMIC ? GameTypes.dynamic55 : GameTypes.fivefive));
                break;
            case SIX_BUTTON:
                gameSelectionInterface.setNextScreen(new GameScreen(mode == GameType.Mode.DYNAMIC ? GameTypes.dynamic66 : GameTypes.sixsix));
                break;
            case BACK_BUTTON:
                gameSelectionInterface.setNextScreen(new GameModeSelectionScreen());
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
