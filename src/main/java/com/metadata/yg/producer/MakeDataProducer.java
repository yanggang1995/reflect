package com.metadata.yg.producer;

import com.metadata.yg.inf.CallBack;
import com.metadata.yg.transform.MetadataTransform;

import java.util.concurrent.CountDownLatch;

/**
 * @author: Y.G
 * @description:
 * @create: 2019-01-01 22:30
 **/
public class MakeDataProducer implements Runnable{
    private int size;
    private CallBack<Void> callBack;
    private MetadataTransform transform;
    private CountDownLatch latch;
    public MakeDataProducer(){
        this.size=1000000;
    }

    public MakeDataProducer(int size,CallBack<Void> callBack,MetadataTransform transform,CountDownLatch latch){
        this.size=size;
        this.callBack=callBack;
        this.transform=transform;
        this.latch=latch;
    }

    @Override
    public void run() {
        int num=0;
        while (size>0) {
            num++;
            callBack.call(num, transform.getFormatRow(null));
            size--;
        }
        latch.countDown();
    }
}
