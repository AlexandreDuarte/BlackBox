package com.azkh.blackbox.gamescreen.elements;

import com.azkh.blackbox.Resources;
import com.azkh.blackbox.utils.Carousel;
import com.badlogic.gdx.Gdx;
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

        float cx = bounds.x + bounds.width / 2f;
        float cy = bounds.y + bounds.height / 2f;
        float radius = bounds.width / 2.4f;

        if (active) {
            Resources.game.getShapeRenderer().setColor(Resources.beamColor1);
            Resources.game.getShapeRenderer().circle(cx, cy, radius, 20);
            Resources.game.getShapeRenderer().setColor(Resources.background);
            Resources.game.getShapeRenderer().circle(cx, cy, radius * 0.45f, 16);
            Resources.game.getShapeRenderer().setColor(Resources.board);
        } else {
            switch (state) {
                case CROSSED:
                    Resources.game.getShapeRenderer().setColor(Resources.beamColor1);
                    Resources.game.getShapeRenderer().rectLine(bounds.x + 4, bounds.y + 4, bounds.x + bounds.width - 4, bounds.y + bounds.height - 4, 4);
                    Resources.game.getShapeRenderer().rectLine(bounds.x + 4, bounds.y + bounds.height - 4, bounds.x + bounds.width - 4, bounds.y + 4, 4);
                    Resources.game.getShapeRenderer().setColor(Resources.board);
                    break;
                case NEUTRAL:
                    Resources.game.getShapeRenderer().end();
                    Gdx.gl.glLineWidth(4);
                    Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
                    Resources.game.getShapeRenderer().setColor(Resources.board);
                    Resources.game.getShapeRenderer().rect(bounds.x, bounds.y, bounds.width, bounds.height);
                    Resources.game.getShapeRenderer().end();
                    Gdx.gl.glLineWidth(1);
                    Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
                    Resources.game.getShapeRenderer().setColor(Resources.board);
                    break;
                case FILLED:
                    Resources.game.getShapeRenderer().setColor(Resources.beamColor1);
                    Resources.game.getShapeRenderer().circle(cx, cy, radius, 20);
                    Resources.game.getShapeRenderer().setColor(Resources.background);
                    Resources.game.getShapeRenderer().circle(cx, cy, radius * 0.45f, 16);
                    Resources.game.getShapeRenderer().setColor(Resources.board);
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
