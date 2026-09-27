package com.azkh.blackbox.ui.element.button;

import com.azkh.blackbox.Resources;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class BBButtonRightArrow extends BBListenerButtonShape{



    static float L = Resources.menuFont.getXHeight();
    static float l = L/4;

    static float sqrt2 = (float)Math.sqrt(2.0);

    static Vector2 p1 = new Vector2(L/4, L);
    static Vector2 p21 = new Vector2(3*L/4 + sqrt2*l/4, L/2 - sqrt2*l/4);
    static Vector2 p23 = new Vector2(3*L/4 + sqrt2*l/4, L/2 + sqrt2*l/4);
    static Vector2 p3 = new Vector2(L/4, 0);

    static Vector2 offset = new Vector2(0, -10);


    Vector2 P;

    public BBButtonRightArrow(int id, float x, float y, BBListener ffListener) {
        super(id, x, y, L, L, Align.left, Align.center, ffListener);
        P = new Vector2(getBounds().getX(), getBounds().getY());
    }

    @Override
    public void drawSelected(ShapeRenderer shapeRenderer) {
        Color c = Resources.beamColor1.cpy();
        c.a *= Resources.screenAlpha;
        shapeRenderer.setColor(c);
        shapeRenderer.rectLine(P.cpy().add(p1.cpy().add(offset)), P.cpy().add(p21.cpy().add(offset)), l);
        shapeRenderer.rectLine(P.cpy().add(p3.cpy().add(offset)), P.cpy().add(p23.cpy().add(offset)), l);
        shapeRenderer.setColor(Resources.background);
    }

    @Override
    public void drawUnselected(ShapeRenderer shapeRenderer) {
        Color c = Resources.titleColor.cpy();
        c.a *= Resources.screenAlpha;
        shapeRenderer.setColor(c);
        shapeRenderer.rectLine(P.cpy().add(p1), P.cpy().add(p21), l);
        shapeRenderer.rectLine(P.cpy().add(p3), P.cpy().add(p23), l);
        shapeRenderer.setColor(Resources.background);
    }
}
