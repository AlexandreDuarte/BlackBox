package com.azokh.blackbox.gamescreen.elements;


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

    }

    @Override
    public void dispose() {

    }
}
