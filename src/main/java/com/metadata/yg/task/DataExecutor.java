package com.metadata.yg.task;

import com.metadata.yg.handle.WriteDataHandle;
import com.metadata.yg.utils.StopWatch;

import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.metadata.yg.utils.TBuilderRoomSqlFileTool.*;

public class DataExecutor {
//    public final static int BSIZE = 1024 * 1024;
    public final static int DATACACHENUM = 5000;
    public static int currThreadCount = 0;
    public static int maxThreadCount = 20;
    public static File dataFile = new File("data/dataFile.csv");

    public void executor(List<String> rs) throws Exception {
        final ExecutorService threadPool = Executors.newFixedThreadPool(maxThreadCount);//10
        final List<Future<String>> threadResultList = new ArrayList<>();
        final BufferedWriter bw = initDataWrite(dataFile); // 主要的buffer对象.
        final WriteDataHandle writeDataFile = new WriteDataHandle(DATACACHENUM);//缓存 10000
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        loadDB((num, data) -> {
            try {
                final String tempData = data;
                // 添加数据，如果超出了缓存数据，则 开始写入文件系统
                if (writeDataFile.add(tempData)) {
                    currThreadCount++;//0
                    // 如果提交的线程过多，则取回之后再提交.
                    if (currThreadCount >= maxThreadCount) {// 0  10
                        for (Future<String> fs : threadResultList) {
                            String tempDataName = fs.get();
                            currThreadCount--;
                        }
                        threadResultList.clear(); // 清空
                        currThreadCount = threadResultList.size();
                    }
                    Future<String> future = threadPool.submit(new TaskWithResult(writeDataFile, bw));
                    threadResultList.add(future);
                }

            } catch (Exception e) {
                writeLog("录入错误的数据：:0", e.getMessage());
            }
            return null;
        },rs);
        writeDataFile.flush(bw);
        threadPool.shutdown();
        stopWatch.stop();
        System.out.println(String.format("任务完成时间:%s ms", stopWatch.getTime()));
    }
}
