package com.azokh.blackbox.ui.element.beam;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ui.element.Element;
import com.badlogic.gdx.graphics.Color;

public class BeamElement implements Element {

    private int width = 0;
    int targetWidth;
    float xpos, ypos;
    float delay;
    Color color, shadowColor;
    boolean vertical, direction;


    public BeamElement(int targetWidth, float delay, float ypos, Color color, boolean vertical, boolean direction) {
        this.targetWidth = targetWidth;
        this.delay = delay;
        this.ypos = ypos;
        this.color = color;
        this.shadowColor = color.cpy().mul(0.7f);
        this.vertical = vertical;
        this.direction = direction;

        if (direction) {
            xpos = 0;
        } else {
            xpos = targetWidth;
        }
    }

    public void update(float delta) {
        if (width < targetWidth) {
            width += targetWidth * delta / delay;
            if (!direction) {
                xpos = targetWidth - width;
            }
        }
    }

    @Override
    public void dispose() {

    }

    public void render() {
        if (vertical) {
            Resources.game.getShapeRenderer().setColor(shadowColor);
            Resources.game.getShapeRenderer().rect(ypos+5, xpos-10, 50, width+10);
            Resources.game.getShapeRenderer().setColor(color);
            Resources.game.getShapeRenderer().rect(ypos, xpos, 50, width);
        } else {
            Resources.game.getShapeRenderer().setColor(shadowColor);
            Resources.game.getShapeRenderer().rect(xpos-10, ypos-5, width+10, 50);
            Resources.game.getShapeRenderer().setColor(color);
            Resources.game.getShapeRenderer().rect(xpos, ypos, width, 50);
        }


    }

    public int getWidth() {
        return width;
    }

}
