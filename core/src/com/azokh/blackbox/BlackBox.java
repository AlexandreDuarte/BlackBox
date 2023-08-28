package com.azokh.blackbox;


import com.azokh.blackbox.mainscreen.MainMenuScreen;
import com.azokhgameservices.NoGameServiceClient;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mazatech.svgt.SVGAssets;

public class BlackBox extends Game {


	@Override
	public void create () {
		PixmapPacker fontTexture = new PixmapPacker(1024, 1024, Pixmap.Format.RGBA8888, 2, false, new PixmapPacker.GuillotineStrategy());

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 2*Gdx.graphics.getWidth()/15;
		parameter.color = Resources.titleColor;
		parameter.magFilter = Texture.TextureFilter.Linear;
		parameter.minFilter = Texture.TextureFilter.Linear;
		parameter.characters = "0123456789VDSTARBLCKOXIEU:x";
		parameter.packer = fontTexture;
		Resources.titleFont = generator.generateFont(parameter);
		Resources.menuFont = generator.generateFont(parameter);
		Resources.textFont = generator.generateFont(parameter);

		Resources.menuFont.getData().scale(-0.3f);
		Resources.textFont.getData().scale(-0.55f);
		generator.dispose();

		Resources.camera = new OrthographicCamera();
		Resources.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Resources.shapeRenderer = new ShapeRenderer();
		Resources.batch = new SpriteBatch();

		Resources.game = this;

		Resources.mainMenuScreen = new MainMenuScreen();

		SVGAssets.init();

		if (Resources.gsClient == null)
			Resources.gsClient = new NoGameServiceClient();


		this.setScreen(Resources.mainMenuScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		Resources.shapeRenderer.dispose();
		Resources.batch.dispose();
		Resources.menuFont.dispose();
		Resources.titleFont.dispose();
		Resources.textFont.dispose();
		SVGAssets.dispose();
	}
}
