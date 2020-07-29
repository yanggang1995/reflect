package com.metadata.yg.producer;

import com.metadata.yg.transform.MetadataTransform;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Y.G
 * @description:
 * @create: 2019-01-02 19:18
 **/
public class DataProducer implements Runnable{
    private int size;
    private MetadataTransform transform;
    private ConcurrentLinkedQueue<List> queue;
    private CountDownLatch latch;

    public DataProducer(int size, MetadataTransform transform,ConcurrentLinkedQueue<List> queue,CountDownLatch latch){
        this.size=size;
        this.transform=transform;
        this.queue=queue;
        this.latch=latch;
    }

    @Override
    public void run() {
        while (size>0) {
            queue.add(transform.getFormatRow(null));
            size--;
        }
        latch.countDown();
    }
}
