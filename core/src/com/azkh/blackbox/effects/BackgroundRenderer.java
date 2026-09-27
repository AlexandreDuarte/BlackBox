package com.azkh.blackbox.effects;

import com.azkh.blackbox.Resources;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class BackgroundRenderer {

    private static class Particle {
        float x, y;
        float radius;
        float vx, vy;
        float alpha;
        Color color;

        Particle(float width, float height) {
            reset(width, height, true);
        }

        void reset(float width, float height, boolean randomPosition) {
            if (randomPosition) {
                x = MathUtils.random(0, width);
                y = MathUtils.random(0, height);
            } else {
                x = MathUtils.random(0, width);
                y = -10;
            }
            radius = MathUtils.random(8f, 15f);
            vx = MathUtils.random(-40f, 40f);
            vy = MathUtils.random(25f, 50f);
            alpha = MathUtils.random(0.08f, 0.22f);
            color = MathUtils.randomBoolean() ? Resources.beamColor1 : Resources.beamColor2;
        }

        void update(float delta, float width, float height) {
            x += vx * delta;
            y += vy * delta;
            if (x < -10 || x > width + 10 || y > height + 10) {
                reset(width, height, false);
            }
        }
    }

    private final Particle[] particles;

    public BackgroundRenderer() {
        int count = 25;
        particles = new Particle[count];
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        for (int i = 0; i < count; i++) {
            particles[i] = new Particle(width, height);
        }
    }

    public void render(float delta) {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        ShapeRenderer shapeRenderer = Resources.game.getShapeRenderer();
        shapeRenderer.setProjectionMatrix(Resources.game.getCamera().combined);

        // 1. Draw Subtle Tech Background Grid
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float spacing = 60f;

        // Subtle grid dots at intersections
        for (float x = 0; x <= width; x += spacing) {
            for (float y = 0; y <= height; y += spacing) {
                shapeRenderer.setColor(Resources.pastelBlueOutline.r, Resources.pastelBlueOutline.g, Resources.pastelBlueOutline.b, 0.08f);
                shapeRenderer.circle(x, y, 1.5f, 8);
            }
        }

        // Subtle vertical & horizontal ambient lines
        shapeRenderer.end();
        Gdx.gl.glLineWidth(1f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Resources.board.r, Resources.board.g, Resources.board.b, 0.06f);
        for (float x = 0; x <= width; x += spacing * 2) {
            shapeRenderer.line(x, 0, x, height);
        }
        for (float y = 0; y <= height; y += spacing * 2) {
            shapeRenderer.line(0, y, width, y);
        }
        shapeRenderer.end();

        // 2. Render Floating Ambient Energy Particles
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Particle p : particles) {
            p.update(delta, width, height);
            shapeRenderer.setColor(p.color.r, p.color.g, p.color.b, p.alpha);
            shapeRenderer.circle(p.x, p.y, p.radius, 12);
        }
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
