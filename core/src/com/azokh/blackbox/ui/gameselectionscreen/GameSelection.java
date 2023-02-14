package com.azokh.blackbox.ui.gameselectionscreen;


import com.azokh.blackbox.GameScreen;
import com.azokh.blackbox.ScreenSelectionInterface;
import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ui.BBButton;
import com.azokh.blackbox.ui.BBListener;
import com.azokh.blackbox.ui.BBListenerButton;
import com.azokh.blackbox.ui.Element;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

public class GameSelection implements Element, BBListener, InputProcessor {


    List<BBButton> buttons;
    public static final int FOUR_BUTTON = 0;
    public static final int SIX_BUTTON = 1;
    public static final int EIGHT_BUTTON = 2;
    public static final int BACK_BUTTON = 3;
    ScreenSelectionInterface gameSelectionInterface;

    public GameSelection(ScreenSelectionInterface gameSelectionInterface) {
        buttons = new ArrayList<BBButton>();
        BBButton backButton = new BBListenerButton(BACK_BUTTON, 40, 40, "BACK", Resources.textFont, this);
        //BBButton validateButton = new BBListenerButton(VALIDATE_BUTTON, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()*4/5, "VALIDATE", Resources.textFont, this, Align.center);
        BBButton fourButton = new BBListenerButton(FOUR_BUTTON, Gdx.graphics.getWidth() / 2, 2*Gdx.graphics.getHeight() / 5, "4x4", Resources.menuFont, this, Align.center);
        BBButton sixButton = new BBListenerButton(SIX_BUTTON, Gdx.graphics.getWidth() / 2, 2*Gdx.graphics.getHeight() / 5 + (int)(3*fourButton.height()/2), "6x6", Resources.menuFont, this, Align.center);
        BBButton eightButton = new BBListenerButton(EIGHT_BUTTON, Gdx.graphics.getWidth() / 2, 2*Gdx.graphics.getHeight() / 5 + (int)(3*fourButton.height()/2) + (int)(3*sixButton.height()/2), "8x8", Resources.menuFont, this, Align.center);

        buttons.add(fourButton);
        buttons.add(sixButton);
        buttons.add(eightButton);
        buttons.add(backButton);

        this.gameSelectionInterface = gameSelectionInterface;
    }

    @Override
    public void onClick(BBButton flb) {

        switch (flb.getId()) {
            case FOUR_BUTTON:

                gameSelectionInterface.setNextScreen(new GameScreen(6));
                break;
            case SIX_BUTTON:
                gameSelectionInterface.setNextScreen(new GameScreen(8));
                break;
            case EIGHT_BUTTON:
                gameSelectionInterface.setNextScreen(new GameScreen(10));
                break;
            case BACK_BUTTON:
                gameSelectionInterface.setNextScreen(Resources.mainMenuScreen);
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
        Resources.batch.end();
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
