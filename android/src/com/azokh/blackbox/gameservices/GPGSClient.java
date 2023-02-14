package com.azokh.blackbox.gameservices;

import com.azokh.azokhgameservices.IGameServiceClient;
import com.azokh.blackbox.AndroidLauncher;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.PlayGamesSdk;

public class GPGSClient implements IGameServiceClient {

    AndroidLauncher activity;

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
                authAction();
            } else {
                System.out.println("Auth failed");
            }


        });
    }

    public void authAction() {
        PlayGames.getPlayersClient(activity).getCurrentPlayer().addOnCompleteListener(mTask -> {
            System.out.println(mTask.getResult().getPlayerId());
                }
        );
    }


    private static final int SIGN_IN_REQUEST_CODE = 1001;
}
