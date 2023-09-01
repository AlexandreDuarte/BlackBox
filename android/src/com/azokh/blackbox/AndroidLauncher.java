package com.azokh.blackbox;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.core.view.WindowCompat;

import com.azokh.blackbox.gameservices.GPGSClient;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.math.Rectangle;

import java.util.Objects;

public class AndroidLauncher extends AndroidApplication {

	public GPGSClient gpgsClient;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
			/*getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setStatusBarColor(Resources.background.toIntBits());*/
			WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
			layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
			getWindow().setAttributes(layoutParams);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				Rect rect = Objects.requireNonNull(getWindow().getWindowManager().getDefaultDisplay().getCutout()).getBoundingRectTop();
				//Gdx.app.log("Starting", "message  " + rect.left + rect.bottom);
				Resources.screenCutoutTop = new Rectangle(rect.left, rect.bottom, rect.width(), rect.height());
			}
		}
		super.onCreate(savedInstanceState);


		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode= true;
		config.useCompass = false;
		config.useAccelerometer = false;
		config.useGyroscope = false;
		config.useRotationVectorSensor = false;

		gpgsClient = new GPGSClient(this);
		Resources.gsClient = gpgsClient;
		gpgsClient.initialize();

		BlackBox game = new BlackBox();
		initialize(game, config);
	}

}
