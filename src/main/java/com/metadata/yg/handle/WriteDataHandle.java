package com.metadata.yg.handle;

import com.metadata.yg.utils.StopWatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WriteDataHandle {
    public static int index=0;

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();//读写锁

    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private List<String> cacheList;

    int currItemCount = 0;

    int dataCacheNum;

    public WriteDataHandle() {
        cacheList = new ArrayList();
    }

    public WriteDataHandle(int dataCacheNum) {
        this.dataCacheNum = dataCacheNum;
        cacheList = new ArrayList(dataCacheNum);
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

    public void save(String path) throws Exception {
        BufferedWriter bw=null;
        try {
            writeLock.lock();
            bw=initDataWrite(new File(path+"."+index+".csv"));
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
            index++;
        } finally {
            if(bw!=null) {
                bw.close();
            }
            writeLock.unlock();
        }
    }

    public void flush(String path) throws Exception {
        System.out.println(String.format("flush线程：%s, 需要保存数据的集合长度:%s", Thread.currentThread().getName(), cacheList.size()));
        BufferedWriter bw= null;
        try {
            bw = initDataWrite(new File(path+"."+index+".csv"));
            System.out.println(index);
        for (String str : cacheList) {
            bw.write(str + "\r\n");
        }
        System.out.println(String.format("flush线程：%s, 消费完成，消费数据长度：%s", Thread.currentThread().getName(), cacheList.size()));
        cacheList.clear(); // 清空数据
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(bw!=null){
                closeWrite(bw);
            }
        }
    }

    private void closeWrite(BufferedWriter bw) throws Exception {
        bw.flush();
        bw.close();
    }

    public BufferedWriter initDataWrite(File dataFile) throws Exception {
        if (!dataFile.exists()) {
            if (!dataFile.createNewFile()) {
                System.err.println("创建文件失败，已存在：" + dataFile.getAbsolutePath());
            }
        }
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataFile, true), "UTF-8"));
    }
}
