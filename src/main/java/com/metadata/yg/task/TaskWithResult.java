package com.metadata.yg.task;

import com.metadata.yg.handle.WriteDataHandle;

public class TaskWithResult implements Runnable {
//public class TaskWithResult implements Callable<String> {
    private WriteDataHandle handle;

    private String path;

    public TaskWithResult(WriteDataHandle handle, String path) {
        this.handle = handle;
        this.path = path;
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
