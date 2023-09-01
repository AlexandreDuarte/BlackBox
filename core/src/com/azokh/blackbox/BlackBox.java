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
import com.mazatech.gdx.SVGAssetsConfigGDX;
import com.mazatech.gdx.SVGAssetsGDX;

public class BlackBox extends Game {

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;

	private MainMenuScreen mainMenuScreen;

	private SVGAssetsGDX svg;
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
		parameter.characters = "0123456789VDSTARBLCKOXIEU:x";
		parameter.packer = fontTexture;
		Resources.titleFont = generator.generateFont(parameter);
		Resources.menuFont = generator.generateFont(parameter);
		Resources.textFont = generator.generateFont(parameter);

		Resources.menuFont.getData().scale(-0.3f);
		Resources.textFont.getData().scale(-0.55f);
		generator.dispose();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();

		Resources.game = this;

		SVGAssetsConfigGDX svgCfg = new SVGAssetsConfigGDX(Gdx.graphics.getBackBufferWidth(),
				Gdx.graphics.getBackBufferHeight(),
				Gdx.graphics.getPpiX());
		//SVGAssets.init();

		svg = new SVGAssetsGDX(svgCfg);

		gameHeight = Gdx.graphics.getHeight() - Resources.screenCutoutTop.getHeight();

		if (Resources.gsClient == null)
			Resources.gsClient = new NoGameServiceClient();



		mainMenuScreen = new MainMenuScreen();

		Gdx.app.log("Starting", "message  " + Resources.screenCutoutTop.getX() + " " + Resources.screenCutoutTop.getY() + " " + Resources.screenCutoutTop.getWidth() + " " + Resources.screenCutoutTop.getHeight());



		this.setScreen(mainMenuScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
		batch.dispose();
		Resources.menuFont.dispose();
		Resources.titleFont.dispose();
		Resources.textFont.dispose();
		svg.dispose();
		//SVGAssets.dispose();
	}

	public float getGameHeight() {
		return gameHeight;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public SVGAssetsGDX getSvg() {
		return svg;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}
}
