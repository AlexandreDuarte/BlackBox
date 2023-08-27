package com.azokh.blackbox.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GameStatEntry {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd", Locale.UK);

    private final Date date;
    private final long gameTime;

    public GameStatEntry(long gameTime) {
        date = Calendar.getInstance().getTime();
        this.gameTime = gameTime;
    }

    public String getDate() {
        return dateFormat.format(date);
    }

    public long getGameTime() {
        return gameTime;
    }
}
