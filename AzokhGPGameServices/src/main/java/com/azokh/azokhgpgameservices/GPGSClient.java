package com.azokh.azokhgpgameservices;

import android.app.Activity;

import com.azokh.azokhgameservices.IGameServiceClient;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.PlayGamesSdk;

public class GPGSClient implements IGameServiceClient {

    Activity activity;

    public GPGSClient(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void initialize() {
        PlayGamesSdk.initialize(activity);

        GamesSignInClient gamesSignInClient = PlayGames.getGamesSignInClient(activity);

        gamesSignInClient.isAuthenticated().addOnCompleteListener(isAuthenticatedTask -> {

            authAction((isAuthenticatedTask.isSuccessful() &&
                    isAuthenticatedTask.getResult().isAuthenticated()));


        });
    }

    public void authAction(boolean isAuth) {

    }
}
