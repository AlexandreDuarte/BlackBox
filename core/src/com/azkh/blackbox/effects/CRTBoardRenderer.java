package com.azkh.blackbox.effects;

import com.azkh.blackbox.Resources;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class CRTBoardRenderer {

    private FrameBuffer fbo;
    private ShaderProgram shader;
    private float distortion = 0.20f;

    private static final String VERTEX_SHADER =
            "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
            "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" +
            "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +
            "uniform mat4 u_projTrans;\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "void main() {\n" +
            "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" +
            "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +
            "   gl_Position = u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
            "}\n";

    private static final String FRAGMENT_SHADER =
            "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "uniform sampler2D u_texture;\n" +
            "uniform float u_distortion;\n" +
            "void main() {\n" +
            "   vec2 cc = v_texCoords - vec2(0.5);\n" +
            "   float dist = dot(cc, cc);\n" +
            "   vec2 uv = v_texCoords + cc * (dist * u_distortion);\n" +
            "   if (uv.x < 0.0 || uv.x > 1.0 || uv.y < 0.0 || uv.y > 1.0) {\n" +
            "       discard;\n" +
            "   } else {\n" +
            "       vec4 col = texture2D(u_texture, uv);\n" +
            "       float scanline = sin(uv.y * 380.0) * 0.035;\n" +
            "       col.rgb -= scanline;\n" +
            "       gl_FragColor = col * v_color;\n" +
            "   }\n" +
            "}\n";

    public CRTBoardRenderer() {
        initFbo();
        shader = new ShaderProgram(VERTEX_SHADER, FRAGMENT_SHADER);
        if (!shader.isCompiled()) {
            Gdx.app.error("CRTBoardRenderer", "Shader compilation error: " + shader.getLog());
        }
    }

    private void initFbo() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
    }

    public void begin() {
        if (fbo == null || fbo.getWidth() != Gdx.graphics.getWidth() || fbo.getHeight() != Gdx.graphics.getHeight()) {
            if (fbo != null) fbo.dispose();
            initFbo();
        }
        fbo.begin();
        ScreenUtils.clear(0, 0, 0, 0);
    }

    public void endAndDraw() {
        fbo.end();

        Texture tex = fbo.getColorBufferTexture();
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if (shader.isCompiled()) {
            Resources.game.getBatch().setShader(shader);
            shader.bind();
            shader.setUniformf("u_distortion", distortion);
        }

        Resources.game.getBatch().begin();
        Resources.game.getBatch().setColor(1f, 1f, 1f, Resources.screenAlpha);
        Resources.game.getBatch().draw(tex, 0, 0, width, height, 0, 0, width, height, false, true);
        Resources.game.getBatch().end();

        Resources.game.getBatch().setShader(null);
    }

    public Vector2 transformTouch(float screenX, float screenY) {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        float normX = screenX / width;
        float normY = (height - screenY) / height;

        float ccX = normX - 0.5f;
        float ccY = normY - 0.5f;
        float dist = ccX * ccX + ccY * ccY;

        float unDistortX = normX - ccX * dist * distortion;
        float unDistortY = normY - ccY * dist * distortion;

        return new Vector2(unDistortX * width, unDistortY * height);
    }

    public void dispose() {
        if (fbo != null) {
            fbo.dispose();
            fbo = null;
        }
        if (shader != null) {
            shader.dispose();
            shader = null;
        }
    }
}
