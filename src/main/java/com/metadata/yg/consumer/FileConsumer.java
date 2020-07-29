package com.metadata.yg.consumer;

import com.metadata.yg.constant.Conf;
import com.metadata.yg.transform.MetadataTransform;
import com.metadata.yg.transform.impl.BJLTTransform;
import com.metadata.yg.utils.DataUtils;
import com.metadata.yg.utils.FileUtils;
import com.metadata.yg.utils.ObjectUtils;
import com.metadata.yg.utils.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author: Y.G
 * @description:
 * @create: 2019-01-02 18:57
 **/
public class FileConsumer implements Runnable {
    public static final Logger logger = LoggerFactory.getLogger(FileConsumer.class);
    private ConcurrentLinkedQueue<List> queue;
    private String flushPath;
    private BJLTTransform transform;
    private int max;

    public FileConsumer(ConcurrentLinkedQueue<List> queue, Map path, MetadataTransform transform) {
        this.queue = queue;
        this.flushPath = DataUtils.getKeyString(path);
        this.transform=(BJLTTransform)transform;
        this.max= DataUtils.getMapMaxVal(path);

    }

    @Override
    public void run() {
        List<BufferedOutputStream> bws;
        try {
            while (transform.index<max) {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                bws = FileUtils.getBwWithoutpath(flushPath, 0, Conf.isSplit);
                List object;
                int index = 0;
                while ((object = queue.poll()) != null) {
                    int i = 0;
                    try {
                        for (; i < bws.size(); i++) {
                            if (object.get(i) instanceof Integer) {
                                continue;
                            }
                            bws.get(i).write(ObjectUtils.getObjectValues(object.get(i)));

                        }
                        index++;
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info(flushPath.split("/")[i] + "不存在该数据表");
                    }
                }
                FileUtils.flushBws(bws);
                stopWatch.stop();
                logger.info(String.format("%s，消费完成，耗费时间:%s ms,消费数据长度:%s", Thread.currentThread().getName(), stopWatch.getTime(), index));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        return Thread.currentThread().getName();
    }
}
