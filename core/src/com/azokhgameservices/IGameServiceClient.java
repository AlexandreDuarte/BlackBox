package com.azokhgameservices;

import com.azokh.blackbox.data.GameStatEntry;
import com.azokh.blackbox.data.Leaderboard;

public interface IGameServiceClient {

    void initialize();

    void auth();


    void updateLeaderboard(Leaderboard leaderboard, GameStatEntry statEntry);

}
