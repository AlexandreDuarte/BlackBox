package com.azkh.blackbox.gamescreen.elements;


import com.azkh.blackbox.Resources;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class EmptyBoardCell extends BoardCell {

    public EmptyBoardCell(Rectangle bounds) {
        setBounds(bounds);
    }

    @Override
    public void execute() {

    }

    @Override
    public void render() {
        Resources.game.getShapeRenderer().end();
        Gdx.gl.glLineWidth(4);
        Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
        Resources.game.getShapeRenderer().setColor(Resources.board.cpy().mul(0.5f));
        Resources.game.getShapeRenderer().rect(bounds.x, bounds.y, bounds.width, bounds.height);
        Resources.game.getShapeRenderer().end();
        Gdx.gl.glLineWidth(1);
        Resources.game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        Resources.game.getShapeRenderer().setColor(Resources.board);
    }

    @Override
    public void dispose() {

    }
}
