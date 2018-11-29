package com.metadata.yg.task;

import com.metadata.yg.handle.WriteDataHandle;
import com.metadata.yg.inf.CallBack;
import com.metadata.yg.inf.MetadataExecutor;
import com.metadata.yg.utils.StopWatch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataExecutor {
    private final static int DATACACHENUM = 100000;
    private static int currThreadCount = 0;
    private static int maxThreadCount = 20;
    private String dataFile;

    public DataExecutor(String path) {
        this.dataFile = path;
    }

    public void executor(MetadataExecutor executor, ResultSet rs) throws Exception {

        final ExecutorService threadPool = Executors.newFixedThreadPool(maxThreadCount);//10
        final List<Future<String>> threadResultList = new ArrayList<>();
        final WriteDataHandle writeDataFile = new WriteDataHandle(DATACACHENUM);//缓存 10000
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        loadDB((num, data) -> {
            try {
                final List tempData = data;
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
                    Future<String> future = threadPool.submit(new TaskWithResult(writeDataFile, dataFile));
                    threadResultList.add(future);
                }
            } catch (Exception e) {
                writeLog("录入错误的数据：:0", e.getMessage());
            }
            return null;
        }, executor, rs);
        threadPool.shutdown();
        writeDataFile.flush(dataFile);
        stopWatch.stop();
        System.out.println(String.format("任务完成时间:%s ms", stopWatch.getTime()));
        System.out.println(WriteDataHandle.index);
    }

    //读一行数据
    public void loadDB(CallBack<Void> callBack, MetadataExecutor executor, ResultSet rs) throws Exception {
        int num = 0;
        while (rs.next()) {
            List<String> tmp = new ArrayList<>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                tmp.add(rs.getObject(i).toString());
            }
            num++;
            callBack.call(num, executor.getFormatRow(tmp));
        }
    }

    public void writeLog(String str, Object... values) {
        System.out.println(str);
    }
}
