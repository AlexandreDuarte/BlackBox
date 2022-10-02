package com.azokh.blackbox.mainscreen;

import com.azokh.blackbox.ui.Element;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;


public class BeamMainMenuScreen implements Element {

    BeamElement beamElement1, beamElement2;

    public BeamMainMenuScreen(int title_delta, int title_height, Color laserColor1, Color laserColor2) {
        beamElement1 = new BeamElement(Gdx.graphics.getHeight(), 2.0f, Gdx.graphics.getWidth()/2 - title_delta - 25, laserColor1, true);
        beamElement2 = new BeamElement(Gdx.graphics.getWidth(), 2.0f, 2*Gdx.graphics.getHeight()/3-(int)title_height-65, laserColor2, false);
    }

    @Override
    public void render() {
        beamElement2.render();
        beamElement1.render();
    }

    @Override
    public void update(float delta) {
        beamElement1.update(delta);
        beamElement2.update(delta);
    }

    @Override
    public void dispose() {
        beamElement1.dispose();
        beamElement2.dispose();
    }
}
