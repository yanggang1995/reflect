package com.metadata.yg.task;

import com.metadata.yg.handle.WriteDataHandle;

import java.io.BufferedWriter;
import java.util.concurrent.Callable;

public class TaskWithResult implements Callable<String> {
    WriteDataHandle handle;

    BufferedWriter bufferedWriter;

    public TaskWithResult(WriteDataHandle handle, BufferedWriter bufferedWriter) {
        this.handle = handle;
        this.bufferedWriter = bufferedWriter;
    }

    @Override
    public String call() throws Exception {
        String fileName = Thread.currentThread().getName();

        handle.save(bufferedWriter);

        return fileName;
    }
}
