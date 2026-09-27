package com.azkh.blackbox.gamescreen.elements;

import com.azkh.blackbox.Resources;
import com.azkh.blackbox.renderer.BBShapeRendererHelper;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class InputBoardCell extends BoardCell{

    Direction direction;

    boolean noaction;

    public InputBoardCell(Rectangle bounds, Direction direction, boolean noaction) {
        this.direction = direction;
        this.noaction = noaction;
        setBounds(bounds);
    }

    @Override
    public void execute() {
        if (noaction) return;
        setActive(!isActive());
    }

    @Override
    public void render() {

        if (noaction) {
            if (active)
            {
                Resources.game.getShapeRenderer().setColor(Resources.beamColor1);
            }
            else
            {
                Resources.game.getShapeRenderer().setColor(Resources.boardInactive);
            }
            Resources.game.getShapeRenderer().rect(bounds.x + direction.getOffset().x*bounds.getWidth()/2, bounds.y + direction.getOffset().y*bounds.getHeight()/2, direction.getScalemod().x*bounds.getWidth(), direction.getScalemod().y*bounds.getHeight());
            Resources.game.getShapeRenderer().arc(bounds.x + bounds.width/2, bounds.y + bounds.height/2, bounds.width/2, direction.getStart(), direction.getAngle(), direction.getSegments());
            Resources.game.getShapeRenderer().setColor(Resources.board);
            return;
        }

        float cx = bounds.x + bounds.width / 2f;
        float cy = bounds.y + bounds.height / 2f;
        float arrowSize = bounds.width * 0.22f;

        if (active) {
            Resources.game.getShapeRenderer().setColor(Resources.beamColor1);
            Resources.game.getShapeRenderer().rect(bounds.x + direction.getOffset().x*bounds.getWidth()/2, bounds.y + direction.getOffset().y*bounds.getHeight()/2, direction.getScalemod().x*bounds.getWidth(), direction.getScalemod().y*bounds.getHeight());
            Resources.game.getShapeRenderer().arc(bounds.x + bounds.width/2, bounds.y + bounds.height/2, bounds.width/2, direction.getStart(), direction.getAngle(), direction.getSegments());
            Resources.game.getShapeRenderer().setColor(Resources.background);
            drawArrowhead(cx, cy, arrowSize, direction, true);
            Resources.game.getShapeRenderer().setColor(Resources.board);
        } else {

            float line_width = 4f;

            float x = bounds.x + direction.getOffset().x * bounds.getWidth() / 2 + line_width / 2;
            float y = bounds.y + direction.getOffset().y * bounds.getHeight() / 2 + line_width / 2;


            float x_w = bounds.x + direction.getOffset().x * bounds.getWidth() / 2;
            float y_h = bounds.y + direction.getOffset().y * bounds.getHeight() / 2;

            float x_offset = direction.getScalemod().x * bounds.getWidth() - line_width;
            float y_offset = direction.getScalemod().y * bounds.getHeight() - line_width;

            float width = x_offset + line_width / 2;
            float height = y_offset + line_width / 2;


            switch (direction) {
                case LEFT:
                    Resources.game.getShapeRenderer().rectLine(x_w, y, x + width, y, line_width);
                    Resources.game.getShapeRenderer().rectLine(x_w, y + y_offset, x + width, y + y_offset, line_width);
                    Resources.game.getShapeRenderer().rectLine(x, y_h, x, y + height, line_width);
                    break;
                case RIGHT:
                    Resources.game.getShapeRenderer().rectLine(x_w, y, x + width, y, line_width);
                    Resources.game.getShapeRenderer().rectLine(x_w, y + y_offset, x + width, y + y_offset, line_width);
                    Resources.game.getShapeRenderer().rectLine(x + x_offset, y_h, x + x_offset, y + height, line_width);
                    break;
                case DOWN:
                    Resources.game.getShapeRenderer().rectLine(x_w, y, x + width, y, line_width);
                    Resources.game.getShapeRenderer().rectLine(x, y_h, x, y + height, line_width);
                    Resources.game.getShapeRenderer().rectLine(x + x_offset, y_h, x + x_offset, y + height, line_width);
                    break;
                case UP:
                    Resources.game.getShapeRenderer().rectLine(x_w, y + y_offset, x + width, y + y_offset, line_width);
                    Resources.game.getShapeRenderer().rectLine(x, y_h, x, y + height, line_width);
                    Resources.game.getShapeRenderer().rectLine(x + x_offset, y_h, x + x_offset, y + height, line_width);
                    break;
                case UP_RIGHT:
                    Resources.game.getShapeRenderer().rectLine(x, y_h, x, y + height, line_width);
                    Resources.game.getShapeRenderer().rectLine(x_w, y, x + width, y, line_width);
                    break;
                case UP_LEFT:
                    Resources.game.getShapeRenderer().rectLine(x_w, y, x + width, y, line_width);
                    Resources.game.getShapeRenderer().rectLine(x + x_offset, y_h, x + x_offset, y + height, line_width);
                    break;
                case DOWN_LEFT:
                    Resources.game.getShapeRenderer().rectLine(x_w, y + y_offset, x + width, y + y_offset, line_width);
                    Resources.game.getShapeRenderer().rectLine(x + x_offset, y_h, x + x_offset, y + height, line_width);
                    break;
                case DOWN_RIGHT:
                    Resources.game.getShapeRenderer().rectLine(x_w, y + y_offset, x + width, y + y_offset, line_width);
                    Resources.game.getShapeRenderer().rectLine(x, y_h, x, y + height, line_width);

            }
            BBShapeRendererHelper.arc(Resources.game.getShapeRenderer(), bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, bounds.width / 2, direction.getStart(), direction.getAngle(), direction.getSegments(), line_width);

            Resources.game.getShapeRenderer().setColor(Resources.board);
            drawArrowhead(cx, cy, arrowSize, direction, true);
        }
    }

    private void drawArrowhead(float cx, float cy, float size, Direction dir, boolean filled) {
        float tipX = cx, tipY = cy;
        float base1X = cx, base1Y = cy;
        float base2X = cx, base2Y = cy;

        switch (dir) {
            case UP:
                tipX = cx; tipY = cy + size;
                base1X = cx - size; base1Y = cy - size * 0.6f;
                base2X = cx + size; base2Y = cy - size * 0.6f;
                break;
            case DOWN:
                tipX = cx; tipY = cy - size;
                base1X = cx - size; base1Y = cy + size * 0.6f;
                base2X = cx + size; base2Y = cy + size * 0.6f;
                break;
            case LEFT:
                tipX = cx - size; tipY = cy;
                base1X = cx + size * 0.6f; base1Y = cy + size;
                base2X = cx + size * 0.6f; base2Y = cy - size;
                break;
            case RIGHT:
                tipX = cx + size; tipY = cy;
                base1X = cx - size * 0.6f; base1Y = cy + size;
                base2X = cx - size * 0.6f; base2Y = cy - size;
                break;
            default:
                return;
        }

        if (filled) {
            Resources.game.getShapeRenderer().triangle(tipX, tipY, base1X, base1Y, base2X, base2Y);
        } else {
            Resources.game.getShapeRenderer().rectLine(base1X, base1Y, tipX, tipY, 3f);
            Resources.game.getShapeRenderer().rectLine(tipX, tipY, base2X, base2Y, 3f);
            Resources.game.getShapeRenderer().rectLine(base2X, base2Y, base1X, base1Y, 3f);
        }
    }

    @Override
    public void dispose() {

    }


    public enum Direction {
        UP(180.0f, 180.0f, new Vector2(0.0f, 1.0f), new Vector2(1.0f, 0.5f), 12, true, false),
        DOWN(0.0f, 180.0f, new Vector2(0.0f, 0.0f), new Vector2(1.0f, 0.5f), 12, false, false),
        RIGHT(90.0f, 180.0f, new Vector2(1.0f, 0.0f), new Vector2(0.5f, 1.0f), 12, true, true),
        LEFT(270.0f, 180.0f, new Vector2(0.0f, 0.0f), new Vector2(0.5f, 1.0f), 12, false, true),
        UP_LEFT(0.0f, 270.0f, new Vector2(1.0f, 0.0f), new Vector2(0.5f, 0.5f), 16, false, false),
        UP_RIGHT(270.0f, 270.0f, new Vector2(0.0f, 0.0f), new Vector2(0.5f, 0.5f), 16, true, false),
        DOWN_LEFT(90.0f, 270.0f, new Vector2(1.0f, 1.0f), new Vector2(0.5f, 0.5f), 16, false, true),
        DOWN_RIGHT(180.0f, 270.0f, new Vector2(0.0f, 1.0f), new Vector2(0.5f, 0.5f), 16, true, true);

        private final float start, angle;
        private final int segments;
        private final Vector2 offset, scalemod;

        private final Boolean side, horizontal;

        Direction(float start, float angle, Vector2 offset, Vector2 scalemod, int segments, boolean side, boolean horizontal) {
            this.start = start;
            this.angle = angle;
            this.segments = segments;
            this.offset = offset;
            this.scalemod = scalemod;
            this.side = side;
            this.horizontal = horizontal;
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

        public Boolean getHorizontal() {
            return horizontal;
        }

        public Boolean getSide() {
            return side;
        }
    }
}
