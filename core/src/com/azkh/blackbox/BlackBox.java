package com.azkh.blackbox;

import com.azkh.blackbox.effects.BackgroundRenderer;
import com.azkh.blackbox.mainscreen.MainMenuScreen;
import com.azkhgameservices.NoGameServiceClient;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class BlackBox extends Game {

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;

	private MainMenuScreen mainMenuScreen;

	private float gameHeight;

	@Override
	public void create () {
		PixmapPacker fontTexture = new PixmapPacker(1024, 1024, Pixmap.Format.RGBA8888, 2, false, new PixmapPacker.GuillotineStrategy());

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 2*Gdx.graphics.getWidth()/15;
		parameter.color = Resources.titleColor;
		parameter.magFilter = Texture.TextureFilter.Linear;
		parameter.minFilter = Texture.TextureFilter.Linear;
		parameter.characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
		parameter.packer = fontTexture;
		Resources.titleFont = generator.generateFont(parameter);
		Resources.menuFont = generator.generateFont(parameter);
		Resources.textFont = generator.generateFont(parameter);
		Resources.scoreFont = generator.generateFont(parameter);

		Resources.menuFont.getData().scale(-0.3f);
		Resources.textFont.getData().scale(-0.55f);
		Resources.scoreFont.getData().scale(-0.65f);
		generator.dispose();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();

		Resources.game = this;
		Resources.backgroundRenderer = new BackgroundRenderer();

		if (Resources.gsClient == null)
			Resources.gsClient = new NoGameServiceClient();

		mainMenuScreen = new MainMenuScreen();

		this.setScreen(mainMenuScreen);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Resources.background);
		if (Resources.backgroundRenderer != null) {
			Resources.backgroundRenderer.render(Gdx.graphics.getDeltaTime());
		}
		super.render();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
		batch.dispose();
		Resources.menuFont.dispose();
		Resources.titleFont.dispose();
		Resources.textFont.dispose();
		Resources.scoreFont.dispose();
	}
	public OrthographicCamera getCamera() {
		return camera;
	}

	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}
}
