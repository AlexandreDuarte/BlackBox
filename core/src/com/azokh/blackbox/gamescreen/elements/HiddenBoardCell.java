package com.azokh.blackbox.gamescreen.elements;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.utils.Carousel;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class HiddenBoardCell extends BoardCell {

    public enum State {
        NEUTRAL, FILLED, CROSSED
    }

    State state = State.NEUTRAL;

    Iterator<State> carousel = (new Carousel<State>(state)).iterator();

    public HiddenBoardCell(Rectangle bounds) {
        setBounds(bounds);
    }

    @Override
    public void execute() {
        state = carousel.next();
        setActive(state == State.FILLED);
    }

    @Override
    public void render() {



        if (active) {
            Resources.game.getShapeRenderer().rect(bounds.x, bounds.y, bounds.width, bounds.height);
        } else {
            switch (state) {
                case CROSSED:
                    Resources.game.getShapeRenderer().rectLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height, 1);
                    Resources.game.getShapeRenderer().rectLine(bounds.x, bounds.y + bounds.height, bounds.x + bounds.width, bounds.y, 1);
                case NEUTRAL:
                    Resources.game.getShapeRenderer().end();
                    Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
                    Resources.game.getShapeRenderer().setColor(Resources.board);
                    Resources.game.getShapeRenderer().rect(bounds.x, bounds.y, bounds.width, bounds.height);
                    Resources.game.getShapeRenderer().end();
                    Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
                    Resources.game.getShapeRenderer().setColor(Resources.board);
                    break;
                case FILLED:
                    Resources.game.getShapeRenderer().rect(bounds.x, bounds.y, bounds.width, bounds.height);
                    break;
            }
        }
    }

    public State getState() {
        return state;
    }


    @Override
    public void dispose() {

    }

}
