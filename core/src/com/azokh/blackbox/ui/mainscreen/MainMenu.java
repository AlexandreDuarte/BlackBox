package com.azokh.blackbox.ui.mainscreen;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ScreenSelectionInterface;
import com.azokh.blackbox.gameselectionscreen.GameSelectionScreen;
import com.azokh.blackbox.statsscreen.StatsScreen;
import com.azokh.blackbox.ui.element.Element;
import com.azokh.blackbox.ui.element.button.BBButton;
import com.azokh.blackbox.ui.element.button.BBListener;
import com.azokh.blackbox.ui.element.button.BBListenerButton;
import com.azokh.blackbox.ui.element.button.BBListenerButtonTexture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.mazatech.gdx.SVGTexture;
import com.mazatech.svgt.SVGAlignment;
import com.mazatech.svgt.SVGColor;
import com.mazatech.svgt.SVGDocument;
import com.mazatech.svgt.SVGTAlign;
import com.mazatech.svgt.SVGTMeetOrSlice;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Element, BBListener, InputProcessor {


    ScreenSelectionInterface screenSelectionInterface;
    List<BBButton> buttons;
    public static final int START_BUTTON = 0;
    public static final int STATS_BUTTON = 1;
    public static final int ABOUT_BUTTON = 2;
    public static final int GOOGLE_PLAY_BUTTON = 3;

    SVGDocument googlePlay, googlePlaySelected;
    SVGTexture googlePlayTexture, googlePlayTextureSelected;


    public MainMenu(ScreenSelectionInterface screenSelectionInterface, int offset) {
        this.screenSelectionInterface = screenSelectionInterface;
        buttons = new ArrayList<>();
        BBButton startButton = new BBListenerButton(START_BUTTON, Gdx.graphics.getWidth() / 2.0f - offset - 60, Resources.game.getGameHeight() / 2.0f, "START", Resources.menuFont, this, Align.right);
        BBButton statsButton = new BBListenerButton(STATS_BUTTON, Gdx.graphics.getWidth() / 2.0f - offset - 60, Resources.game.getGameHeight() / 2.0f + 3*startButton.height()/2, "STATS", Resources.textFont, this, Align.right);
        //BBButton exitButton = new BBListenerButton(ABOUT_BUTTON, Gdx.graphics.getWidth() / 2 - offset - 80, Gdx.graphics.getHeight() / 2 + (int)(5*startButton.height()/4) + (int)(5*statsButton.height()/4), "ABOUT", Resources.textFont, this, Align.right);

        googlePlay = Resources.game.getSvg().createDocumentFromFile("google-play.svg");
        googlePlay.setAspectRatio(new SVGAlignment(SVGTAlign.XMidYMid,
                SVGTMeetOrSlice.Slice));

        googlePlaySelected = Resources.game.getSvg().createDocumentFromFile("google-play-selected.svg");
        googlePlaySelected.setAspectRatio(new SVGAlignment(SVGTAlign.XMidYMid,
                SVGTMeetOrSlice.Slice));

        googlePlayTexture = Resources.game.getSvg().createTexture(googlePlay,
                (((Gdx.graphics.getWidth()/16)*16)/20), (((Gdx.graphics.getWidth()/16)*16)/20),
                SVGColor.Clear, false);

        googlePlayTextureSelected = Resources.game.getSvg().createTexture(googlePlaySelected,
                (((Gdx.graphics.getWidth()/16)*16)/20), (((Gdx.graphics.getWidth()/16)*16)/20),
                SVGColor.Clear, false);

        BBButton googlePlayButton = new BBListenerButtonTexture(GOOGLE_PLAY_BUTTON, Gdx.graphics.getWidth()/2.0f, Resources.game.getGameHeight() - 100, googlePlayTexture, googlePlayTextureSelected, this);

        buttons.add(startButton);
        buttons.add(statsButton);
        //buttons.add(exitButton);
        buttons.add(googlePlayButton);
    }

    @Override
    public void onClick(BBButton flb) {

        switch (flb.getId()) {
            case START_BUTTON:
                screenSelectionInterface.setNextScreen(new GameSelectionScreen());
                break;
            case STATS_BUTTON:
                screenSelectionInterface.setNextScreen(new StatsScreen());
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
        for(int i = 0; i < buttons.size(); i++)
        {
            BBButton b = buttons.get(i);
            if(!b.isHidden())
            {
                //b.drawBounds(Resources.game.getShapeRenderer());
            }
        }
        Resources.game.getShapeRenderer().end();



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
        googlePlay.dispose();
        googlePlaySelected.dispose();
        googlePlayTexture.dispose();
        googlePlayTextureSelected.dispose();
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
