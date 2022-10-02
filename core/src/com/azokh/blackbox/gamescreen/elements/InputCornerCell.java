package com.azokh.blackbox.gamescreen.elements;

import com.azokh.blackbox.Resources;
import com.badlogic.gdx.math.Rectangle;

public class InputCornerCell extends InputBoardCell {
    public InputCornerCell(Rectangle bounds, Direction direction) {
        super(bounds, direction);
    }

    @Override
    public void render() {

        if (active) {
            Resources.shapeRenderer.setColor(Resources.beamColor1);
            //Resources.shapeRenderer.arc(bounds.x + bounds.width/2, bounds.y + bounds.height/2, 4*bounds.width/10, direction.getStart(), direction.getAngle(), direction.getSegments());
            Resources.shapeRenderer.rect(bounds.x + direction.getOffset().x*bounds.getWidth()/2, bounds.y + direction.getOffset().y*bounds.getHeight()/2, direction.getScalemod().x*bounds.getWidth(), direction.getScalemod().y*bounds.getHeight());
            Resources.shapeRenderer.arc(bounds.x + bounds.width/2, bounds.y + bounds.height/2, bounds.width/2, direction.getStart(), direction.getAngle(), direction.getSegments());
            Resources.shapeRenderer.setColor(Resources.board);
        } else {
            Resources.shapeRenderer.setColor(Resources.boardInactive);
            Resources.shapeRenderer.rect(bounds.x + direction.getOffset().x*bounds.getWidth()/2, bounds.y + direction.getOffset().y*bounds.getHeight()/2, direction.getScalemod().x*bounds.getWidth(), direction.getScalemod().y*bounds.getHeight());
            Resources.shapeRenderer.arc(bounds.x + bounds.width/2, bounds.y + bounds.height/2, bounds.width/2, direction.getStart(), direction.getAngle(), direction.getSegments());
            Resources.shapeRenderer.setColor(Resources.board);
        }
    }
}
