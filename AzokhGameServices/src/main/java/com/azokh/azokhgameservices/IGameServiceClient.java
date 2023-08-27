package com.azokh.azokhgameservices;

public interface IGameServiceClient {

    void initialize();

    void auth();


    void updateLeaderboard(String leaderboardID, long value);

}
