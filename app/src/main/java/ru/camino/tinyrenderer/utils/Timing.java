package ru.camino.tinyrenderer.utils;

public class Timing {
    public static final String TO_STRING_PATTERN = "%1$s: %2$d:%3$d.%4$d";

    private String mTitle;
    private long mStartTime;
    private long mFinishTime;

    public Timing(String title) {
        mTitle = title;
    }

    public void start() {
        mStartTime = System.currentTimeMillis();
    }

    public void stop() {
        mFinishTime = System.currentTimeMillis();
    }

    public long getDurationMillis() {
        final long finishTime = mFinishTime == 0 ? System.currentTimeMillis() : mFinishTime;

        return finishTime - mStartTime;
    }

    @Override
    public String toString() {
        final long duration = getDurationMillis();
        final long millis =  duration % 1000;
        final long seconds = (duration - millis) / 1000 % 60;
        final long minutes = (duration - millis) / 1000 - seconds;
        return String.format(TO_STRING_PATTERN, mTitle, minutes, seconds, millis);
    }
}
