package com.azkh.blackbox.data;

public class Leaderboard {

    private final String name;
    private final String leaderboardId;

    public Leaderboard(String name, String leaderboardId) {
        this.name = name;
        this.leaderboardId = leaderboardId;
    }

    public String getName() {
        return name;
    }

    public String getLeaderboardId() {
        return leaderboardId;
    }
}
