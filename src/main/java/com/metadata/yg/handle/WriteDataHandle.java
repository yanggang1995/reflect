package com.metadata.yg.handle;

import com.metadata.yg.utils.StopWatch;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WriteDataHandle {
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();//读写锁

    ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    List<String> cacheList;

    int currItemCount = 0;

    int dataCacheNum;

    public WriteDataHandle() {
        cacheList = new ArrayList<String>();
    }

    public WriteDataHandle(int dataCacheNum) {
        this.dataCacheNum = dataCacheNum;
        cacheList = new ArrayList<String>(dataCacheNum);
    }

    public boolean isCacheExpires() {
        return currItemCount >= dataCacheNum;
    }

    public boolean add(String sqlStr) {
        try {
            writeLock.lock();
            cacheList.add(sqlStr);
            currItemCount++;
            return isCacheExpires();
        } finally {
            writeLock.unlock();
        }
    }

    public void save(BufferedWriter bw) throws Exception {
        try {
            writeLock.lock();
            // 如果数据没有超出缓存.则返回.
            if (!isCacheExpires()) {
                return;
            }
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            for (String str : cacheList) {
                bw.write(str + "\r\n");
                currItemCount--;
            }
            stopWatch.stop();
            System.out.println(String.format("%s，消费完成，耗费时间:%s ms,消费数据长度:%s",Thread.currentThread().getName(), stopWatch.getTime(),
                    cacheList.size()));
            cacheList.clear(); // 清空数据.
        } finally {
            writeLock.unlock();
        }
    }

    public void flush(BufferedWriter bw) throws Exception {
        System.out.println(String.format("flush线程：%s, 需要保存数据的集合长度:%s", Thread.currentThread().getName(), cacheList.size()));
        for (String str : cacheList) {
            bw.write(str + "\r\n");
        }
        System.out.println(String.format("flush线程：%s, 消费完成，消费数据长度：%s", Thread.currentThread().getName(), cacheList.size()));
        cacheList.clear(); // 清空数据
        closeWrite(bw);
    }

    private void closeWrite(BufferedWriter bw) throws Exception {
        bw.flush();
        bw.close();
    }
}
