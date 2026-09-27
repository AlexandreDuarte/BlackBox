package com.azkh.blackbox;

import com.azkh.blackbox.effects.BackgroundRenderer;
import com.azkhgameservices.IGameServiceClient;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;

public class Resources {

    public static BitmapFont titleFont;
    public static BitmapFont menuFont;
    public static BitmapFont textFont;
    public static BitmapFont scoreFont;

    public final static Color titleColor = new Color(0xCFCFC4FF);
    public final static Color beamColor1 = new Color(0xF05454FF);
    public final static Color beamColor2 = new Color(0x30475EFF);
    public final static Color pastelBlueOutline = new Color(0xC3DBD9FF);
    public final static Color background = new Color(0x161613FF);
    public final static Color board = new Color(0x595953FF);
    public final static Color boardInactive = new Color(0x20201EFF);

    public static IGameServiceClient gsClient;

    public static BlackBox game;

    public static BackgroundRenderer backgroundRenderer;

    public static float screenAlpha = 1.0f;
}
