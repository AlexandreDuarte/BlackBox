package com.azokh.blackbox;

import com.azokh.blackbox.data.Leaderboard;

public class GameType {

    private final int boardSize;
    private final Leaderboard leaderboard;

    public GameType(int boardSize, Leaderboard leaderboard) {
        this.boardSize = boardSize;
        this.leaderboard = leaderboard;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }
}
