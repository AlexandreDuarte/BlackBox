package com.azokh.blackbox.gameselectionscreen;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.ui.element.Element;
import com.azokh.blackbox.ui.element.beam.BeamElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class BeamGameSelectionScreen implements Element {

    BeamElement beamElement1, beamElement2;

    public BeamGameSelectionScreen(int title_delta, int title_height, Color laserColor1, Color laserColor2) {
        beamElement2 = new BeamElement(Gdx.graphics.getWidth(), 2.0f, Resources.game.getGameHeight()/3-title_height-65, laserColor2, false, false);
        beamElement1 = new BeamElement(Gdx.graphics.getWidth(), 2.0f, 2*Resources.game.getGameHeight()/3-title_height+65, laserColor1, false, true);

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
