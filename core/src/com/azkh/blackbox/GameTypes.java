package com.azkh.blackbox;

public class GameTypes {
    public static final GameType fourfour = new GameType(4, Leaderboards.leaderboard4, GameType.Mode.DEFAULT);
    public static final GameType fivefive = new GameType(5, Leaderboards.leaderboard5, GameType.Mode.DEFAULT);
    public static final GameType sixsix = new GameType(6, Leaderboards.leaderboard6, GameType.Mode.DEFAULT);

    public static final GameType dynamic44 = new GameType(4, Leaderboards.leaderboard4_dynamic, GameType.Mode.DYNAMIC);
    public static final GameType dynamic55 = new GameType(5, Leaderboards.leaderboard5_dynamic, GameType.Mode.DYNAMIC);
    public static final GameType dynamic66 = new GameType(6, Leaderboards.leaderboard6_dynamic, GameType.Mode.DYNAMIC);
}
