package com.azokh.blackbox;

import android.os.Bundle;

import com.azokh.blackbox.gameservices.GPGSClient;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

	GPGSClient gpgsClient;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode= true;
		config.useCompass = false;
		config.useAccelerometer = false;
		config.useGyroscope = false;
		config.useRotationVectorSensor = false;

		BlackBox game = new BlackBox();
		gpgsClient = new GPGSClient(this);
		gpgsClient.initialize();
		game.gsClient = gpgsClient;
		initialize(game, config);
	}
}
