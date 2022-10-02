package com.azokh.blackbox.gamescreen.elements;

import com.azokh.blackbox.Resources;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class InputBoardCell extends BoardCell{

    Direction direction;

    public InputBoardCell(Rectangle bounds, Direction direction) {
        this.direction = direction;
        setBounds(bounds);
    }

    @Override
    public void execute() {
        setActive(!isActive());
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
            Resources.shapeRenderer.rect(bounds.x + direction.getOffset().x*bounds.getWidth()/2, bounds.y + direction.getOffset().y*bounds.getHeight()/2, direction.getScalemod().x*bounds.getWidth(), direction.getScalemod().y*bounds.getHeight());
            Resources.shapeRenderer.arc(bounds.x + bounds.width/2, bounds.y + bounds.height/2, bounds.width/2, direction.getStart(), direction.getAngle(), direction.getSegments());
        }

    }

    @Override
    public void dispose() {

    }


    public enum Direction {
        UP(180.0f, 180.0f, new Vector2(0.0f, 1.0f), new Vector2(1.0f, 0.5f), 8),
        DOWN(0.0f, 180.0f, new Vector2(0.0f, 0.0f), new Vector2(1.0f, 0.5f), 8),
        RIGHT(90.0f, 180.0f, new Vector2(1.0f, 0.0f), new Vector2(0.5f, 1.0f), 8),
        LEFT(270.0f, 180.0f, new Vector2(0.0f, 0.0f), new Vector2(0.5f, 1.0f), 8),
        UP_LEFT(0.0f, 270.0f, new Vector2(1.0f, 0.0f), new Vector2(0.5f, 0.5f), 10),
        UP_RIGHT(270.0f, 270.0f, new Vector2(0.0f, 0.0f), new Vector2(0.5f, 0.5f), 10),
        DOWN_LEFT(90.0f, 270.0f, new Vector2(1.0f, 1.0f), new Vector2(0.5f, 0.5f), 10),
        DOWN_RIGHT(180.0f, 270.0f, new Vector2(0.0f, 1.0f), new Vector2(0.5f, 0.5f), 10);

        private final float start, angle;
        private final int segments;
        private final Vector2 offset, scalemod;

        Direction(float start, float angle, Vector2 offset, Vector2 scalemod, int segments) {
            this.start = start;
            this.angle = angle;
            this.segments = segments;
            this.offset = offset;
            this.scalemod = scalemod;
        }

        public static Direction getDirection(boolean side, boolean horizontal) {
            if (horizontal) {
                if (side) {
                    return RIGHT;
                }
                return LEFT;
            } else {
                if (side) {
                    return UP;
                }
                return DOWN;
            }
        }

        public static Direction getCornerDirection(boolean down, boolean left) {
            if (down) {
                if (left) {
                    return DOWN_LEFT;
                } else {
                    return DOWN_RIGHT;
                }
            } else {
                if (left) {
                    return UP_LEFT;
                } else {
                    return UP_RIGHT;
                }
            }
        }

        public Vector2 getOffset() {
            return offset;
        }

        public Vector2 getScalemod() {
            return scalemod;
        }

        public float getAngle() {
            return angle;
        }

        public float getStart() {
            return start;
        }

        public int getSegments() {
            return segments;
        }
    }
}
