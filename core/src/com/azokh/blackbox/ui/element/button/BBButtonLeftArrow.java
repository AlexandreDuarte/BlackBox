package com.azokh.blackbox.ui.element.button;

import com.azokh.blackbox.Resources;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class BBButtonLeftArrow extends BBListenerButtonShape{



    static float L = Resources.menuFont.getCapHeight();
    static float l = L/4;

    static float sqrt2 = (float)Math.sqrt(2.0);

    static Vector2 p1 = new Vector2(3*L/4, L);
    static Vector2 p21 = new Vector2(L/4 - sqrt2*l/4, L/2 - sqrt2*l/4);
    static Vector2 p23 = new Vector2(L/4 - sqrt2*l/4, L/2 + sqrt2*l/4);
    static Vector2 p3 = new Vector2(3*L/4, 0);

    static Vector2 offset = new Vector2(0, -10);


    Vector2 P = new Vector2(getBounds().getX(), getBounds().getY());

    public BBButtonLeftArrow(int id, int x, int y, int width, int height, BBListener ffListener) {
        super(id, x - (int)L/2, y, (int)L, (int)L, ffListener);
    }

    @Override
    public void drawSelected(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Resources.beamColor1);
        shapeRenderer.rectLine(P.cpy().add(p1.cpy().add(offset)), P.cpy().add(p21.cpy().add(offset)), l);
        shapeRenderer.rectLine(P.cpy().add(p3.cpy().add(offset)), P.cpy().add(p23.cpy().add(offset)), l);
        shapeRenderer.setColor(Resources.background);
    }

    @Override
    public void drawUnselected(ShapeRenderer shapeRenderer) {
        //Gdx.app.log("render", "x: " + P.cpy().add(p1).x + " y:" + P.cpy().add(p1).y);
        shapeRenderer.setColor(Resources.titleColor);
        shapeRenderer.rectLine(P.cpy().add(p1), P.cpy().add(p21), l);
        shapeRenderer.rectLine(P.cpy().add(p3), P.cpy().add(p23), l);
        shapeRenderer.setColor(Resources.background);
    }
}
