package com.azokh.blackbox.gameservices;

import com.azokh.azokhgameservices.IGameServiceClient;
import com.azokh.blackbox.AndroidLauncher;
import com.azokh.blackbox.data.GameStatEntry;
import com.azokh.blackbox.data.Leaderboard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.PlayGamesSdk;

import java.nio.ByteBuffer;

public class GPGSClient implements IGameServiceClient {

    AndroidLauncher activity;

    LeaderboardsClient leaderboardsClient;

    public GPGSClient(AndroidLauncher activity) {
        this.activity = activity;
    }

    @Override
    public void initialize() {
        System.out.println("Initializing playgamessdk");
        PlayGamesSdk.initialize(activity);
        leaderboardsClient = PlayGames.getLeaderboardsClient(activity);
    }

    @Override
    public void auth() {

        GamesSignInClient gamesSignInClient = PlayGames.getGamesSignInClient(activity);

        gamesSignInClient.isAuthenticated().addOnCompleteListener(isAuthenticatedTask -> {

            boolean isAuth = (isAuthenticatedTask.isSuccessful() &&
                    isAuthenticatedTask.getResult().isAuthenticated());

            if (isAuth) {
                authAction();
            } else {
                System.out.println("Auth failed");
            }


        });
    }


    public void updateLeaderboard(Leaderboard leaderboard, long value) {
        GameStatEntry gameEntry = new GameStatEntry(value);

        FileHandle handle = Gdx.files.local("gamedata_" + leaderboard.getName() + ".dat");
        ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE/Byte.SIZE);
        buffer.putLong(value);
        handle.writeBytes(buffer.array(),true);
    }

    @Override
    public void updateLeaderboard(String leaderboardId, long value) {
        leaderboardsClient.submitScore(leaderboardId, value);
    }

    public void authAction() {
        PlayGames.getPlayersClient(activity).getCurrentPlayer().addOnCompleteListener(mTask -> {
            //System.out.println(mTask.getResult().getPlayerId());
                }
        );
    }


    private static final int SIGN_IN_REQUEST_CODE = 1001;
}
