package com.azokh.blackbox.gameservices;

import com.azokh.blackbox.AndroidLauncher;
import com.azokh.blackbox.data.GameStatEntry;
import com.azokh.blackbox.data.Leaderboard;
import com.azokhgameservices.IGameServiceClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.PlayGamesSdk;

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

        gamesSignInClient.isAuthenticated().addOnCompleteListener(isAuthenticatedTask -> {

            boolean isAuth = (isAuthenticatedTask.isSuccessful() &&
                    isAuthenticatedTask.getResult().isAuthenticated());

            if (isAuth) {
                isAuthenticated = true;
                authAction();
            } else {
                isAuthenticated = false;
                Gdx.app.log("Game Services", "Authentication failed");
            }


        });
    }

    @Override
    public void updateLeaderboard(Leaderboard leaderboard, GameStatEntry statEntry) {
        if (isAuthenticated)
            updateGoogleLeaderboard(leaderboard.getLeaderboardId(), statEntry.getGameTime());

        FileHandle file = Gdx.files.local("local_leaderboard_" + leaderboard.getName() + ".dat");

        String entry = statEntry.getDate() + "," +  String.valueOf(statEntry.getGameTime()) + "\n";
        file.writeString(entry, true);
    }

    private void updateGoogleLeaderboard(String leaderboardId, long value) {
        if (isAuthenticated)
            leaderboardsClient.submitScore(leaderboardId, value);
    }

    public void authAction() {
        leaderboardsClient = PlayGames.getLeaderboardsClient(activity);
        PlayGames.getPlayersClient(activity).getCurrentPlayer().addOnCompleteListener(mTask -> {
            //System.out.println(mTask.getResult().getPlayerId());
                }
        );
    }
}
