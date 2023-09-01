package com.azokh.blackbox.gamescreen.elements;

import com.azokh.blackbox.Resources;
import com.badlogic.gdx.math.Rectangle;

public class ObserverBoardCell extends StaticBoardCell {

    public ObserverBoardCell(Rectangle bounds, boolean active) {
        setBounds(bounds);
        this.active = active;
    }

    public ObserverBoardCell(BoardCell boardCell) {
        this(boardCell.getBounds(), boardCell.active);
    }



    @Override
    public void render() {
        if (active) {
            Resources.game.getShapeRenderer().rect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    @Override
    public void dispose() {

    }
}
