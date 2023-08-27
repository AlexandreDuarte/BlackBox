package com.azokh.blackbox.ui.gamescreen;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.gamescreen.elements.TimerElement;
import com.azokh.blackbox.ui.BBButton;
import com.azokh.blackbox.ui.BBListener;
import com.azokh.blackbox.ui.BBListenerButton;
import com.azokh.blackbox.ui.Element;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class GameMenu implements Element, BBListener, InputProcessor {

    List<BBButton> buttons;

    private final TimerElement timer;

    public static final int BACK_BUTTON = 0;
    public static final int VALIDATE_BUTTON = 1;

    public GameMenu() {
        this.timer = new TimerElement(Gdx.graphics.getWidth()/2, 4*Gdx.graphics.getHeight()/5, Resources.textFont, true);
        buttons = new ArrayList<BBButton>();
        BBButton backButton = new BBListenerButton(BACK_BUTTON, 40, 40, "BACK", Resources.textFont, this);
        //BBButton validateButton = new BBListenerButton(VALIDATE_BUTTON, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()*4/5, "VALIDATE", Resources.textFont, this, Align.center);
        buttons.add(backButton);
        //buttons.add(validateButton);
    }

    @Override
    public void onClick(BBButton flb) {
        switch (flb.getId()) {
            case BACK_BUTTON:
                Resources.game.setScreen(Resources.mainMenuScreen);
                break;
        }
    }

    @Override
    public void render() {
        Resources.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(int i = 0; i < buttons.size(); i++)
        {
            BBButton b = buttons.get(i);
            if(!b.isHidden())
            {
                //b.drawBounds(Resources.shapeRenderer);
            }
        }
        Resources.shapeRenderer.end();



        Resources.batch.begin();
        for(int i = 0; i < buttons.size(); i++)
        {
            BBButton b = buttons.get(i);
            if(!b.isHidden())
            {
                b.draw(Resources.batch);
            }
        }
        this.timer.render();
        Resources.batch.end();
    }

    public TimerElement getTimer() {
        return timer;
    }

    @Override
    public void update(float delta) {
        //if (gameBoard.hasGameStarted()) {
        //    timer.resumeTimer();
        //}
        timer.update(delta);
    }

    @Override
    public void dispose() {

    }

    public void resume() {
        timer.resumeTimer();
    }

    public void pause() {
        timer.stopTimer();
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

