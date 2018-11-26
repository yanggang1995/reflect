package com.metadata.yg.utils;

public class StopWatch {
    long begin;
    long end;

    public void start() {
        begin = System.currentTimeMillis();
    }

    public void stop() {
        end = System.currentTimeMillis();
    }

    public long getTime() {
        return end - begin;
    }
}
