package com.metadata.yg.task;

import com.metadata.yg.handle.WriteDataHandle;
import com.metadata.yg.utils.DataUtils;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class TaskWithResult implements Runnable {
//public class TaskWithResult implements Callable<String> {
    private WriteDataHandle handle;
    private String path;
    private CountDownLatch countDownLatch;

    public TaskWithResult(WriteDataHandle handle, Map path, CountDownLatch countDownLatch) {
        this.handle = handle;
        this.path = DataUtils.getKeyString(path);
        this.countDownLatch=countDownLatch;
    }

    @Override
    public void run()  {
//    public String call(){
//        String fileName = Thread.currentThread().getName();
        try {
            handle.save(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return fileName;
    }
}
