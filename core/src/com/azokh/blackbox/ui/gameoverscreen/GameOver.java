package com.azokh.blackbox.ui.gameoverscreen;

import com.azokh.blackbox.GameType;
import com.azokh.blackbox.Resources;
import com.azokh.blackbox.data.GameStatEntry;
import com.azokh.blackbox.gamescreen.GameBoardObserver;
import com.azokh.blackbox.ui.element.button.BBButton;
import com.azokh.blackbox.ui.element.button.BBListener;
import com.azokh.blackbox.ui.element.button.BBListenerButton;
import com.azokh.blackbox.ui.element.Element;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

public class GameOver implements Element, BBListener, InputProcessor {


    List<BBButton> buttons;

    public static final int BACK_BUTTON = 0;

    GameBoardObserver gameRealBoard;

    public boolean exit = false;

    public GameOver(GameType gameType, float gameScore, GameBoardObserver gameRealBoard) {
        this.gameRealBoard = gameRealBoard;
        Resources.gsClient.updateLeaderboard(gameType.getLeaderboard(), new GameStatEntry((long)(gameScore*1000)));
        buttons = new ArrayList<>();
        BBButton backButton = new BBListenerButton(BACK_BUTTON, Gdx.graphics.getWidth()/2, 4*Resources.game.getGameHeight()/5, "BACK", Resources.textFont, this, Align.center);
        buttons.add(backButton);
    }

    @Override
    public void onClick(BBButton flb) {
        this.exit = true;
    }

    @Override
    public void render() {
        Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        for(int i = 0; i < buttons.size(); i++)
        {
            BBButton b = buttons.get(i);
            if(!b.isHidden())
            {
                //b.drawBounds(Resources.game.getShapeRenderer());
            }
        }
        Resources.game.getShapeRenderer().end();


        this.gameRealBoard.render();

        Resources.game.getBatch().begin();
        for(int i = 0; i < buttons.size(); i++)
        {
            BBButton b = buttons.get(i);
            if(!b.isHidden())
            {
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {

        float pointerX = screenX;
        float pointerY = Gdx.graphics.getHeight() - screenY;
        for(int i = 0; i < buttons.size(); i++)
        {
            if(buttons.get(i).contains(pointerX, pointerY))
            {
                if(!buttons.get(i).isHidden())
                {
                    buttons.get(i).setSelected(true);
                }
                return true;
            }
        }
        return false;
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
        return false;
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

