package com.azkh.blackbox.ui.element;

import com.azkh.blackbox.Resources;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class BBText implements Element {

    private BitmapFont font;
    private CharSequence text;
    private Color color;
    private int hAlign;
    private int vAlign;
    private float x, y;

    private final GlyphLayout layout;
    private float drawX, drawY;

    public BBText(CharSequence text, BitmapFont font, float x, float y, Color color, int hAlign, int vAlign) {
        this.text = (text != null) ? text : "";
        this.font = font;
        this.x = x;
        this.y = y;
        this.color = (color != null) ? color.cpy() : (font != null ? font.getColor().cpy() : Resources.titleColor.cpy());
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        this.layout = new GlyphLayout(font, text, 0, text.length(), color, 0, hAlign, false, null);
        updateLayout();
    }

    public BBText(CharSequence text, BitmapFont font, float x, float y, Color color, int align) {
        this(text, font, x, y, color, align, align);
    }

    public BBText(CharSequence text, BitmapFont font, float x, float y, int hAlign, int vAlign) {
        this(text, font, x, y, font != null ? font.getColor() : Resources.titleColor, hAlign, vAlign);
    }

    public BBText(CharSequence text, BitmapFont font, float x, float y, int align) {
        this(text, font, x, y, font != null ? font.getColor() : Resources.titleColor, align, align);
    }

    public BBText(CharSequence text, BitmapFont font, float x, float y) {
        this(text, font, x, y, Align.left, Align.top);
    }

    public void updateLayout() {
        if (font == null) return;
        layout.setText(font, text, 0, text.length(), color, 0, hAlign, false, null);

        drawX = x;

        float offsety;
        if ((vAlign & Align.top) != 0) {
            offsety = 0.0f;
        } else if ((vAlign & Align.bottom) != 0) {
            offsety = layout.height;
        } else if ((vAlign & Align.center) != 0 || vAlign == Align.center) {
            offsety = layout.height / 2.0f;
        } else {
            offsety = 0.0f;
        }
        drawY = y + offsety;
    }

    public void setText(CharSequence text) {
        this.text = (text != null) ? text : "";
        updateLayout();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateLayout();
    }

    public void setFont(BitmapFont font) {
        this.font = font;
        updateLayout();
    }

    public void setColor(Color color) {
        if (color != null) {
            this.color = color.cpy();
            updateLayout();
        }
    }

    public void setHorizontalAlign(int hAlign) {
        this.hAlign = hAlign;
        updateLayout();
    }

    public void setVerticalAlign(int vAlign) {
        this.vAlign = vAlign;
        updateLayout();
    }

    public void setAlign(int hAlign, int vAlign) {
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        updateLayout();
    }

    public void setAlign(int align) {
        this.hAlign = align;
        this.vAlign = align;
        updateLayout();
    }

    public CharSequence getText() {
        return text;
    }

    public float getWidth() {
        return layout.width;
    }

    public float getHeight() {
        return layout.height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getDrawX() {
        if (Align.isRight(hAlign)) {
            return x - layout.width;
        } else if (Align.isCenterHorizontal(hAlign)) {
            return x - layout.width / 2.0f;
        }
        return x;
    }

    public float getDrawY() {
        return drawY;
    }

    public int getHAlign() {
        return hAlign;
    }

    public int getVAlign() {
        return vAlign;
    }

    public GlyphLayout getLayout() {
        return layout;
    }

    public void draw(SpriteBatch batch) {
        if (font == null || batch == null) return;

        Color drawColor = (color != null) ? color : font.getColor();
        font.setColor(drawColor.r, drawColor.g, drawColor.b, drawColor.a * Resources.screenAlpha);
        font.draw(batch, text, drawX, drawY, 0, hAlign, false);
    }

    @Override
    public void render() {
        draw(Resources.game.getBatch());
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {

    }
}
