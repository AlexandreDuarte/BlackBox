package com.azkhgameservices;

import com.azkh.blackbox.data.GameStatEntry;
import com.azkh.blackbox.data.Leaderboard;

import java.util.List;

public interface IGameServiceClient {

    void initialize();

    void auth();

    boolean isAuthenticated();

    void updateLeaderboard(Leaderboard leaderboard, GameStatEntry statEntry);

    void fetchLeaderboardScores(Leaderboard leaderboard, LeaderboardCallback callback);

    interface LeaderboardCallback {
        void onScoresLoaded(List<OnlineScoreEntry> topScores, OnlineScoreEntry userScore);
        void onFailed();
    }

    class OnlineScoreEntry {
        public final String rank;
        public final String holderName;
        public final long time;

        public OnlineScoreEntry(String rank, String holderName, long time) {
            this.rank = rank;
            this.holderName = holderName;
            this.time = time;
        }
    }
}
