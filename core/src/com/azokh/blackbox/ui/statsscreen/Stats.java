package com.azokh.blackbox.ui.statsscreen;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ScreenSelectionInterface;
import com.azokh.blackbox.ui.element.Element;
import com.azokh.blackbox.ui.element.button.BBButton;
import com.azokh.blackbox.ui.element.button.BBButtonLeftArrow;
import com.azokh.blackbox.ui.element.button.BBButtonRightArrow;
import com.azokh.blackbox.ui.element.button.BBListener;
import com.azokh.blackbox.ui.element.button.BBListenerButton;
import com.azokh.blackbox.utils.ListCarousel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Stats implements Element, BBListener, InputProcessor {

    enum StatViews {
        fourfour("4x4"), fivefive("5x5"), sixsix("6x6");

        final String title;

        StatViews(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    ScreenSelectionInterface screenSelectionInterface;
    List<BBButton> buttons;
    public static final int LEFT_BUTTON = 0;
    public static final int RIGHT_BUTTON = 1;
    public static final int BACK_BUTTON = 2;
    public static final int GOOGLE_PLAY_BUTTON = 3;

    StatViews currentStatView = StatViews.fourfour;

    GlyphLayout currentStatViewTitle;

    ListIterator<StatViews> carousel = (new ListCarousel<StatViews>(currentStatView)).iterator();

    public Stats(ScreenSelectionInterface screenSelectionInterface) {
        this.screenSelectionInterface = screenSelectionInterface;

        currentStatViewTitle = new GlyphLayout(Resources.menuFont, currentStatView.getTitle());


        buttons = new ArrayList<>();
        BBButtonLeftArrow leftButton = new BBButtonLeftArrow(LEFT_BUTTON, Gdx.graphics.getWidth() / 5, Resources.game.getGameHeight() / 5 + (int)currentStatViewTitle.height, this);
        BBButtonRightArrow rightButton = new BBButtonRightArrow(RIGHT_BUTTON, 4*Gdx.graphics.getWidth() / 5, Resources.game.getGameHeight() / 5 + (int)currentStatViewTitle.height, this);
        //BBButton exitButton = new BBListenerButton(ABOUT_BUTTON, Gdx.graphics.getWidth() / 2 - offset - 80, Resources.game.getGameHeight() / 2 + (int)(5*startButton.height()/4) + (int)(5*statsButton.height()/4), "ABOUT", Resources.textFont, this, Align.right);
        BBButton backButton = new BBListenerButton(BACK_BUTTON, 40, 40, "BACK", Resources.textFont, this);




        buttons.add(leftButton);
        buttons.add(rightButton);
        buttons.add(backButton);
        //buttons.add(exitButton);
    }

    @Override
    public void onClick(BBButton flb) {

        switch (flb.getId()) {
            case LEFT_BUTTON:
                currentStatView = carousel.previous();
                currentStatViewTitle.setText(Resources.menuFont, currentStatView.getTitle());
                break;
            case RIGHT_BUTTON:
                currentStatView = carousel.next();
                currentStatViewTitle.setText(Resources.menuFont, currentStatView.getTitle());
                break;
            case BACK_BUTTON:
                screenSelectionInterface.setNextScreen(Resources.game.getMainMenuScreen());
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
                b.draw(Resources.game.getShapeRenderer());
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
        Resources.menuFont.draw(Resources.game.getBatch(), currentStatViewTitle, Gdx.graphics.getWidth()/2.0f - currentStatViewTitle.width/2, 4.0f*Resources.game.getGameHeight()/5.0f);

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
