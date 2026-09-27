package com.azkh.blackbox.gameselectionscreen;

import com.azkh.blackbox.Resources;
import com.azkh.blackbox.ui.element.Element;
import com.azkh.blackbox.ui.element.beam.BeamElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class BeamGameSelectionScreen implements Element {

    BeamElement beamElement1, beamElement2;

    public BeamGameSelectionScreen(Color laserColor1, Color laserColor2) {
        beamElement2 = new BeamElement(Gdx.graphics.getWidth(), 2.0f, Gdx.graphics.getHeight()/3.0f, laserColor2, false, false);
        beamElement1 = new BeamElement(Gdx.graphics.getWidth(), 2.0f, 2.0f*Gdx.graphics.getHeight()/3.0f, laserColor1, false, true);

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
