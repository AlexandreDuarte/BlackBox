package com.azkh.blackbox;

import com.azkh.blackbox.data.Leaderboard;

public class GameType {

    public enum Mode {
        DEFAULT("DEFAULT"),
        DYNAMIC("DYNAMIC");

        private final String displayName;

        Mode(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private final int boardSize;
    private final Leaderboard leaderboard;
    private final Mode mode;

    public GameType(int boardSize, Leaderboard leaderboard, Mode mode) {
        this.boardSize = boardSize;
        this.leaderboard = leaderboard;
        this.mode = mode;
    }

    public GameType(int boardSize, Leaderboard leaderboard) {
        this(boardSize, leaderboard, Mode.DEFAULT);
    }

    public int getBoardSize() {
        return boardSize;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public Mode getMode() {
        return mode;
    }
}
