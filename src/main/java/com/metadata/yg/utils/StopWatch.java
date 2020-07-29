package com.metadata.yg.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopWatch {
    private static final Logger logger = LoggerFactory.getLogger(StopWatch.class);
    private long begin;
    private long end;

    public void start() {
        begin = System.currentTimeMillis();
    }

    public void stop() {
        end = System.currentTimeMillis();
    }

    public long getTime() {
        return end - begin;
    }

    public static void exitApplication(){
        logger.error("-------------------程序终止-------------------");
        System.exit(-1);
    }
}
