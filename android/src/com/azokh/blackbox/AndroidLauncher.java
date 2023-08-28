package com.azokh.blackbox;

import android.os.Bundle;

import com.azokh.blackbox.gameservices.GPGSClient;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

	public GPGSClient gpgsClient;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
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
