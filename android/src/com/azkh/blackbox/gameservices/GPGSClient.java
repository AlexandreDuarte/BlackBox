package com.azkh.blackbox.gameservices;

import com.azkh.blackbox.AndroidLauncher;
import com.azkh.blackbox.data.GameStatEntry;
import com.azkh.blackbox.data.Leaderboard;
import com.azkhgameservices.IGameServiceClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.PlayGamesSdk;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;

import java.util.ArrayList;
import java.util.List;

public class GPGSClient implements IGameServiceClient {

    AndroidLauncher activity;
    LeaderboardsClient leaderboardsClient;
    boolean isAuthenticated = false;

    public GPGSClient(AndroidLauncher activity) {
        this.activity = activity;
    }

    @Override
    public void initialize() {
        System.out.println("Initializing playgamessdk");
        PlayGamesSdk.initialize(activity);
    }

    @Override
    public void auth() {
        GamesSignInClient gamesSignInClient = PlayGames.getGamesSignInClient(activity);

        gamesSignInClient.signIn().addOnCompleteListener(authenticationResultTask -> {
            if (authenticationResultTask.isSuccessful() && authenticationResultTask.getResult().isAuthenticated()) {
                Gdx.app.log("Game Services", "Successfully authenticated");
                isAuthenticated = true;
                authAction();
            } else {
                isAuthenticated = false;
                Gdx.app.log("Game Services", "Authentication failed");
            }
        });
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void updateLeaderboard(Leaderboard leaderboard, GameStatEntry statEntry) {
        if (isAuthenticated) {
            updateGoogleLeaderboard(leaderboard.getLeaderboardId(), statEntry.getGameTime());
        }

        FileHandle file = Gdx.files.local("local_leaderboard_" + leaderboard.getName() + ".dat");
        String entry = statEntry.getDate() + "," + String.valueOf(statEntry.getGameTime()) + "\n";
        file.writeString(entry, true);
    }

    private void updateGoogleLeaderboard(String leaderboardId, long value) {
        if (isAuthenticated && leaderboardsClient != null) {
            leaderboardsClient.submitScore(leaderboardId, value);
        }
    }

    public void authAction() {
        leaderboardsClient = PlayGames.getLeaderboardsClient(activity);
        PlayGames.getPlayersClient(activity).getCurrentPlayer().addOnCompleteListener(mTask -> {
            if (mTask.isSuccessful()) {
                Gdx.app.log("Game Services", "Player ID: " + mTask.getResult().getPlayerId());
            }
        });
    }

    @Override
    public void fetchLeaderboardScores(Leaderboard leaderboard, LeaderboardCallback callback) {
        if (!isAuthenticated || leaderboardsClient == null) {
            callback.onFailed();
            return;
        }

        String leaderboardId = leaderboard.getLeaderboardId();

        leaderboardsClient.loadTopScores(leaderboardId, LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC, 10)
                .addOnCompleteListener(topTask -> {
                    if (!topTask.isSuccessful() || topTask.getResult() == null || topTask.getResult().get() == null) {
                        callback.onFailed();
                        return;
                    }

                    LeaderboardsClient.LeaderboardScores scores = topTask.getResult().get();
                    LeaderboardScoreBuffer buffer = scores.getScores();
                    List<OnlineScoreEntry> topList = new ArrayList<>();

                    if (buffer != null) {
                        for (LeaderboardScore score : buffer) {
                            String rank = "#" + score.getRank();
                            String name = score.getScoreHolderDisplayName();
                            long time = score.getRawScore();
                            topList.add(new OnlineScoreEntry(rank, name, time));
                        }
                        buffer.release();
                    }

                    leaderboardsClient.loadCurrentPlayerLeaderboardScore(leaderboardId, LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC)
                            .addOnCompleteListener(playerTask -> {
                                OnlineScoreEntry userScore = null;
                                if (playerTask.isSuccessful() && playerTask.getResult() != null && playerTask.getResult().get() != null) {
                                    LeaderboardScore pScore = playerTask.getResult().get();
                                    userScore = new OnlineScoreEntry("#" + pScore.getRank(), pScore.getScoreHolderDisplayName(), pScore.getRawScore());
                                }
                                callback.onScoresLoaded(topList, userScore);
                            });
                });
    }
}
