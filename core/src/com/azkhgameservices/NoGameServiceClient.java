package com.azkhgameservices;

import com.azkh.blackbox.data.GameStatEntry;
import com.azkh.blackbox.data.Leaderboard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoGameServiceClient implements IGameServiceClient {

    @Override
    public void initialize() {
    }

    @Override
    public void auth() {
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void updateLeaderboard(Leaderboard leaderboard, GameStatEntry statEntry) {
        FileHandle file = Gdx.files.local("local_leaderboard_" + leaderboard.getName() + ".dat");

        String entry = statEntry.getDate() + "," + String.valueOf(statEntry.getGameTime()) + "\n";
        file.writeString(entry, true);
    }

    @Override
    public void fetchLeaderboardScores(Leaderboard leaderboard, LeaderboardCallback callback) {
        FileHandle file = Gdx.files.local("local_leaderboard_" + leaderboard.getName() + ".dat");
        List<OnlineScoreEntry> entries = new ArrayList<>();
        if (file.exists()) {
            String text = file.readString();
            String[] lines = text.split("\n");
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    try {
                        String date = parts[0].trim();
                        long time = Long.parseLong(parts[1].trim());
                        entries.add(new OnlineScoreEntry("", date, time));
                    } catch (Exception ignored) {
                    }
                }
            }
            Collections.sort(entries, (a, b) -> Long.compare(a.time, b.time));
        }

        List<OnlineScoreEntry> topScores = new ArrayList<>();
        OnlineScoreEntry userBest = null;

        for (int i = 0; i < entries.size(); i++) {
            OnlineScoreEntry raw = entries.get(i);
            OnlineScoreEntry formatted = new OnlineScoreEntry("#" + (i + 1), raw.holderName, raw.time);
            if (i < 5) {
                topScores.add(formatted);
            }
            if (i == 0) {
                userBest = formatted;
            }
        }

        if (callback != null) {
            callback.onScoresLoaded(topScores, userBest);
        }
    }
}
