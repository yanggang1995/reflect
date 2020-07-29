package com.metadata.yg.task;

import com.metadata.yg.consumer.FileConsumer;
import com.metadata.yg.inf.QueueCallBack;
import com.metadata.yg.producer.DataProducer;
import com.metadata.yg.transform.MetadataTransform;
import com.metadata.yg.transform.impl.BJLTTransform;
import com.metadata.yg.utils.DataUtils;
import com.metadata.yg.utils.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Y.G
 * @description:
 * @create: 2019-01-02 19:38
 **/
public class QueueExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DataExecutor.class);
    private final static int DATACACHENUM = 100000;  //数据缓存条数
    private static int currThreadCount = 0; //线程计数器
    private static int maxThreadCount = 40; //最大线程数
    final ExecutorService threadPool = Executors.newFixedThreadPool(maxThreadCount);
    private final ConcurrentLinkedQueue<List> queue = new ConcurrentLinkedQueue<>();


    /**
     * 调用并执行loadDB，运行整个处理流程
     *
     * @param executor
     * @param loop
     * @throws Exception
     */
    public void executor(MetadataTransform executor, Map loop) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CountDownLatch latch = new CountDownLatch(20);
        loadDBNull(() -> {
            try {
                if (currThreadCount < 20) {// 0  10
                    threadPool.execute(new FileConsumer(queue, loop,executor));
                    currThreadCount++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }, executor, loop, latch);
        latch.await();
        while (queue.isEmpty()) {
            threadPool.shutdownNow();
            break;
        }
        stopWatch.stop();
        logger.info(String.format("任务完成时间:%s ms", stopWatch.getTime()));
    }


    public void loadDBNull(QueueCallBack<Void> callBack, MetadataTransform transform, Map loop, CountDownLatch latch) throws Exception {
        int max = DataUtils.getMapMaxVal(loop);
        for (int i = 0; i < 20; i++) {
            threadPool.execute(new DataProducer(max / 20, transform, queue, latch));
        }
        while(((BJLTTransform)transform).index<max) {
            callBack.call();
        }
    }
}
