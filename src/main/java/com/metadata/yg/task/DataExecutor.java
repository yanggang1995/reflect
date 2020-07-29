package com.metadata.yg.task;

import com.metadata.yg.handle.WriteDataHandle;
import com.metadata.yg.inf.CallBack;
import com.metadata.yg.producer.MakeDataProducer;
import com.metadata.yg.transform.MetadataTransform;
import com.metadata.yg.utils.DataUtils;
import com.metadata.yg.utils.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class DataExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DataExecutor.class);
    private final static int DATACACHENUM = 100000;  //数据缓存条数
    private static int currThreadCount = 0; //线程计数器
    private static int maxThreadCount = 40; //最大线程数
    final ExecutorService threadPool = Executors.newFixedThreadPool(maxThreadCount);


    /**
     * 调用并执行loadDB，运行整个处理流程
     * @param executor
     * @param loop
     * @throws Exception
     */
    public void executor(MetadataTransform executor, Map loop) throws Exception {
        final List<Future<String>> threadResultList = new ArrayList<>();
        final WriteDataHandle writeDataFile = new WriteDataHandle(DATACACHENUM);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CountDownLatch latch=new CountDownLatch(20);
        loadDBNull((num,data) -> {
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
                    threadPool.execute(new TaskWithResult(writeDataFile, loop,latch));
//                    Future<String> future = threadPool.submit(new TaskWithResult(writeDataFile, dataFile));
//                    threadResultList.add(future);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }, executor, loop,latch);
        latch.await();
        threadPool.shutdown();
        writeDataFile.flush(loop);
        stopWatch.stop();
        logger.info(String.format("任务完成时间:%s ms", stopWatch.getTime()));
    }

//    /**
//     * 读一行数据
//     * @param callBack 回调接口
//     * @param transform 数据转换
//     * @param rs jdbc结果集
//     * @throws Exception
//     */
/*    public void loadDB(CallBack<Void> callBack, MetadataTransform transform, ResultSet rs) throws Exception {
        int num = 0;
        while (rs.next()) {
            List<byte []> tmp = new ArrayList<>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i+) {
                tmp.add((rs.getObject(i)==null?"":rs.getObject(i)).toString().getBytes());
            }
            num++;
            callBack.call(num, transform.getFormatRow(tmp));
        }
    }*/

    public void loadDBNull(CallBack<Void> callBack,MetadataTransform transform, Map loop,CountDownLatch latch) throws Exception {
        int num = 0;
        int max=DataUtils.getMapMaxVal(loop);
/*        while (max>0) {
            num++;
            callBack.call(num, transform.getFormatRow(null));
            max--;
        }*/

        for(int i=0;i<20;i++){
            threadPool.execute(new MakeDataProducer(max/20,callBack,transform,latch));
        }
    }
}
