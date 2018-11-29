package com.metadata.yg.task;

import com.metadata.yg.handle.WriteDataHandle;

import java.util.concurrent.Callable;

public class TaskWithResult implements Callable<String> {
    private WriteDataHandle handle;

    private String path;

    public TaskWithResult(WriteDataHandle handle, String path) {
        this.handle = handle;
        this.path = path;
    }

    @Override
    public String call() throws Exception {
        String fileName = Thread.currentThread().getName();
        handle.save(path);
        return fileName;
    }
}
