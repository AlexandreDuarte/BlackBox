package com.azokhgameservices;

import com.azokh.blackbox.data.GameStatEntry;
import com.azokh.blackbox.data.Leaderboard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class NoGameServiceClient implements IGameServiceClient{
    @Override
    public void initialize() {

    }

    @Override
    public void auth() {

    }

    @Override
    public void updateLeaderboard(Leaderboard leaderboard, GameStatEntry statEntry) {
        FileHandle file = Gdx.files.local("local_leaderboard_" + leaderboard.getName() + ".dat");

        String entry = statEntry.getDate() + "," +  String.valueOf(statEntry.getGameTime()) + "\n";
        file.writeString(entry, true);
    }
}
