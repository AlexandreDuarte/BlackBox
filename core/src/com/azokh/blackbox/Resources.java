package com.azokh.blackbox;

import com.azokhgameservices.IGameServiceClient;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;

public class Resources {

    public static BitmapFont titleFont;
    public static BitmapFont menuFont;
    public static BitmapFont textFont;

    public static Color titleColor = new Color(0xCFCFC4FF);
    public static Color beamColor1 = new Color(0xF05454FF);
    public static Color beamColor2 = new Color(0x30475EFF);
    public static Color pastelBlueOutline = new Color(0xC3DBD9FF);
    public static Color background = new Color(0x161613FF);
    public static Color board = new Color(0x595953FF);
    public static Color boardInactive = new Color(0x20201EFF);

    public static IGameServiceClient gsClient;

    public static BlackBox game;

    public static Rectangle screenCutoutTop = new Rectangle();
}