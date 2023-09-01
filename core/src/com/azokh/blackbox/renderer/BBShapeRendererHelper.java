package com.azokh.blackbox.renderer;

import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class BBShapeRendererHelper {

    static Vector2 tmp = new Vector2();

    public static void arc (ShapeRenderer shapeRenderer, float x, float y, float radius, float start, float degrees, int segments, float width) {
        if (segments <= 0) throw new IllegalArgumentException("segments must be > 0.");
        float colorBits = shapeRenderer.getColor().toFloatBits();
        float theta = (2 * MathUtils.PI * (degrees / 360.0f)) / segments;
        float cos = MathUtils.cos(theta);
        float sin = MathUtils.sin(theta);
        float cx1 = (radius - width) * MathUtils.cos(start * MathUtils.degreesToRadians);
        float cy1 = (radius - width) * MathUtils.sin(start * MathUtils.degreesToRadians);
        float cx2 = (radius) * MathUtils.cos(start * MathUtils.degreesToRadians);
        float cy2 = (radius) * MathUtils.sin(start * MathUtils.degreesToRadians);

        ImmediateModeRenderer renderer = shapeRenderer.getRenderer();

        float x1_cx1, x2_cx1, x1_cx2, x2_cx2, y1_cx1, y2_cx1, y1_cx2, y2_cx2;
        float temp;

        for (int i = 0; i < segments; i++) {
            x1_cx1 = x + cx1;
            y1_cx1 = y + cy1;

            temp = cx1;
            cx1 = cos * cx1 - sin * cy1;
            cy1 = sin * temp + cos * cy1;

            x2_cx1 = x + cx1;
            y2_cx1 = y + cy1;


            x1_cx2 = x + cx2;
            y1_cx2 = y + cy2;

            temp = cx2;
            cx2 = cos * cx2 - sin * cy2;
            cy2 = sin * temp + cos * cy2;


            x2_cx2 = x + cx2;
            y2_cx2 = y + cy2;


            renderer.color(colorBits);
            renderer.vertex(x1_cx2, y1_cx2, 0);
            renderer.color(colorBits);
            renderer.vertex(x1_cx1, y1_cx1, 0);
            renderer.color(colorBits);
            renderer.vertex(x2_cx2, y2_cx2, 0);

            renderer.color(colorBits);
            renderer.vertex(x2_cx1, y2_cx1, 0);
            renderer.color(colorBits);
            renderer.vertex(x2_cx2, y2_cx2, 0);
            renderer.color(colorBits);
            renderer.vertex(x1_cx1, y1_cx1, 0);
        }
    }

}
